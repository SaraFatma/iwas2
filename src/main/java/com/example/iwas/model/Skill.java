package com.example.iwas.model;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private int skillId;

    @Column(name = "skill_name", unique = true, nullable = false)
    private String skillName;
    
    @ManyToMany(mappedBy = "skills")
    private List<Project> projects;

    //17
     @ManyToMany(mappedBy = "skills")
     private List<Employee> employees; // Employees who have this skill
    
   // Constructors
     public Skill() {}

    public Skill(String skillName) {
         this.skillName = skillName;
     }


    // @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<EmployeeSkill> employeeSkills; // Employee Skills Relationship

    // public List<EmployeeSkill> getEmployeeSkills() {
    //     return employeeSkills;
    // }

    // public void setEmployeeSkills(List<EmployeeSkill> employeeSkills) {
    //     this.employeeSkills = employeeSkills;
    // }
    //17
    // Getters and Setters
    public int getSkillId() { return skillId; }
    public void setSkillId(int skillId) { this.skillId = skillId; }

    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
}
