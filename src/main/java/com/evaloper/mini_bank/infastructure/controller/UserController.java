package com.evaloper.mini_bank.infastructure.controller;

import com.evaloper.mini_bank.domain.entities.UserEntity;
import com.evaloper.mini_bank.payload.request.*;
import com.evaloper.mini_bank.payload.response.BankResponse;
import com.evaloper.mini_bank.payload.response.NameAccountResponse;
import com.evaloper.mini_bank.payload.response.PhoneNumberResponse;
import com.evaloper.mini_bank.repository.UserRepository;
import com.evaloper.mini_bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/check-phone")
    public boolean validatePhoneNumber(@RequestParam String phoneNumber) {
        UserEntity user = userRepository.findByPhoneNumber(phoneNumber);
        return user != null;
    }

    @GetMapping("/balance-enquiry")
    public ResponseEntity<BankResponse> balanceEnquiry(@RequestParam String accountNumber) {
        EnquiryRequest request =  EnquiryRequest.builder().accountNumber(accountNumber).build();
        BankResponse response = userService.balanceEnquiry(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name-enquiry")
    public ResponseEntity<NameAccountResponse> nameEnquiry(@RequestParam String accountNumber) {
        EnquiryRequest request = EnquiryRequest.builder().accountNumber(accountNumber).build();
        NameAccountResponse response = userService.nameEnquiry(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name-account-enquiry/{phoneNumber}")
    public ResponseEntity<NameAccountResponse> nameAndAccountEnquiry(@PathVariable String phoneNumber) {
        EnquiryRequest request = EnquiryRequest.builder().phoneNumber(phoneNumber).build();
        NameAccountResponse response = userService.nameAndAccountEnquiry(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/phone-enquiry")
    public ResponseEntity<PhoneNumberResponse> phoneNumberEnquiry(@RequestBody PhoneNumberEnquiryRequest request) {
        PhoneNumberResponse response = userService.PhoneNumberEnquiry(request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/credit-account")
    public BankResponse creditAccount(@RequestBody CreditAndDebitRequest request){
        return userService.creditAccount(request);
    }

    @PostMapping("/debit-account")
    public BankResponse debitAccount(@RequestBody CreditAndDebitRequest request){
        return userService.debitAccount(request);
    }

    @PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest request){
        return userService.transfer(request);
    }

    @PostMapping("/buy-airtime")
    public BankResponse buyAirtime(@RequestBody AirtimeRequest request) {
        return userService.buyAirtime(request);
    }

    @PostMapping("/buy-data")
    public BankResponse buyData(@RequestBody DataRequest request) {
        return userService.buyData(request);
    }
}

