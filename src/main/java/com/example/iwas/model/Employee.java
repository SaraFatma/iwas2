package com.example.iwas.model;
import java.util.List;
import jakarta.persistence.*;
 

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private int empId;

    @Column(name = "emp_name", nullable = false)
    private String empName;

    @Column(name = "emp_email", unique = true, nullable = false)
    private String empEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "emp_designation", nullable = false)
    private Designation empDesignation = Designation.EMPLOYEE; // Default: EMPLOYEE

    @Column(name = "emp_department")
    private String empDepartment;

    @Column(name = "emp_availability", nullable = false)
    private boolean empAvailability = true; // Default TRUE

    
    @Column(name = "password", nullable = false)
    private String password;  // Store the password directly


    // Enum for Designation
    public enum Designation {
        ADMIN, EMPLOYEE
    }
    

    //17
    @ManyToMany
    @JoinTable(
        name = "employee_skill",
        joinColumns = @JoinColumn(name = "emp_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )


   
    private List<Skill> skills;

    public List<Skill> getSkills() { 
        return skills; 
    }
    public void setSkills(List<Skill> skills) { 
        this.skills = skills; 
    }
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeeProject> assignedProjects;
     

   
     // Constructors
    public Employee() {}

    public Employee(String empName, String empEmail, Designation empDesignation, String empDepartment, boolean empAvailability, String password) {
        this.empName = empName;
        this.empEmail = empEmail;
        this.empDesignation = empDesignation;
        this.empDepartment = empDepartment;
        this.empAvailability = empAvailability;
        this.password = password;
    }

    // Getters and Setters
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }

    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }

    public String getEmpEmail() { return empEmail; }
    public void setEmpEmail(String empEmail) { this.empEmail = empEmail; }

    public Designation getEmpDesignation() { return empDesignation; }
    public void setEmpDesignation(Designation empDesignation) { this.empDesignation = empDesignation; }

    public String getEmpDepartment() { return empDepartment; }
    public void setEmpDepartment(String empDepartment) { this.empDepartment = empDepartment; }

    public boolean isEmpAvailability() { return empAvailability; }
    public void setEmpAvailability(boolean empAvailability) { this.empAvailability = empAvailability; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isAdmin() {
        return this.empDesignation == Designation.ADMIN;
    }
}

