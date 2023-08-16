package com.example.metalcardproject.Services;

import com.example.metalcardproject.Data.Model.BankCard;
import com.example.metalcardproject.Data.Model.UserBankAccount;
import com.example.metalcardproject.Data.Repositories.UserBankAccountRepository;
import com.example.metalcardproject.Exceptions.InsufficientFundException;
import com.example.metalcardproject.Exceptions.InvalidPinException;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import com.example.metalcardproject.dtos.RegisterRequest.RegisterATMCardRequest;
import com.example.metalcardproject.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

@Service
public class BankServiceImpl implements BankService{

   @Autowired
   UserBankAccountServices userBankAccountServices;

    @Override
    public String deposit(String accountNumber, BigDecimal amount) throws NoUserAccountException {
        UserBankAccount foundAccount = userBankAccountServices.findAccountByAccountNumber(accountNumber);
        foundAccount.setBalance(foundAccount.getBalance().add(amount));
        userBankAccountServices.saveUpdate(foundAccount);
        return "You have successfully deposited";
    }


    @Override
    public BigDecimal checkBalance(String accountNumber, String pin) throws NoUserAccountException {
        UserBankAccount foundAccount = userBankAccountServices.findAccountByAccountNumber(accountNumber);
        if(foundAccount.getPin().equals(pin)){ return foundAccount.getBalance();}
        else return null;
    }

    @Override
    public String withdraw(String accountNumber, BigDecimal amount, String pin) throws InsufficientFundException, NoUserAccountException, InvalidPinException {
        UserBankAccount foundAccount = userBankAccountServices.findAccountByAccountNumber(accountNumber);
        if(foundAccount.getBalance().compareTo(amount) < 0){
            throw  new InsufficientFundException("Your balance is too low, your balance is currently %s" + foundAccount.getBalance());}
        if(!foundAccount.getPin().equals(pin)){
            throw new InvalidPinException("Invalid pin");}
        foundAccount.setBalance(foundAccount.getBalance().subtract(amount));
        userBankAccountServices.saveUpdate(foundAccount);
        return "Withdrawal successful";
    }

    @Override
    public String transfer(String senderAccountNumber, BigDecimal amount, String receiverAccountNumber, String senderPin) throws InsufficientFundException, NoUserAccountException, InvalidPinException {
        UserBankAccount foundSenderAccount = userBankAccountServices.findAccountByAccountNumber(senderAccountNumber);
        UserBankAccount foundReceiverAccount = userBankAccountServices.findAccountByAccountNumber(receiverAccountNumber);
        withdraw(senderAccountNumber, amount, senderPin);
        deposit(receiverAccountNumber, amount);
        return "Transfer Successful";
    }

}
