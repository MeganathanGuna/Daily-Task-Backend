package com.example.taskbackend.controller;

import com.example.taskbackend.model.phasetask;
import com.example.taskbackend.service.phasetaskservice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phasetasks")   // ✅ unique path for phase tasks
@CrossOrigin(origins = "*")
public class phasetaskcontroller {

    private final phasetaskservice service;

    public phasetaskcontroller(phasetaskservice service) {
        this.service = service;
    }

    @GetMapping
    public List<phasetask> getAllPhaseTasks() {
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<phasetask> getPhaseTaskById(@PathVariable Long id) {
        return service.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<phasetask> createPhaseTask(@RequestBody phasetask task) {
        return ResponseEntity.ok(service.createTask(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<phasetask> updatePhaseTask(@PathVariable Long id, @RequestBody phasetask task) {
        try {
            return ResponseEntity.ok(service.updateTask(id, task));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhaseTask(@PathVariable Long id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
