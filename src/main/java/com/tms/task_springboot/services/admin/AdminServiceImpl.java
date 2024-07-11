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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Optional;

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

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(Task::getTaskDTO).orElse(null);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(taskDTO.getEmployeeId());
        if(optionalTask.isPresent() && optionalUser.isPresent()){
            Task existingTask = optionalTask.get();
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setDescription(taskDTO.getDescription());
            existingTask.setDueDate(taskDTO.getDueDate());
            existingTask.setPriority(taskDTO.getPriority());
            existingTask.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskDTO.getTaskStatus())));
            existingTask.setUser(optionalUser.get());
            return taskRepository.save(existingTask).getTaskDTO();
        }
        return null;
    }

    @Override
    public List<TaskDTO> searchTaskByTitle(String title) {
        return taskRepository.findAllByTitleContaining(title)
                .stream()
                .map(Task::getTaskDTO)
                .sorted(Comparator.comparing(TaskDTO::getDueDate).reversed())
                .collect(Collectors.toList());
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
