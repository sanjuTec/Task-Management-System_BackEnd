package com.tms.task_springboot.services.auth;

import com.tms.task_springboot.dto.SignUpRequest;
import com.tms.task_springboot.dto.UserDto;

public interface AuthService {
    UserDto signupUser(SignUpRequest signUpRequest);
    boolean hasUserWithEmail(String email);
}
