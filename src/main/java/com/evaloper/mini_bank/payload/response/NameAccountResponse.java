package com.evaloper.mini_bank.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NameAccountResponse {
    private String responseCode;
    private String responseMessage;
    private String firstName;
    private String lastName;
    private String accountNumber;

}
