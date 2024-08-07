package com.evaloper.mini_bank.payload.request;

import com.evaloper.mini_bank.domain.enums.NetworkProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirtimeRequest {
    private String accountNumber;
    private String phoneNumber;
    private BigDecimal amount;
    private NetworkProvider network;
}
