package com.example.metalcardproject.Services;

import com.example.metalcardproject.Data.Model.UserBankAccount;
import com.example.metalcardproject.Exceptions.InsufficientFundException;
import com.example.metalcardproject.Exceptions.InvalidPinException;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import com.example.metalcardproject.dtos.RegisterRequest.RegisterATMCardRequest;

import java.math.BigDecimal;

public interface BankService {
    String deposit(String accountNumber, BigDecimal amount) throws NoUserAccountException;

    BigDecimal checkBalance(String accountNumber, String pin) throws NoUserAccountException;

    String withdraw(String accountNumber, BigDecimal amount, String pin) throws InsufficientFundException, NoUserAccountException, InvalidPinException;

    String transfer(String senderAccountNumber, BigDecimal amount, String receiverAccountNumber, String senderPin) throws InsufficientFundException, NoUserAccountException, InvalidPinException;

}
