package com.example.taskbackend.service;

import com.example.taskbackend.model.phasetask;
import com.example.taskbackend.repository.phasetaskrepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class phasetaskservice {
    private final phasetaskrepo repo;

    public phasetaskservice(phasetaskrepo repo) {
        this.repo = repo;
    }

    public List<phasetask> getAllTasks() {
        return repo.findAll();
    }

    public Optional<phasetask> getTaskById(Long id) {
        return repo.findById(id);
    }

    public phasetask createTask(phasetask task) {
        return repo.save(task);
    }

    public phasetask updateTask(Long id, phasetask updated) {
        return repo.findById(id).map(existing -> {
            existing.setAssignedDate(updated.getAssignedDate());
            existing.setTaskName(updated.getTaskName());
            existing.setStatus(updated.getStatus());
            existing.setRemarks(updated.getRemarks());
            existing.setProjectName(updated.getProjectName());
            existing.setAssignedTo(updated.getAssignedTo());
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Phase Task not found"));
    }

    public void deleteTask(Long id) {
        repo.deleteById(id);
    }
}
