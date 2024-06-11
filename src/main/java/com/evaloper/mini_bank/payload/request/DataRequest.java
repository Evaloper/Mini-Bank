package com.evaloper.mini_bank.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataRequest {
    private String accountNumber;
    private String phoneNumber;
    private String dataAmount;

    public int getDataAmountInBytes(String dataAmount) {
        if (dataAmount == null) {
            throw new IllegalArgumentException("Data amount cannot be null");
        }
        int dataAmountInBytes = 0;
        if (dataAmount.endsWith("GB")) {
            dataAmountInBytes = Integer.parseInt(dataAmount.replace("GB", "")) * 1024;
        } else if (dataAmount.endsWith("MB")) {
            dataAmountInBytes = Integer.parseInt(dataAmount.replace("MB", ""));
        } else {
            throw new IllegalArgumentException("Invalid data amount format. Supported formats are GB and MB.");
        }
        return dataAmountInBytes;
    }
}
