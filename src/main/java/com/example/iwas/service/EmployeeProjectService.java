package com.example.iwas.service;
 
import com.example.iwas.dto.ProjectSuggestionDTO;
import com.example.iwas.model.Employee;
import com.example.iwas.model.Project;
import com.example.iwas.model.Skill;
import com.example.iwas.model.EmployeeProject;
import com.example.iwas.repository.EmployeeRepository;
import com.example.iwas.repository.ProjectRepository;
import com.example.iwas.repository.EmployeeProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Map;

@Service
public class EmployeeProjectService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;
     
     public List<ProjectSuggestionDTO> getSuggestedEmployeesForAllProjects() {
        // Get all projects
        List<Project> allProjects = projectRepository.findAll();

        // Prepare the list of suggestions for each project
        List<ProjectSuggestionDTO> suggestions = new ArrayList<>();

        // Loop through each project to get suggestions
        for (Project project : allProjects) {
            List<Employee> suggestedEmployees = getSuggestedEmployeesForProject(project.getProjectId());
            suggestions.add(new ProjectSuggestionDTO(project, suggestedEmployees));
        }

        return suggestions;
    }
    
 
    public List<Employee> getSuggestedEmployeesForProject(Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    
        Set<Skill> requiredSkills = new HashSet<>(project.getSkills());
    
        // Fetch employees who are already assigned to the project
        List<Employee> alreadyAssignedEmployees = employeeProjectRepository.findByProject(project)
                .stream()
                .map(EmployeeProject::getEmployee)
                .collect(Collectors.toList());
    
        return employeeRepository.findAll().stream()
                .filter(emp -> emp.isEmpAvailability() &&
                        emp.getSkills().stream().anyMatch(requiredSkills::contains) && // At least one matching skill
                        !alreadyAssignedEmployees.contains(emp) // Exclude already assigned employees
                )
                .collect(Collectors.toList());
    }
    



    // This is the new method to check if an employee is already assigned to a project
    public EmployeeProject getEmployeeProjectAssignment(Integer empId, Integer projectId) {
        // Query the database to check if there's already an assignment for this employee and project
        // Fetch the employee and project entities from the database
    Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new RuntimeException("Employee not found"));
    Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        
        
    return employeeProjectRepository.findByEmployeeAndProject(employee, project)
    .orElse(null); 
    }
    public EmployeeProject assignEmployeeToProject(Integer empId, Integer projectId) {
       // Fetch the employee and project entities from the database
    Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new RuntimeException("Employee not found"));
    Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

    // Check if the employee is already assigned to the project
    Optional<EmployeeProject> existingAssignment = employeeProjectRepository.findByEmployeeAndProject(employee, project);
    if (existingAssignment.isPresent()) {
        throw new RuntimeException("Employee is already assigned to this project.");
    }

    // Create a new EmployeeProject object
    EmployeeProject employeeProject = new EmployeeProject();
    employeeProject.setEmployee(employee); // Set employee object
    employeeProject.setProject(project);   // Set project object
    employeeProject.setConfirmed(true);   // Default: Not confirmed

    // Save to the database
    return employeeProjectRepository.save(employeeProject);
    }



    // **3. Admin accepts or rejects a suggested employee**
    public void confirmEmployeeAssignment(Integer empId, Integer projectId, boolean isConfirmed) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        EmployeeProject employeeProject = employeeProjectRepository.findByEmployeeAndProject(employee, project)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        employeeProject.setConfirmed(isConfirmed);
        employeeProjectRepository.save(employeeProject);
    }
 


    public List<Map<String, Object>> getProjectsWithEmployees() {
        List<Object[]> results = employeeProjectRepository.findProjectNamesWithEmployees();
        Map<String, Map<String, Object>> projectEmployeeMap = new HashMap<>();
    
        if (results.isEmpty()) {
            System.out.println("No results found.");
        }
    
        for (Object[] row : results) {
            Integer projectId = (Integer) row[0];
            String projectName = (String) row[1];
            Integer empId = (Integer) row[2]; 
            String empName = (String) row[3]; 
    
            projectEmployeeMap.putIfAbsent(projectName, new HashMap<>());
            Map<String, Object> projectData = projectEmployeeMap.get(projectName);
    
            projectData.put("projectId", projectId);
            projectData.put("projectName", projectName);
           
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> employees = (List<Map<String, Object>>) projectData.getOrDefault("employees", new ArrayList<>());
    
            if (empId != null && empName != null) {
                Map<String, Object> employeeData = new HashMap<>();
                employeeData.put("empId", empId);
                employeeData.put("empName", empName);
                employees.add(employeeData);
            }
    
            projectData.put("employees", employees);
        }
    
        return new ArrayList<>(projectEmployeeMap.values());
    }

    @Transactional

    public boolean removeEmployeeFromProject(int empId, int projectId) {
        int deletedRows = employeeProjectRepository.deleteByEmpIdAndProjectId(empId, projectId);
        return deletedRows > 0;
    }
    public List<Project> getProjectsForEmployee(Integer empId) {
        return employeeProjectRepository.findProjectsByEmployeeId(empId);
    }
    
    
}
