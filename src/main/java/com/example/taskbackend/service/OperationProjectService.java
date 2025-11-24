package com.example.taskbackend.service;

import com.example.taskbackend.model.OperationProject;
import com.example.taskbackend.model.OperationTask;
import com.example.taskbackend.repository.OperationProjectRepository;
import com.example.taskbackend.repository.OperationTaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OperationProjectService {

    private final OperationProjectRepository projectRepo;
    private final OperationTaskRepository taskRepo;

    public OperationProjectService(OperationProjectRepository projectRepo,
                                   OperationTaskRepository taskRepo) {
        this.projectRepo = projectRepo;
        this.taskRepo = taskRepo;
    }

    // Get all project names for sidebar
    public List<String> getAllProjectNames() {
        return projectRepo.findAllProjectNames();
    }

    // Create new project
    public OperationProject createProject(String projectName) {
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be empty");
        }
        String name = projectName.trim();

        if (projectRepo.existsByProjectName(name)) {
            throw new IllegalArgumentException("Project '" + name + "' already exists");
        }

        OperationProject project = new OperationProject();
        project.setProjectName(name);
        return projectRepo.save(project);
    }

    // Get project by name (with tasks if needed later)
    public OperationProject getProjectByName(String projectName) {
        return projectRepo.findByProjectName(projectName)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectName));
    }

    // Delete project + all tasks (cascade works)
    public void deleteProject(Long projectId) {
        if (!projectRepo.existsById(projectId)) {
            throw new EntityNotFoundException("Project not found with id: " + projectId);
        }
        projectRepo.deleteById(projectId);
    }

    // Get task count for badge in sidebar
    public long getTaskCountForProject(String projectName) {
        return taskRepo.countByProjectName(projectName);
    }

    // Optional: Get full project with tasks (lazy-loaded)
    public OperationProject getProjectWithTasks(Long projectId) {
        return projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
    }
}
