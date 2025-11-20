package com.example.taskbackend.repository;

import com.example.taskbackend.model.phasetask;
import org.springframework.data.jpa.repository.JpaRepository;
public interface phasetaskrepo extends JpaRepository<phasetask, Long>{
}
