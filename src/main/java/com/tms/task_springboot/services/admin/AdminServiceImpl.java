package com.tms.task_springboot.services.admin;

import com.tms.task_springboot.dto.TaskDTO;
import com.tms.task_springboot.dto.UserDto;
import com.tms.task_springboot.entities.Task;
import com.tms.task_springboot.entities.User;
import com.tms.task_springboot.enums.TaskStatus;
import com.tms.task_springboot.enums.UserRole;
import com.tms.task_springboot.repositories.TaskRepository;
import com.tms.task_springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().filter(user ->
                user.getUserRole() == UserRole.EMPLOYEE)
                .map(User::getUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Optional<User> optionalUser = userRepository.findById(taskDTO.getEmployeeId());
        if(optionalUser.isPresent()){
            Task task = new Task();
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setPriority(taskDTO.getPriority());
            task.setDueDate(taskDTO.getDueDate());
            task.setTaskStatus(TaskStatus.INPROGRESS);
            task.setUser(optionalUser.get());
           return taskRepository.save(task).getTaskDTO();

        }
        return null;
    }


}