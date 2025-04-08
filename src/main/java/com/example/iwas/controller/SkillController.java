
package com.example.iwas.controller;

import com.example.iwas.model.Skill;
import com.example.iwas.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/skills")  
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillRepository.findAllByOrderBySkillIdAsc();  // ✅ Fetch skills in ascending order
    }
}
