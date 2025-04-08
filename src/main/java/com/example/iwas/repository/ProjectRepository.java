package com.example.iwas.repository;

import com.example.iwas.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    long count();
}
