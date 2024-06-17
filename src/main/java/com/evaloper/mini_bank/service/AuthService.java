package com.evaloper.mini_bank.service;

import com.evaloper.mini_bank.payload.request.CreditAndDebitRequest;
import com.evaloper.mini_bank.payload.request.UserRequest;
import com.evaloper.mini_bank.payload.response.BankResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    BankResponse registerUser(UserRequest userRequest);

    ResponseEntity login(CreditAndDebitRequest.LoginRequest loginRequest);
}
