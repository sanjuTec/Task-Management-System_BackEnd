package com.tms.task_springboot.services.employee;

import com.tms.task_springboot.dto.TaskDTO;
import com.tms.task_springboot.entities.Task;
import com.tms.task_springboot.entities.User;
import com.tms.task_springboot.repositories.TaskRepository;
import com.tms.task_springboot.utils.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final TaskRepository taskRepository;
    private final JwtUtils jwtUtils;

    @Override
    public List<TaskDTO> getTaskByUserId() {
        User user = jwtUtils.getLoggedInUser();
        if(user != null){
            return taskRepository.findAllByUserId(user.getId())
                    .stream()
                    .sorted(Comparator.comparing(Task::getDueDate).reversed())
                    .map(Task::getTaskDTO)
                    .collect(Collectors.toList());
        }
        throw  new EntityNotFoundException("User not found");
    }
}
