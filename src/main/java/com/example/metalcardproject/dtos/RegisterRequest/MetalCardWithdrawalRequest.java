package com.example.metalcardproject.dtos.RegisterRequest;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class MetalCardWithdrawalRequest {
    private String emailAddress;
    private String metalCardNumber;
    private String metalCardCvv;
    private String atmCardCvv;
    private String atmCardNumber;
    private String atmCardPin;
    private String metalCardPin;
    private BigDecimal amount;

}
