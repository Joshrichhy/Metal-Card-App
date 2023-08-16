package com.example.metalcardproject.Services;

import com.example.metalcardproject.Data.Model.BankCard;
import com.example.metalcardproject.Exceptions.InsufficientFundException;
import com.example.metalcardproject.Exceptions.InvalidPinException;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import com.example.metalcardproject.dtos.RegisterRequest.RegisterATMCardRequest;

import java.math.BigDecimal;

public interface BankCardService {
    String requestATMCard(RegisterATMCardRequest registerATMCardRequest) throws NoUserAccountException;

    String withdrawWithATMCard(String cardNumber, String cvv, BigDecimal amount, String pin) throws NoUserAccountException, InvalidPinException, InsufficientFundException;

    String transferWithATMCard(String cardNumber, String cvv, String receiverAccountNumber, BigDecimal amount, String pin) throws NoUserAccountException, InsufficientFundException, InvalidPinException;

    BankCard findATMCard(String cardNumber);
}
