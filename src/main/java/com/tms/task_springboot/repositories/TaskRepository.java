package com.tms.task_springboot.repositories;

import com.tms.task_springboot.dto.TaskDTO;
import com.tms.task_springboot.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByTitleContaining(String title);
}
