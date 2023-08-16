package com.example.metalcardproject.dtos.RegisterRequest;

import com.example.metalcardproject.Data.Model.CardType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterATMCardRequest {
    private String firstName;
    private String lastName;
    private String accountNumber;
    private CardType cardType;
    private String pin;
    private String cardNumber;
    private String cvv;
}
