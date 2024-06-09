package com.evaloper.mini_bank.service.impl;

import com.evaloper.mini_bank.domain.entities.UserEntity;
import com.evaloper.mini_bank.payload.LoginRequest;
import com.evaloper.mini_bank.payload.request.UserRequest;
import com.evaloper.mini_bank.payload.response.AccountInfo;
import com.evaloper.mini_bank.payload.response.BankResponse;
import com.evaloper.mini_bank.repository.UserRepository;
import com.evaloper.mini_bank.service.AuthService;
import com.evaloper.mini_bank.utils.AccountUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @Override
    public BankResponse registerUser(UserRequest userRequest) {
        // Register user implementation remains the same
        if(userRepository.existsByEmail(userRequest.getEmail())){
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        UserEntity newUser = UserEntity.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtil.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .password(userRequest.getPassword()) // Store the password as plain text (not recommended)
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativeNumber(userRequest.getAlternativeNumber())
                .status("ACTIVE")
                .build();

        UserEntity savedUser = userRepository.save(newUser);
        return BankResponse.builder()
                .responseCode(AccountUtil.ACCOUNT_CREATION_SUCCESS_CODE)
                .responseMessage(AccountUtil.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public ResponseEntity<BankResponse> login(LoginRequest loginRequest) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (!userEntityOptional.isPresent() ||
                !userEntityOptional.get().getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEntity<>(
                    BankResponse.builder()
                            .responseCode(AccountUtil.LOGIN_FAILURE_CODE)
                            .responseMessage("Invalid email or password")
                            .build(),
                    HttpStatus.UNAUTHORIZED
            );
        }

        UserEntity userEntity = userEntityOptional.get();
        return new ResponseEntity<>(
                BankResponse.builder()
                        .responseCode(AccountUtil.LOGIN_SUCCESS_CODE)
                        .responseMessage("Login successful")
                        .accountInfo(AccountInfo.builder()
                                .accountBalance(userEntity.getAccountBalance())
                                .accountNumber(userEntity.getAccountNumber())
                                .accountName(userEntity.getFirstName() + " " + userEntity.getLastName() + " " + userEntity.getOtherName())
                                .build())
                        .build(),
                HttpStatus.OK
        );
    }
}
