package com.example.iwas.service;

import com.example.iwas.model.Employee;
import com.example.iwas.model.LeaveRequest;
import com.example.iwas.repository.EmployeeRepository;
import com.example.iwas.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public long countPendingLeaveRequests() {
       // return leaveRequestRepository.countByStatus("PENDING");
        return leaveRequestRepository.countByStatus(LeaveRequest.LeaveStatus.PENDING);
    }

    // Employee applies for leave
    public LeaveRequest applyForLeave(Integer empId, LocalDate startDate, LocalDate endDate, String reason  ) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found!"));

        LeaveRequest leaveRequest = new LeaveRequest();
        
        leaveRequest.setEmployee(employee);
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setReason(reason);
        leaveRequest.setStatus(LeaveRequest.LeaveStatus.PENDING); // ✅ Always set default status

        return leaveRequestRepository.save(leaveRequest);
    }

    // Employee views their leave requests
    public List<LeaveRequest> getLeaveRequestsByEmployee(Integer empId) {
        return leaveRequestRepository.findByEmployeeEmpId(empId);
    }

    // Admin views pending leave requests
    public List<LeaveRequest> getPendingLeaveRequests() {
        return leaveRequestRepository.findByStatus(LeaveRequest.LeaveStatus.PENDING);
    }

    // Admin approves/rejects leave
    public LeaveRequest processLeaveRequest(Integer leaveId, String status) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found!"));

        try {
            LeaveRequest.LeaveStatus leaveStatus = LeaveRequest.LeaveStatus.valueOf(status.toUpperCase()); // ✅ Converts String to Enum
            leaveRequest.setStatus(leaveStatus);
            return leaveRequestRepository.save(leaveRequest);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid leave status! Allowed values: PENDING, APPROVED, REJECTED");
        }
    }
}
