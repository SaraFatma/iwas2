package com.example.iwas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.iwas.service.ProjectService;
import com.example.iwas.service.EmployeeService;
import com.example.iwas.service.LeaveRequestService;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DashboardController {
    
    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LeaveRequestService leaveRequestService;

    // Get total projects count
    @GetMapping("/projects/count")
    public long getTotalProjects() {
        return projectService.countProjects();
    }

    // Get total employees count
    @GetMapping("/employees/count")
    public long getTotalEmployees() {
        return employeeService.countEmployees();
    }

    // Get pending leave requests count
    @GetMapping("/leave/pending/count")
    public long getPendingLeaves() {
        return leaveRequestService.countPendingLeaveRequests();
    }
}
