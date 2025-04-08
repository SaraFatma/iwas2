package com.example.iwas.controller;

import com.example.iwas.model.Employee;
import com.example.iwas.model.Project;
import com.example.iwas.service.EmployeeService;
import com.example.iwas.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.iwas.dto.ProjectRequest; // ✅ Import DTO

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeService employeeService;

    // Get all projects
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    // Get project by ID
    @GetMapping("/{projectId}")
    public Optional<Project> getProjectById(@PathVariable int projectId) {
        return projectService.getProjectById(projectId);
    }


    
    //16 +using dto
   // Add a new project (Admin Only)
    // @PostMapping
    // public ResponseEntity<?> addProject(@RequestHeader("loggedInUserId") int loggedInUserId, @RequestBody Project project) {
    //     Employee requester = employeeService.getEmployeeById(loggedInUserId)
    //             .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

    //     return ResponseEntity.ok(projectService.addProject(project, requester));
    // }
    


    // // Update a project (Admin Only)
    // @PutMapping("/{projectId}")
    // public ResponseEntity<?> updateProject(@PathVariable int projectId, 
    //                                        @RequestHeader("loggedInUserId") int loggedInUserId, 
    //                                        @RequestBody Project updatedProject) {
    //     Employee requester = employeeService.getEmployeeById(loggedInUserId)
    //             .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

    //     return ResponseEntity.ok(projectService.updateProject(projectId, updatedProject, requester));
    // }
    
    // Add a new project (Admin Only)
@PostMapping
public ResponseEntity<?> addProject(@RequestHeader("loggedInUserId") int loggedInUserId, 
                                    @RequestBody ProjectRequest projectRequest) {
    Employee requester = employeeService.getEmployeeById(loggedInUserId)
            .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

    return ResponseEntity.ok(projectService.addProject(projectRequest, requester));
}

// Update a project (Admin Only)
@PutMapping("/{projectId}")
public ResponseEntity<?> updateProject(@PathVariable int projectId, 
                                       @RequestHeader("loggedInUserId") int loggedInUserId, 
                                       @RequestBody ProjectRequest projectRequest) {
    Employee requester = employeeService.getEmployeeById(loggedInUserId)
            .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

    return ResponseEntity.ok(projectService.updateProject(projectId, projectRequest, requester));
}

    
    //16 +using dto

    // Delete a project (Admin Only)
    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable int projectId, @RequestHeader("loggedInUserId") int loggedInUserId) {
        Employee requester = employeeService.getEmployeeById(loggedInUserId)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        return projectService.deleteProject(projectId, requester)
                ? ResponseEntity.ok("Project deleted successfully.")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found.");
    }
}
