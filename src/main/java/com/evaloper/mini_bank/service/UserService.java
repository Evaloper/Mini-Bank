package com.evaloper.mini_bank.service;

import com.evaloper.mini_bank.payload.request.*;
import com.evaloper.mini_bank.payload.response.BankResponse;
import com.evaloper.mini_bank.payload.response.PhoneNumberResponse;

public interface UserService {
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);

    PhoneNumberResponse PhoneNumberEnquiry(PhoneNumberEnquiryRequest request);

    BankResponse creditAccount(CreditAndDebitRequest request);

    BankResponse debitAccount(CreditAndDebitRequest request);

    BankResponse transfer(TransferRequest request);

    BankResponse buyAirtime(AirtimeRequest request);
    BankResponse buyData(DataRequest request);

}
