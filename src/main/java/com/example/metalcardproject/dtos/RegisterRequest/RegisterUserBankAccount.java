package com.example.metalcardproject.dtos.RegisterRequest;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RegisterUserBankAccount {
    private String firstName;
    private String lastName;
    private String AccountNumber;
    private String password;
    private String emailAddress;
    private String phoneNumber;
    private LocalDate dob;
    private String pin;
}
