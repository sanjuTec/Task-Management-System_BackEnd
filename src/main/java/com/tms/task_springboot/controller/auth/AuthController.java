package com.tms.task_springboot.controller.auth;

import com.tms.task_springboot.dto.AuthenticationRequest;
import com.tms.task_springboot.dto.AuthenticationResponse;
import com.tms.task_springboot.dto.SignUpRequest;
import com.tms.task_springboot.dto.UserDto;
import com.tms.task_springboot.entities.User;
import com.tms.task_springboot.repositories.UserRepository;
import com.tms.task_springboot.services.auth.AuthService;
import com.tms.task_springboot.services.jwt.UserService;
import com.tms.task_springboot.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignUpRequest signUpRequest){
        if(authService.hasUserWithEmail(signUpRequest.getEmail())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                                 .body("User already exist with this email");
        }
        UserDto createUserDTO = authService.signupUser(signUpRequest);
        if(createUserDTO == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("User not created");
        }
        return  ResponseEntity.status(HttpStatus.CREATED)
                              .body(createUserDTO);

    }

    @PostMapping(value = "/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwtToken = jwtUtils.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;

    }
}
