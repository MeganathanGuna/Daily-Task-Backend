package com.example.taskbackend.service;

import com.example.taskbackend.model.OperationProject;
import com.example.taskbackend.model.OperationTask;
import com.example.taskbackend.repository.OperationProjectRepository;
import com.example.taskbackend.repository.OperationTaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OperationTaskService {

    private final OperationTaskRepository taskRepo;
    private final OperationProjectRepository projectRepo;

    public OperationTaskService(OperationTaskRepository taskRepo,
                                OperationProjectRepository projectRepo) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
    }

    // Get all tasks (global view)
    public List<OperationTask> getAllTasks() {
        return taskRepo.findAll();
    }

    // Get tasks by project name
    public List<OperationTask> getTasksByProjectName(String projectName) {
        return taskRepo.findByOperationProject_ProjectName(projectName);
    }

    // Create new task
    public OperationTask createTask(
            String taskName,
            String assignedTo,
            String status,
            String link,
            LocalDate assignedDate,
            LocalDate dueDate,
            String remarks,
            String projectName
    ) {
        OperationProject project = projectRepo.findByProjectName(projectName)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectName));

        OperationTask task = new OperationTask();
        task.setTaskName(taskName);
        task.setAssignedTo(assignedTo);
        task.setStatus(status != null && !status.isBlank() ? status : "Open");
        task.setLink(link);
        task.setAssignedDate(assignedDate != null ? assignedDate : LocalDate.now());
        task.setDueDate(dueDate);
        task.setRemarks(remarks);
        task.setOperationProject(project);

        return taskRepo.save(task);
    }

    // Update existing task - FIXED LINE HERE
    public OperationTask updateTask(
            Long taskId,
            String taskName,
            String assignedTo,
            String status,
            String link,
            LocalDate assignedDate,
            LocalDate dueDate,
            String remarks,
            String projectName
    ) {
        OperationTask task = taskRepo.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found: " + taskId));

        // FIXED: Was taskOperationProject() → now task.getOperationProject()
        if (projectName != null &&
                task.getOperationProject() != null &&
                !projectName.equals(task.getOperationProject().getProjectName())) {

            OperationProject newProject = projectRepo.findByProjectName(projectName)
                    .orElseThrow(() -> new EntityNotFoundException("Target project not found: " + projectName));
            task.setOperationProject(newProject);
        }

        task.setTaskName(taskName);
        task.setAssignedTo(assignedTo);
        task.setStatus(status);
        task.setLink(link);
        task.setAssignedDate(assignedDate);
        task.setDueDate(dueDate);
        task.setRemarks(remarks);

        return task; // Saved automatically by @Transactional
    }

    // Delete task
    public void deleteTask(Long taskId) {
        if (!taskRepo.existsById(taskId)) {
            throw new EntityNotFoundException("Task not found: " + taskId);
        }
        taskRepo.deleteById(taskId);
    }
}