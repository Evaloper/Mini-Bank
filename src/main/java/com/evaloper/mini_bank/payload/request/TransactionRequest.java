package com.evaloper.mini_bank.payload.request;

import com.evaloper.mini_bank.domain.enums.TransactionStatus;
import com.evaloper.mini_bank.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private TransactionType transactionType;
    private String accountNumber;
    private BigDecimal amount;
    private TransactionStatus status;
}
