package com.example.iwas.model;

import java.util.Date;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;
    
    @Column(nullable = false)
    private String projectName;
    
    @Column(columnDefinition = "TEXT")
    private String projectDescription;

    @Column(nullable = false)
    private Date startDate;   // ✅ Added startDate
    @Column(nullable = true)
    private Date endDate;  

     
     


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "project_skill",  // Linking table
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;  // List of skills required for this project

    //15
    // Getters and Setters
    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getProjectDescription() { return projectDescription; }
    public void setProjectDescription(String projectDescription) { this.projectDescription = projectDescription; }
    
    
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    

    //15
    // public List<String> getRequiredSkills() { return requiredSkills; }
    // public void setRequiredSkills(List<String> requiredSkills) { this.requiredSkills = requiredSkills; }
    public List<Skill> getSkills() { return skills; }
    public void setSkills(List<Skill> skills) { this.skills = skills; }
    //15
}
