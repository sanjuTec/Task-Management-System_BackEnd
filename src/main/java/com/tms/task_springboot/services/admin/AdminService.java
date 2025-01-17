package com.tms.task_springboot.services.admin;

import com.tms.task_springboot.dto.CommentDTO;
import com.tms.task_springboot.dto.TaskDTO;
import com.tms.task_springboot.dto.UserDto;

import java.util.List;

public interface AdminService {
    List<UserDto> getUsers();
    TaskDTO createTask(TaskDTO taskDTO);

    List<TaskDTO> getAllTasks();

    void deleteTask(Long id);

    TaskDTO getTaskById(Long id);

    TaskDTO updateTask(Long id, TaskDTO taskDTO);

    List<TaskDTO> searchTaskByTitle(String title);

    CommentDTO createComment(Long taskId, String content);

    List<CommentDTO> getCommentsByTaskId(Long taskId);

}
