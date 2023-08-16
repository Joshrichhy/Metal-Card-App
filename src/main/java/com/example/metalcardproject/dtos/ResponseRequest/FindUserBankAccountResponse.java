package com.example.metalcardproject.dtos.ResponseRequest;

import com.example.metalcardproject.Data.Model.BankCard;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class FindUserBankAccountResponse {
    private String fullName;
    private String accountNumber;
    private String emailAddress;
    private String phoneNumber;
    private LocalDate dob;
    private BigDecimal balance = BigDecimal.ZERO;
}
