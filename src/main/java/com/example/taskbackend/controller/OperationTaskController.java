package com.example.taskbackend.controller;

import com.example.taskbackend.model.OperationTask;
import com.example.taskbackend.service.OperationTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation/tasks")
@CrossOrigin(origins = "*")
public class OperationTaskController {

    private final OperationTaskService service;

    public OperationTaskController(OperationTaskService service) {
        this.service = service;
    }

    // GET /operation/tasks → all tasks
    @GetMapping
    public List<OperationTask> getAllTasks() {
        return service.getAllTasks();
    }

    // GET /operation/tasks/project/Server Backup → tasks for specific project
    @GetMapping("/project/{projectName}")
    public List<OperationTask> getTasksByProject(@PathVariable String projectName) {
        return service.getTasksByProjectName(projectName);
    }

    // POST /operation/tasks
    @PostMapping
    public ResponseEntity<OperationTask> createTask(@RequestBody OperationTask task) {
        try {
            OperationTask created = service.createTask(
                    task.getTaskName(),
                    task.getAssignedTo(),
                    task.getStatus(),
                    task.getLink(),
                    task.getAssignedDate(),
                    task.getDueDate(),
                    task.getRemarks(),
                    task.getOperationProject().getProjectName()
            );
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // PUT /operation/tasks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<OperationTask> updateTask(@PathVariable Long id, @RequestBody OperationTask task) {
        try {
            OperationTask updated = service.updateTask(
                    id,
                    task.getTaskName(),
                    task.getAssignedTo(),
                    task.getStatus(),
                    task.getLink(),
                    task.getAssignedDate(),
                    task.getDueDate(),
                    task.getRemarks(),
                    task.getOperationProject() != null ? task.getOperationProject().getProjectName() : null
            );
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /operation/tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
