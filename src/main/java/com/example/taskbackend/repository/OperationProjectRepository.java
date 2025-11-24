package com.example.taskbackend.repository;

import com.example.taskbackend.model.OperationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperationProjectRepository extends JpaRepository<OperationProject, Long>{
    Optional<OperationProject> findByProjectName(String projectName);

    boolean existsByProjectName(String projectName);

    // Get all project names only (for sidebar dropdown)
    @Query("SELECT p.projectName FROM OperationProject p ORDER BY p.projectName")
    List<String> findAllProjectNames();

    // Optional: Search projects by name (for future search bar)
    List<OperationProject> findByProjectNameContainingIgnoreCase(String keyword);
}
