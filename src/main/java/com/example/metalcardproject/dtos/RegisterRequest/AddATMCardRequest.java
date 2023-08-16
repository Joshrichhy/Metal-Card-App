package com.example.metalcardproject.dtos.RegisterRequest;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AddATMCardRequest {
    private String emailAddress;
    private String cardNumber;
    private String cvv;
}
