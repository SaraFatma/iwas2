package com.example.iwas.service;

import com.example.iwas.model.Employee;
import com.example.iwas.model.EmployeeSkill;
import com.example.iwas.model.Skill;
import com.example.iwas.repository.EmployeeRepository;
import com.example.iwas.repository.EmployeeSkillRepository;
import com.example.iwas.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeSkillService {

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SkillRepository skillRepository;

    // Add a skill for an employee
    public String addSkill(int empId, String skillName) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if (employeeOptional.isEmpty()) {
            return "Employee not found!";
        }
 

        Skill skill = skillRepository.findBySkillName(skillName).orElseGet(() -> {
            Skill newSkill = new Skill(skillName);
            return skillRepository.save(newSkill);  // ✅ Automatically create new skill if not found
        });
        EmployeeSkill employeeSkill = new EmployeeSkill(employeeOptional.get(), skill);
        employeeSkillRepository.save(employeeSkill);
        return "Skill added successfully!";
    }
 
    public List<String> getSkillNamesByEmployeeId(int empId) {
        return employeeSkillRepository.findSkillNamesByEmployeeId(empId);
    }
     // Fetch all skills sorted by skill_id
     public List<Skill> getAllSkills() {
        return skillRepository.findAllByOrderBySkillIdAsc();
    }

    // Delete a skill from an employee
    @Transactional
    // public String deleteSkill(int empId, int skillId) {
    //    employeeSkillRepository.deleteByEmployeeEmpIdAndSkillSkillId(empId, skillId);
    //    return "Skill deleted successfully!";
    //     // int deletedCount = employeeSkillRepository.deleteByEmployeeEmpIdAndSkillSkillId(empId, skillId);
    //     // return deletedCount > 0 ? "Skill deleted successfully!" : "Skill not found for employee!";
    // }

 

    public String deleteSkill(int empId, String skillName) {
        EmployeeSkill employeeSkill = employeeSkillRepository.findByEmployee_EmpIdAndSkill_SkillName(empId, skillName);
        
        if (employeeSkill == null) {
            return "Skill not found for employee";
        }

        employeeSkillRepository.delete(employeeSkill);
        return "Skill removed successfully";
    }
    
}
