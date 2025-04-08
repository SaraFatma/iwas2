package com.example.iwas.repository;
import java.util.Optional;
import com.example.iwas.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; 
@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Optional<Skill> findBySkillName(String skillName); 
    List<Skill> findAllByOrderBySkillIdAsc();
}

