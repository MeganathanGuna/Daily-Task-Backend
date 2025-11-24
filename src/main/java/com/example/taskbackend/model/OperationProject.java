package com.example.taskbackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operation_projects")
public class OperationProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name", unique = true, nullable = false, length = 200)
    private String projectName;

    // One Project has Many Tasks
    @OneToMany(
            mappedBy = "operationProject",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<OperationTask> tasks = new ArrayList<>();

    // Constructors
    public OperationProject() {}

    public OperationProject(String projectName) {
        this.projectName = projectName;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public List<OperationTask> getTasks() { return tasks; }
    public void setTasks(List<OperationTask> tasks) { this.tasks = tasks; }

    // Helper methods (optional but clean)
    public void addTask(OperationTask task) {
        tasks.add(task);
        task.setOperationProject(this);
    }

    public void removeTask(OperationTask task) {
        tasks.remove(task);
        task.setOperationProject(null);
    }
}
