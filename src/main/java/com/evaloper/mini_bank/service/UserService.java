package com.evaloper.mini_bank.service;

import com.evaloper.mini_bank.payload.request.*;
import com.evaloper.mini_bank.payload.response.BankResponse;
import com.evaloper.mini_bank.payload.response.NameAccountResponse;
import com.evaloper.mini_bank.payload.response.PhoneNumberResponse;

public interface UserService {
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    public NameAccountResponse nameEnquiry(EnquiryRequest enquiryRequest);
    public NameAccountResponse nameAndAccountEnquiry(EnquiryRequest enquiryRequest);

    PhoneNumberResponse PhoneNumberEnquiry(PhoneNumberEnquiryRequest request);

    BankResponse creditAccount(CreditAndDebitRequest request);

    BankResponse debitAccount(CreditAndDebitRequest request);

    BankResponse transfer(TransferRequest request);

    BankResponse buyAirtime(AirtimeRequest request);
    BankResponse buyData(DataRequest request);

}
