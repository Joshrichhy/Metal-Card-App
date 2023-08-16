package com.example.metalcardproject.Data.Repositories;

import com.example.metalcardproject.Data.Model.BankCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BankCardRepository extends MongoRepository <BankCard, String> {
   Optional<BankCard> findBankCardByCardNumber(String cardNumber);
}
