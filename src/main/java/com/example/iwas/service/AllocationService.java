package com.example.iwas.service;

import com.example.iwas.model.Employee;
import com.example.iwas.model.Project;
import com.example.iwas.repository.EmployeeRepository;
import com.example.iwas.repository.ProjectRepository;
import com.example.iwas.repository.EmployeeProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllocationService {

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public boolean isEmployeeAllocatedToProject(Integer empId, Integer projectId) {
        Employee employee = employeeRepository.findById(empId).orElse(null);
        Project project = projectRepository.findById(projectId).orElse(null);

        if (employee == null || project == null) {
            return false; // Employee or Project doesn't exist
        }

        return employeeProjectRepository.existsByEmployeeAndProject(employee, project);
    }
}
