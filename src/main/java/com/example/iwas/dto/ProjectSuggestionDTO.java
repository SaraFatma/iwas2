package com.example.iwas.dto;

import com.example.iwas.model.Employee;
import com.example.iwas.model.Project;
import java.util.List;

public class ProjectSuggestionDTO {
    
    private Project project;
    private List<Employee> suggestedEmployees;

    public ProjectSuggestionDTO(Project project, List<Employee> suggestedEmployees) {
        this.project = project;
        this.suggestedEmployees = suggestedEmployees;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Employee> getSuggestedEmployees() {
        return suggestedEmployees;
    }

    public void setSuggestedEmployees(List<Employee> suggestedEmployees) {
        this.suggestedEmployees = suggestedEmployees;
    }
}
