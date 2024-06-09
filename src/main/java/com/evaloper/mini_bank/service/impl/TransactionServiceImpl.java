package com.evaloper.mini_bank.service.impl;

import com.evaloper.mini_bank.domain.entities.Transaction;
import com.evaloper.mini_bank.domain.enums.TransactionStatus;
import com.evaloper.mini_bank.payload.request.TransactionRequest;
import com.evaloper.mini_bank.repository.TransactionRepository;
import com.evaloper.mini_bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionRequest.getTransactionType())
                .accountNumber(transactionRequest.getAccountNumber())
                .amount(transactionRequest.getAmount())
                .status(TransactionStatus.COMPLETED)
                .build();

        transactionRepository.save(transaction);

        System.out.println("Transaction saved successfully");

    }
}
