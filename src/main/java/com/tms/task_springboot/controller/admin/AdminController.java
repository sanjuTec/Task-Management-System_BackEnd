package com.tms.task_springboot.controller.admin;

import com.tms.task_springboot.dto.CommentDTO;
import com.tms.task_springboot.dto.TaskDTO;
import com.tms.task_springboot.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    @GetMapping(value = "/users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(adminService.getUsers());
    }

    @PostMapping(value = "/task")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
        TaskDTO createdTaskDTO = adminService.createTask(taskDTO);
        if(createdTaskDTO == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDTO);
        }
    }

    @GetMapping(value = "/tasks")
    public ResponseEntity<?> getAllTasks(){
        return ResponseEntity.ok(adminService.getAllTasks());
    }

    @DeleteMapping(value = "/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        adminService.deleteTask(id);
        return ResponseEntity.ok(null);
    }
    @GetMapping(value = "/task/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getTaskById(id));
    }

    @PutMapping(value = "/task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id,@RequestBody TaskDTO taskDTO){
        TaskDTO updatedTask = adminService.updateTask(id,taskDTO);
        if(updatedTask == null){
            return ResponseEntity.notFound().build();
        }else {
            return  ResponseEntity.ok(updatedTask);
        }
    }

    @GetMapping(value = "/task/search/{title}")
    public ResponseEntity<List<TaskDTO>> searchTask(@PathVariable String title){
        return ResponseEntity.ok(adminService.searchTaskByTitle(title));
    }

    @PostMapping(value = "/task/comment/{taskId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long taskId, @RequestParam String content ){
        CommentDTO createdCommentDTO = adminService.createComment(taskId,content);
        if(createdCommentDTO == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCommentDTO);
        }
    }


}
