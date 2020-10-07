package com.programming.blog.maliansdevelopersblog.Controller;

import com.programming.blog.maliansdevelopersblog.DTO.LoginRequest;
import com.programming.blog.maliansdevelopersblog.Service.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.programming.blog.maliansdevelopersblog.DTO.RegisterRequest;
import com.programming.blog.maliansdevelopersblog.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
