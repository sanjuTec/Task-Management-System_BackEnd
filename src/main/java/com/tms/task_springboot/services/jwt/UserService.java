package com.tms.task_springboot.services.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
}