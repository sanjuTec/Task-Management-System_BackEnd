package com.tms.task_springboot.services.employee;

import com.tms.task_springboot.dto.CommentDTO;
import com.tms.task_springboot.dto.TaskDTO;

import java.util.List;

public interface EmployeeService {

    List<TaskDTO> getTaskByUserId();

    TaskDTO updateTask(Long id,String status);
    TaskDTO getTaskById(Long id);

    CommentDTO createComment(Long taskId, String content);

    List<CommentDTO> getCommentsByTaskId(Long taskId);
}
