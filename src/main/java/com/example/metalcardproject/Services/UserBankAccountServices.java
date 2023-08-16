package com.example.metalcardproject.Services;

import com.example.metalcardproject.Data.Model.UserBankAccount;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import com.example.metalcardproject.dtos.RegisterRequest.RegisterUserBankAccount;
import com.example.metalcardproject.dtos.ResponseRequest.FindUserBankAccountResponse;

public interface UserBankAccountServices {
    String registerUserAccount(RegisterUserBankAccount registerUserBankAccount);

    FindUserBankAccountResponse findUserAccountWithAccountNumber(String accountNumber);

    long numbersOfAccounts();
    UserBankAccount findAccountByAccountNumber(String accountNumber) throws NoUserAccountException;
    void saveUpdate(UserBankAccount userBankAccount);

    Object findATMCardbyCardNumber(String accountNumber, String cardNumber);

    int countUserATMCards(String accountNumber);
}
