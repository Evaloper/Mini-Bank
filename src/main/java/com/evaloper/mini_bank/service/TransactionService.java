package com.evaloper.mini_bank.service;

import com.evaloper.mini_bank.payload.request.TransactionRequest;

public interface TransactionService {
    void saveTransaction(TransactionRequest transactionRequest);
}
