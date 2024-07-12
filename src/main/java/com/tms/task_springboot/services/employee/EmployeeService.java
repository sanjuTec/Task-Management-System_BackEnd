package com.tms.task_springboot.services.employee;

import com.tms.task_springboot.dto.TaskDTO;

import java.util.List;

public interface EmployeeService {

    List<TaskDTO> getTaskByUserId();

    TaskDTO updateTask(Long id,String status);
}
