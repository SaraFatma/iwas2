package com.example.iwas.repository;

import com.example.iwas.model.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Integer> {
   
    //old method-11
    List<EmployeeSkill> findByEmployeeEmpId(int empId);
    //void deleteByEmployeeEmpIdAndSkillSkillId(int empId, int skillId); // Deleting by both empId and skillId
    
     // New method- 11   to return only skills
    @Query("SELECT s.skillName FROM EmployeeSkill es JOIN es.skill s WHERE es.employee.empId = :empId")
    List<String> findSkillNamesByEmployeeId(@Param("empId") int empId);
    
    
    void deleteByEmployeeEmpIdAndSkillSkillId(int empId, int skillId);

    EmployeeSkill findByEmployee_EmpIdAndSkill_SkillName(int empId, String skillName);
}
