package com.example.taskbackend.controller;

import com.example.taskbackend.model.OperationProject;
import com.example.taskbackend.service.OperationProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation/projects")
@CrossOrigin(origins = "*")
public class OperationProjectController {

    private final OperationProjectService service;

    public OperationProjectController(OperationProjectService service) {
        this.service = service;
    }

    // GET /operation/projects/names → for sidebar
    @GetMapping("/names")
    public List<String> getAllProjectNames() {
        return service.getAllProjectNames();
    }

    // GET /operation/projects → all projects (optional)
    @GetMapping
    public List<OperationProject> getAllProjects() {
        return service.getAllProjectNames().stream()
                .map(service::getProjectByName)
                .toList();
    }

    // POST /operation/projects
    @PostMapping
    public ResponseEntity<OperationProject> createProject(@RequestBody OperationProject project) {
        if (project.getProjectName() == null || project.getProjectName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        OperationProject created = service.createProject(project.getProjectName());
        return ResponseEntity.ok(created);
    }

    // DELETE /operation/projects/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        service.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}