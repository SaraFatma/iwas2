package com.example.iwas.controller;

import com.example.iwas.model.Employee;
import com.example.iwas.model.EmployeeProject;
import com.example.iwas.model.Project;
import com.example.iwas.service.EmployeeProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.iwas.dto.ProjectSuggestionDTO;
 
import java.util.List;
import java.util.Map;

 
@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/employee-project")
public class EmployeeProjectController {

    @Autowired
    private EmployeeProjectService employeeProjectService;

   
    @GetMapping("/suggest-all")
    public List<ProjectSuggestionDTO> getSuggestedEmployeesForAllProjects() {
        return employeeProjectService.getSuggestedEmployeesForAllProjects();
    }

    // **1. Get suggested employees for a project**
    @GetMapping("/suggest/{projectId}")
    public List<Employee> getSuggestedEmployees(@PathVariable Integer projectId) {
        return employeeProjectService.getSuggestedEmployeesForProject(projectId);
    }


    @GetMapping("/allProjectEmp")
    public ResponseEntity<List<Map<String, Object>>> getProjectsWithEmployees() {
        try {
            List<Map<String, Object>> projectsWithEmployees = employeeProjectService.getProjectsWithEmployees();
            
            if (projectsWithEmployees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return ResponseEntity.ok(projectsWithEmployees);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // **2. Assign an employee to a project manually**
    @PostMapping("/assign")
    public EmployeeProject assignEmployeeToProject(@RequestParam Integer empId, @RequestParam Integer projectId) {
        return employeeProjectService.assignEmployeeToProject(empId, projectId);
    }

    

    @PostMapping("/confirm")
public String confirmAssignment(@RequestParam Integer empId, @RequestParam Integer projectId, @RequestParam boolean isConfirmed) {
    // Check if the employee is already assigned to the project
    EmployeeProject existingAssignment = employeeProjectService.getEmployeeProjectAssignment(empId, projectId);
    
    if (existingAssignment != null) {
        return "Employee is already assigned to this project.";
    }

    if (isConfirmed) {
        // Assign employee to the project if confirmed
        employeeProjectService.assignEmployeeToProject(empId, projectId);
        return "Employee assignment confirmed.";
    } else {
        // Reject the assignment and do nothing in the table
        return "Employee assignment rejected.";
    }
}
 

@DeleteMapping("/remove")
public ResponseEntity<String> removeEmployeeFromProject(@RequestBody Map<String, Integer> request) {
    try {
        int empId = request.get("empId");
        int projectId = request.get("projectId");
        System.out.println("Received Delete Request - EmpID: " + empId + ", ProjectID: " + projectId);
        boolean isRemoved = employeeProjectService.removeEmployeeFromProject(empId, projectId);

        if (isRemoved) {
            return ResponseEntity.ok("Employee removed successfully from project.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee or Project not found.");
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while removing employee.");
    }
}

@GetMapping("/projects/{empId}")
public ResponseEntity<List<Project>> getProjectsForEmployee(@PathVariable Integer empId) {
    List<Project> projects = employeeProjectService.getProjectsForEmployee(empId);
    return ResponseEntity.ok(projects);
}


}
