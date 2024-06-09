package com.evaloper.mini_bank.payload.response;

import com.evaloper.mini_bank.domain.entities.ErrorDetails;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class BankResponse<T> {

    private  String responseCode;
    private String responseMessage;
    private AccountInfo accountInfo;

    public BankResponse(String message) {
        this.responseMessage = message;
    }

    public BankResponse(String responseCode, String responseMessage, AccountInfo accountInfo) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.accountInfo = accountInfo;
    }

    public BankResponse(String message, ErrorDetails errorDetail) {
        this.responseMessage = message;
    }

    public BankResponse(String message, String fileUrl) {
        this.responseMessage = message;
    }
}
