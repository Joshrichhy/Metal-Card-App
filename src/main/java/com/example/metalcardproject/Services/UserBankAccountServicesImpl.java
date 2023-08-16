package com.example.metalcardproject.Services;

import com.example.metalcardproject.Data.Model.BankCard;
import com.example.metalcardproject.Data.Model.UserBankAccount;
import com.example.metalcardproject.Data.Repositories.UserBankAccountRepository;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import com.example.metalcardproject.dtos.RegisterRequest.RegisterUserBankAccount;
import com.example.metalcardproject.dtos.ResponseRequest.FindUserBankAccountResponse;
import com.example.metalcardproject.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

@Service
public class UserBankAccountServicesImpl implements UserBankAccountServices {
    @Autowired
   private UserBankAccountRepository userBankAccountRepository;
    int count = 0;

    @Override
    public String registerUserAccount(RegisterUserBankAccount registerUserBankAccount) {
        registerUserBankAccount.setAccountNumber(generateAccountNumber());
       userBankAccountRepository.save(Mapper.map(registerUserBankAccount));
        return "Registration Successful";
    }

    @Override
    public FindUserBankAccountResponse findUserAccountWithAccountNumber(String accountNumber) {
        UserBankAccount foundAccount = userBankAccountRepository.findByAccountNumber(accountNumber);
       return Mapper.map(foundAccount);

    }

    @Override
    public long numbersOfAccounts() {
        return userBankAccountRepository.count();
    }

    @Override
    public UserBankAccount findAccountByAccountNumber(String accountNumber) throws NoUserAccountException {
        UserBankAccount foundAccount = userBankAccountRepository.findByAccountNumber(accountNumber);
        if(foundAccount==null){
            throw  new NoUserAccountException("Account does not exist");
        }
        return userBankAccountRepository.findByAccountNumber(accountNumber);
    }

    private String generateAccountNumber() {
        count++;
        return "2023"+count;
    }
    public void saveUpdate(UserBankAccount userBankAccount){
        userBankAccountRepository.save(userBankAccount);
    }

    @Override
    public Object findATMCardbyCardNumber(String accountNumber, String cardNumber) {
        UserBankAccount userBankAccount = userBankAccountRepository.findByAccountNumber(accountNumber);
        Set<BankCard> cards = userBankAccount.getATMCard();
        for (BankCard bankCard: cards) {
            if (bankCard.getCardNumber().equals(cardNumber)) return bankCard;
        }
        return null;
        //cards.forEach(bankCard -> {if (bankCard.getCardNumber().equals(cardNumber))});
    }

    @Override
    public int countUserATMCards(String accountNumber) {
        return userBankAccountRepository.findByAccountNumber(accountNumber).getATMCard().size();
    }
}
