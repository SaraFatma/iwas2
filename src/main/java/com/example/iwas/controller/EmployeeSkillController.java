package com.example.iwas.controller;

//import com.example.iwas.model.EmployeeSkill;
import com.example.iwas.service.EmployeeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.iwas.dto.SkillRequest;  // Import the DTO

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/employee-skills")
public class EmployeeSkillController {

    @Autowired
    private EmployeeSkillService employeeSkillService;


    
    @PostMapping("/{empId}")
public String addSkill(@PathVariable int empId, @RequestBody SkillRequest request) {
    return employeeSkillService.addSkill(empId, request.getSkillName());
}

 
    // Get skill names instead of full EmployeeSkill objects
@GetMapping("/{empId}/names")
public List<String> getSkillNamesByEmployeeId(@PathVariable int empId) {
    return employeeSkillService.getSkillNamesByEmployeeId(empId);
}
 
    @DeleteMapping("/{empId}")
public String deleteSkill(@PathVariable int empId, @RequestParam String skillName) {
    return employeeSkillService.deleteSkill(empId, skillName);
}

}


 