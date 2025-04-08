 package com.example.iwas.controller;

import com.example.iwas.model.Employee;
import com.example.iwas.service.AuthService;
import com.example.iwas.dto.LoginRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        String empEmail = loginRequest.getEmpEmail();
        String password = loginRequest.getPassword();

        Employee employee = authService.authenticate(empEmail, password);

        if (employee != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("employeeId", employee.getEmpId());
            response.put("emp_name", employee.getEmpName());
            response.put("emp_email", employee.getEmpEmail());
            response.put("emp_department", employee.getEmpDepartment());
            response.put("emp_role", employee.getEmpDesignation());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}

