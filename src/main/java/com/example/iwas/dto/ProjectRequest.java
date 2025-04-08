package com.example.iwas.dto;
import java.util.Date;
import java.util.List;

public class ProjectRequest {
    private String projectName;
    private String projectDescription;
    // Skill IDs selected by the admin
    private Date startDate;  // ✅ Added startDate
    private Date endDate; 
    private List<Integer> skillIds;  

    // Getters and Setters
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }



    public String getProjectDescription() { return projectDescription; }
    public void setProjectDescription(String projectDescription) { this.projectDescription = projectDescription; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }



    public List<Integer> getSkillIds() { return skillIds; }
    public void setSkillIds(List<Integer> skillIds) { this.skillIds = skillIds; }
}
