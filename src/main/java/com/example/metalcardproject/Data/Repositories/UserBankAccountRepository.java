package com.example.metalcardproject.Data.Repositories;

import com.example.metalcardproject.Data.Model.UserBankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserBankAccountRepository extends MongoRepository <UserBankAccount, String> {
    UserBankAccount findByAccountNumber(String accountNumber);
}
