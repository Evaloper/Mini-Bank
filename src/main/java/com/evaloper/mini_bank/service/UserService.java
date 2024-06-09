package com.evaloper.mini_bank.service;

import com.evaloper.mini_bank.payload.request.CreditAndDebitRequest;
import com.evaloper.mini_bank.payload.request.EnquiryRequest;
import com.evaloper.mini_bank.payload.request.TransferRequest;
import com.evaloper.mini_bank.payload.response.BankResponse;

public interface UserService {
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);

    BankResponse creditAccount(CreditAndDebitRequest request);

    BankResponse debitAccount(CreditAndDebitRequest request);

    BankResponse transfer(TransferRequest request);

}
