package com.evaloper.mini_bank.payload.request;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Size(min = 2, max = 125, message = "first name must be at least 2 characters")
    @NotBlank(message = "first name must not be blank")
    private String firstName;

    @Size(min = 2, max = 125, message = "last name must be at least 2 characters")
    @NotBlank(message = "last name must not be blank")
    private String lastName;
    @Size(min = 1, max = 125, message = "Other names must be at least 2 characters")
    @NotBlank(message = "Other names must not be blank")
    private String otherName;

    private String gender;

    @NotBlank(message = "address must not be blank")
    private String address;

    private String stateOfOrigin;

    @Size(min = 11, max = 11, message = "Phone number too long or short")
    @NotBlank(message = "Phone number is blank!")
    @Digits(fraction = 0, integer = 11, message = "Phone number is incorrect!")
    private String phoneNumber;

    private String alternativeNumber;

    @Size(max = 35)
    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email should not be blank!")
    private String email;

    @Size(min = 4, max = 25, message = "Password too short or long")
    @NotBlank(message = "Password cannot be blank!")
    private String password;

    // account number and account balance will be automatically generated for the user
    // user status will also be set to active automatically the account is created
}

