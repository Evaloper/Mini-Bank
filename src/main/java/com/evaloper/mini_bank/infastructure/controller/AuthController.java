package com.evaloper.mini_bank.infastructure.controller;

import com.evaloper.mini_bank.payload.LoginRequest;
import com.evaloper.mini_bank.payload.request.UserRequest;
import com.evaloper.mini_bank.payload.response.BankResponse;
import com.evaloper.mini_bank.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BankResponse> register(@RequestBody UserRequest userRequest) {
        BankResponse response = authService.registerUser(userRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<BankResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
