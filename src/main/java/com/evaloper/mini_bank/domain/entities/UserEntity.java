package com.evaloper.mini_bank.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_tbl")
public class UserEntity extends BaseClass{
    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    private String address;
    private String stateOfOrigin;
    private String accountNumber;
    // we are using big decimal because of accuracy when it comes to currency
    private BigDecimal accountBalance;
    private String phoneNumber;
    private String alternativeNumber;
    @Email(message = "Invalid Email")
    private String email;
    private String password;
    private String status;

}
