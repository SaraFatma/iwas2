package com.example.iwas.repository;

import com.example.iwas.model.EmployeeProject;
import com.example.iwas.model.Employee;
import com.example.iwas.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {
    
    List<EmployeeProject> findByProject(Project project); // Get all employees assigned to a project
    
    List<EmployeeProject> findByEmployee(Employee employee); // Get all projects assigned to an employee
   
    boolean existsByEmployeeAndProject(Employee employee, Project project); // Check if assignment exists

    Optional<EmployeeProject> findByEmployeeAndProject(Employee employee, Project project); // Fetch existing assignment
    

    @Query("SELECT p.projectId, p.projectName, e.empId, e.empName FROM Project p " +
    "LEFT JOIN EmployeeProject ep ON p.projectId = ep.project.projectId " +
    "LEFT JOIN Employee e ON ep.employee.empId = e.empId")
    List<Object[]> findProjectNamesWithEmployees();


    @Modifying
    @Query("DELETE FROM EmployeeProject ep WHERE ep.employee.empId = :empId AND ep.project.projectId = :projectId")
    int deleteByEmpIdAndProjectId(@Param("empId") int empId, @Param("projectId") int projectId);


    @Query("SELECT ep.project FROM EmployeeProject ep WHERE ep.employee.empId = :empId")
    List<Project> findProjectsByEmployeeId(@Param("empId") Integer empId);

}
