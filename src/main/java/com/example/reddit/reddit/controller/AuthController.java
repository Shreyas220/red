package com.example.reddit.reddit.controller;


import com.example.reddit.reddit.dto.LoginRequest;
import com.example.reddit.reddit.dto.RegisterRequest;
import com.example.reddit.reddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;


    //signing up the new user
    @PostMapping("/singup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Succesful", HttpStatus.OK);
    }

    //Verifying user
    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token, GeneralSecurityException e){
        authService.verifyAccount(token, e);
        return new ResponseEntity<>("Account activated successfully",HttpStatus.OK);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest){
        authService.login(loginRequest);
        return;authService.login(loginRequest);
    }


}
