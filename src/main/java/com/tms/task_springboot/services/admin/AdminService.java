package com.tms.task_springboot.services.admin;

import com.tms.task_springboot.dto.TaskDTO;
import com.tms.task_springboot.dto.UserDto;

import java.util.List;

public interface AdminService {
    List<UserDto> getUsers();
    TaskDTO createTask(TaskDTO taskDTO);

    List<TaskDTO> getAllTasks();

    void deleteTask(Long id);

}
