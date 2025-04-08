package com.example.iwas.service;

import com.example.iwas.model.Employee;
import com.example.iwas.model.EmployeeSkill;
import com.example.iwas.model.Skill;
import com.example.iwas.repository.EmployeeRepository;
import com.example.iwas.repository.EmployeeSkillRepository;
import com.example.iwas.repository.SkillRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
 
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    @Autowired
    private SkillRepository skillRepository;

    public long countEmployees() {
        return employeeRepository.count();
    }   

    // Get all employees
    public List<Employee> getAllEmployees() {
        
        return  employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "empId"));
    }

    // Get employee by ID
    public Optional<Employee> getEmployeeById(int empId) {
        return employeeRepository.findById(empId);
    }

    // Get employee by email
    public Optional<Employee> getEmployeeByEmail(String empEmail) {
        return employeeRepository.findByEmpEmail(empEmail);
    }

    // Add a new employee
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public String promoteToAdmin(int empId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setEmpDesignation(Employee.Designation.ADMIN);
            employeeRepository.save(employee);
            return "Employee promoted to Admin successfully.";
        } else {
            return "Employee not found.";
        }
    }
    
    @Transactional
    public String updateProfile(int empId, boolean availability, List<String> skillNames) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found!"));

        // Update availability
        employee.setEmpAvailability(availability);
        employeeRepository.save(employee);

        for (String skillName : skillNames) {
            Skill skill = skillRepository.findBySkillName(skillName).orElseGet(() -> {
                Skill newSkill = new Skill(skillName);
                return skillRepository.save(newSkill);
            });

            EmployeeSkill newSkill = new EmployeeSkill(employee, skill);
            employeeSkillRepository.save(newSkill);
        }

        return "Profile updated successfully!";
}


    public Employee updateEmployee(int empId, Employee updatedEmployee, Employee requester) {
        return employeeRepository.findById(empId).map(employee -> {
            if (requester.getEmpId() == empId) { 
                // Employee updating their own profile → Only allow availability update
                employee.setEmpAvailability(updatedEmployee.isEmpAvailability());
            } else if (requester.getEmpDesignation() == Employee.Designation.ADMIN) { 
                // Admin updating any employee → Allow full update, but prevent null overwrites
                employee.setEmpName(updatedEmployee.getEmpName() != null ? updatedEmployee.getEmpName() : employee.getEmpName());
                employee.setEmpEmail(updatedEmployee.getEmpEmail() != null ? updatedEmployee.getEmpEmail() : employee.getEmpEmail());
                employee.setEmpDesignation(updatedEmployee.getEmpDesignation() != null ? updatedEmployee.getEmpDesignation() : employee.getEmpDesignation());
                employee.setEmpDepartment(updatedEmployee.getEmpDepartment() != null ? updatedEmployee.getEmpDepartment() : employee.getEmpDepartment());
                employee.setEmpAvailability(updatedEmployee.isEmpAvailability());
                employee.setPassword(updatedEmployee.getPassword() != null ? updatedEmployee.getPassword() : employee.getPassword());
            } else {
                throw new RuntimeException("Permission denied!");
            }
    
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new RuntimeException("Employee not found!"));
    }
    
    // Delete an employee
    public boolean deleteEmployee(int empId) {
        if (employeeRepository.existsById(empId)) {
            employeeRepository.deleteById(empId);
            return true;
        }
        return false;
    }
}

