package com.example.iwas.service;
 
import com.example.iwas.model.Employee; 
import com.example.iwas.dto.ProjectRequest;
import com.example.iwas.model.Project;
import com.example.iwas.model.Skill;
import com.example.iwas.repository.ProjectRepository;
import com.example.iwas.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SkillRepository skillRepository;

    
    public long countProjects() {
        return projectRepository.count();
    }
        
 
    
        // ✅ Get all projects
        public List<Project> getAllProjects() {
           // return projectRepository.findAll();
            return projectRepository.findAll(Sort.by(Sort.Direction.ASC, "projectId"))  ;
        }
    
        // ✅ Get project by ID
        public Optional<Project> getProjectById(int projectId) {
            return projectRepository.findById(projectId);
        }

    public Project addProject(ProjectRequest projectRequest, Employee requester) {
        if (requester.getEmpDesignation() != Employee.Designation.ADMIN) {
            throw new RuntimeException("Only admins can add projects!");
        }

        Project project = new Project();
        project.setProjectName(projectRequest.getProjectName());
        project.setProjectDescription(projectRequest.getProjectDescription());      
        project.setStartDate(projectRequest.getStartDate());  // ✅ Added
        project.setEndDate(projectRequest.getEndDate());   

        // Fetch skills and associate them with the project
        List<Skill> skills = skillRepository.findAllById(projectRequest.getSkillIds());
        project.setSkills(skills);

        return projectRepository.save(project);
    }

    public Project updateProject(int projectId, ProjectRequest projectRequest, Employee requester) {
        if (requester.getEmpDesignation() != Employee.Designation.ADMIN) {
            throw new RuntimeException("Only admins can update projects!");
        }

        return projectRepository.findById(projectId).map(project -> {
            project.setProjectName(projectRequest.getProjectName());
            project.setProjectDescription(projectRequest.getProjectDescription());
            project.setStartDate(projectRequest.getStartDate());  // ✅ Added
            project.setEndDate(projectRequest.getEndDate());

            // Update skills
            List<Skill> skills = skillRepository.findAllById(projectRequest.getSkillIds());
            project.setSkills(skills);

            return projectRepository.save(project);
        }).orElseThrow(() -> new RuntimeException("Project not found"));
    } 

    public boolean deleteProject(int projectId, Employee requester) {
        if (requester.getEmpDesignation() != Employee.Designation.ADMIN) {
            throw new RuntimeException("Only admins can delete projects!");
        }

        if (projectRepository.existsById(projectId)) {
            projectRepository.deleteById(projectId);
            return true;
        }
        return false;
    }
}
