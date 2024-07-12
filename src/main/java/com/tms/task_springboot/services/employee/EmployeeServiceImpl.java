package com.tms.task_springboot.services.employee;

import com.tms.task_springboot.dto.TaskDTO;
import com.tms.task_springboot.entities.Task;
import com.tms.task_springboot.entities.User;
import com.tms.task_springboot.enums.TaskStatus;
import com.tms.task_springboot.repositories.TaskRepository;
import com.tms.task_springboot.utils.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    @Override
    public TaskDTO updateTask(Long id, String status) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task existingTask = optionalTask.get();
            existingTask.setTaskStatus(mapStringToTaskStatus(status));
            return taskRepository.save(existingTask).getTaskDTO();
        }
        throw new EntityNotFoundException("Task not found");
    }

    private TaskStatus mapStringToTaskStatus(String status){
        return switch (status){
            case "INPROGRESS" -> TaskStatus.INPROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "PENDING" -> TaskStatus.PENDING;
            case "DEFERRED" -> TaskStatus.DEFERRED;
            default -> TaskStatus.CANCELLED;
        };
    }
}
