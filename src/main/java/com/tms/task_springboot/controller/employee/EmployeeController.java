package com.tms.task_springboot.controller.employee;

import com.tms.task_springboot.dto.TaskDTO;
import com.tms.task_springboot.services.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping(value = "/tasks")
    public ResponseEntity<List<TaskDTO>> getTaskByUserId(){
        return ResponseEntity.ok(employeeService.getTaskByUserId());
    }
}
