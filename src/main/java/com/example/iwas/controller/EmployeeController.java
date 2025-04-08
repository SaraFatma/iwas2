package com.example.iwas.controller;

import com.example.iwas.model.Employee;
import com.example.iwas.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Get employee by ID
    @GetMapping("/{empId}")
    public Optional<Employee> getEmployeeById(@PathVariable int empId) {
        return employeeService.getEmployeeById(empId);
    }

    // Get employee by email
    @GetMapping("/email/{empEmail}")
    public Optional<Employee> getEmployeeByEmail(@PathVariable String empEmail) {
        return employeeService.getEmployeeByEmail(empEmail);
    }

    // Add a new employee
    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }
    

    // for admin side
    @PutMapping("/{empId}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Integer empId, 
            @RequestBody Employee updatedEmployee,
            @RequestHeader("loggedInUserId") Integer loggedInUserId,
            @RequestHeader("loggedInUserRole") String loggedInUserRole) {
    
        // Fetch the requester from DB
        Employee requester = employeeService.getEmployeeById(loggedInUserId)
            .orElseThrow(() -> new RuntimeException("Requester not found!"));
    
        if (!"admin".equalsIgnoreCase(loggedInUserRole) && !empId.equals(loggedInUserId) ) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("Employees can only update their own profile!");
        }
    
       
        Employee emp = employeeService.updateEmployee(empId, updatedEmployee, requester);
        return ResponseEntity.ok(emp);
    }
    
     
    //Promote To Admin----NOT IN USE
    // @PutMapping("/promote/{empId}")
    // public ResponseEntity<String> promoteEmployeeToAdmin(@PathVariable int empId) {
    //     return ResponseEntity.ok(employeeService.promoteToAdmin(empId));
    // }

    // ✅ Delete an employee (Admins only)
    @DeleteMapping("/{empId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int empId) {
        boolean isDeleted = employeeService.deleteEmployee(empId);
        return isDeleted ? ResponseEntity.ok("Employee deleted successfully.")
                         : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
    } 

     
    //for employee side only update skills and availability
    @PutMapping("/{empId}/update")
    public ResponseEntity<String> updateProfile(
            @PathVariable int empId,
            @RequestHeader("loggedInUserId") int loggedInUserId,
            @RequestBody UpdateProfileRequest request) {
    
        Employee loggedInUser = employeeService.getEmployeeById(loggedInUserId)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));   
    
        if (loggedInUser.getEmpDesignation() == Employee.Designation.EMPLOYEE && loggedInUser.getEmpId() != empId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update another profile.");
        }
    
        return ResponseEntity.ok(employeeService.updateProfile(empId, request.isAvailability(), request.getSkills()));
    }
    

    //dto
    public static class UpdateProfileRequest {
        private boolean availability;
        private List<String> skills;
    
        // Getters & Setters
        public boolean isAvailability() { return availability; }
        public void setAvailability(boolean availability) { this.availability = availability; }
    
        public List<String> getSkills() { return skills; }
        public void setSkills(List<String> skills) { this.skills = skills; }
    }
    
}
