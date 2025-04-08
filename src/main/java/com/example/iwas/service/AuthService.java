package com.example.iwas.service;

import com.example.iwas.model.Employee;
import com.example.iwas.repository.EmployeeRepository;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.Optional;

@Service
public class AuthService {
 
    @Autowired
    private EmployeeRepository employeeRepository; 
    
    public Employee authenticate(String empEmail, String password) {
        Optional<Employee> employee = employeeRepository.findByEmpEmail(empEmail);
    
        if (employee.isPresent() && employee.get().getPassword().equals(password)) {
            return employee.get(); // Return Employee object
        }
        return null; // Return null instead of "Invalid Credentials"
    }
    
    
}


