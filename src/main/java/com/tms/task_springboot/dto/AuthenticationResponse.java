package com.tms.task_springboot.dto;

import com.tms.task_springboot.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private UserRole userRole;

}
