package com.example.metalcardproject.Services;

import com.example.metalcardproject.Data.Model.BankCard;
import com.example.metalcardproject.Data.Model.UserBankAccount;
import com.example.metalcardproject.Data.Repositories.BankCardRepository;
import com.example.metalcardproject.Exceptions.InsufficientFundException;
import com.example.metalcardproject.Exceptions.InvalidPinException;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import com.example.metalcardproject.dtos.RegisterRequest.RegisterATMCardRequest;
import com.example.metalcardproject.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Random;

@Service
public class BankCardServiceImpl implements BankCardService{
    @Autowired
    private UserBankAccountServices userBankAccountServices;

    private Random random = new Random();

    @Autowired
    private BankCardRepository bankCardRepository;

    Long cardNumber = 20L;
    int cvv = 10;

    public String requestATMCard(RegisterATMCardRequest registerATMCardRequest) throws NoUserAccountException {
        UserBankAccount userBankAccount =  userBankAccountServices.findAccountByAccountNumber(registerATMCardRequest.getAccountNumber());
        registerATMCardRequest.setCardNumber(generateCardNumber());
        registerATMCardRequest.setCvv(generateCvv());
        BankCard bankCard = new BankCard();
        Mapper.mapATMcard(registerATMCardRequest, bankCard);
        userBankAccount.setATMCard(bankCard);
        userBankAccountServices.saveUpdate(userBankAccount);
        bankCardRepository.save(bankCard);
        return "Request Successful";
    }

    @Override
    public String withdrawWithATMCard(String cardNumber, String cvv, BigDecimal amount, String pin) throws NoUserAccountException, InvalidPinException, InsufficientFundException {
        BankCard bankCard = bankCardRepository.findBankCardByCardNumber(cardNumber).orElseThrow(()
                -> new IllegalArgumentException("invalid card number"));
        if(!bankCard.getPin().equals(pin)){throw new InvalidPinException("Pin incorrect");}
        UserBankAccount userBankAccount = userBankAccountServices.findAccountByAccountNumber(bankCard.getAccountNumber());
        if(userBankAccount.getBalance().compareTo(amount) < 0){
            throw  new InsufficientFundException("Your balance is too low, your balance is currently %s" + userBankAccount.getBalance());}
        userBankAccount.setBalance(userBankAccount.getBalance().subtract(amount));
        userBankAccountServices.saveUpdate(userBankAccount);
        return "Withdrawal Succesful";
    }

    @Override
    public String transferWithATMCard(String cardNumber, String cvv, String receiverAccountNumber, BigDecimal amount, String pin) throws NoUserAccountException, InsufficientFundException, InvalidPinException {
        UserBankAccount receiverAccount = userBankAccountServices.findAccountByAccountNumber(receiverAccountNumber);
        withdrawWithATMCard(cardNumber, cvv,amount,pin);
        receiverAccount.setBalance(receiverAccount.getBalance().add(amount));
        userBankAccountServices.saveUpdate(receiverAccount);
        return "Transfer Successful";
    }

    @Override
    public BankCard findATMCard(String cardNumber) {
        BankCard bankCard = bankCardRepository.findBankCardByCardNumber(cardNumber).orElseThrow(()
                -> new IllegalArgumentException("invalid card number"));
    return bankCard;
    }

    private String generateCvv() {
        cvv*=3;
        return ""+cvv;
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for(int i = 0 ;  i < 10 ; i++) cardNumber.append(random.nextInt(10));
        System.out.println(cardNumber);
        return cardNumber.toString();
    }
}
