package com.example.iwas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_project")
public class EmployeeProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "is_confirmed", nullable = false)
    private boolean isConfirmed = false; // Default: Not confirmed

    // Constructors
    public EmployeeProject() {}

    public EmployeeProject(Employee employee, Project project, boolean isConfirmed  ) {
        this.employee = employee;
        this.project = project;
        this.isConfirmed = isConfirmed;
       
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public boolean isConfirmed() { return isConfirmed; }
    public void setConfirmed(boolean confirmed) { isConfirmed = confirmed; }
}
