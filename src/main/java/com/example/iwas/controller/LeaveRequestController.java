package com.example.iwas.controller;

import com.example.iwas.dto.LeaveRequestRequest;
import com.example.iwas.model.LeaveRequest;
import com.example.iwas.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
@RestController

@CrossOrigin(origins = "*")
@RequestMapping("/api/leave")
 
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;
    // @Autowired
    //  private LeaveRequestRepository leaveRequestRepository;

 
    @PostMapping("/apply")
public LeaveRequest applyForLeave(@RequestBody LeaveRequestRequest leaveRequestRequest) {
    return leaveRequestService.applyForLeave(
        
        leaveRequestRequest.getEmpId(),
        leaveRequestRequest.getStartDate(),
        leaveRequestRequest.getEndDate(),
        leaveRequestRequest.getReason()
    );
 }
 


    // Employee views their leave requests
    @GetMapping("/{empId}")
    public List<LeaveRequest> getLeaveRequestsByEmployee(@PathVariable Integer empId) {
        return leaveRequestService.getLeaveRequestsByEmployee(empId);
    }

    //Admin views all pending leave requests
    @GetMapping("/pending")
    public List<LeaveRequest> getPendingLeaveRequests() {
        return leaveRequestService.getPendingLeaveRequests();
    }


    // Admin approves/rejects leave
    @PutMapping("/{leaveId}/approve")
    public LeaveRequest approveLeave(@PathVariable Integer leaveId, 
                                     @RequestParam String status) {
        return leaveRequestService.processLeaveRequest(leaveId, status);
    }
}
