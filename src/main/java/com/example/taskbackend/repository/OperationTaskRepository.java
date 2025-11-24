package com.example.taskbackend.repository;

import com.example.taskbackend.model.OperationTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OperationTaskRepository extends JpaRepository<OperationTask, Long> {
    // All tasks under a specific project
    List<OperationTask> findByOperationProjectId(Long projectId);

    // For filtering in frontend
    List<OperationTask> findByOperationProject_ProjectName(String projectName);

    // Count tasks per project (for sidebar badge)
    @Query("SELECT COUNT(t) FROM OperationTask t WHERE t.operationProject.projectName = :projectName")
    Long countByProjectName(@Param("projectName") String projectName);

    // Overdue tasks (optional for badge)
    @Query("SELECT t FROM OperationTask t WHERE t.dueDate < :today AND t.status NOT IN ('Completed', 'Closed')")
    List<OperationTask> findOverdueTasks(@Param("today") LocalDate today);

    // Search tasks globally
    @Query("SELECT t FROM OperationTask t WHERE LOWER(t.taskName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<OperationTask> searchByTaskName(@Param("keyword") String keyword);
}
