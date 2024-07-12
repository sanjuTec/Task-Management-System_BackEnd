package com.tms.task_springboot.controller.employee;

import com.tms.task_springboot.dto.TaskDTO;
import com.tms.task_springboot.services.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/employee")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping(value = "/tasks")
    public ResponseEntity<List<TaskDTO>> getTaskByUserId(){
        return ResponseEntity.ok(employeeService.getTaskByUserId());
    }

    @GetMapping(value = "/task/{id}/{status}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @PathVariable String status){
        TaskDTO updatedTaskDTO = employeeService.updateTask(id, status);
        if(updatedTaskDTO == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.ok(updatedTaskDTO);
        }

    }
}
