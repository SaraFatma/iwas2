package com.example.iwas.dto;

import com.example.iwas.model.Employee;
import com.example.iwas.model.Project;
import java.util.List;

public class ProjectWithEmployeesDTO {
    private Integer projectId;
    private String projectName;
    private String projectDescription;
    private List<Employee> assignedEmployees;

    public ProjectWithEmployeesDTO(Project project, List<Employee> employees) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.projectDescription = project.getProjectDescription();
        this.assignedEmployees = employees;
    }

    // Getters & Setters
    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getProjectDescription() { return projectDescription; }
    public void setProjectDescription(String projectDescription) { this.projectDescription = projectDescription; }

    public List<Employee> getAssignedEmployees() { return assignedEmployees; }
    public void setAssignedEmployees(List<Employee> assignedEmployees) { this.assignedEmployees = assignedEmployees; }
}
