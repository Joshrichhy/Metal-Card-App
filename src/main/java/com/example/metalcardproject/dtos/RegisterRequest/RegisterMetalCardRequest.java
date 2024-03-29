package com.example.metalcardproject.dtos.RegisterRequest;

import com.example.metalcardproject.Data.Model.BankCard;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterMetalCardRequest {
    private String firstName;
    private String lastName;
    private String pin;
    private String cardNumber;
    private String cvv;
    private BankCard userCard;

}
