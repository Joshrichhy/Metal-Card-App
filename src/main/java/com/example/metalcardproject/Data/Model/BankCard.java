package com.example.metalcardproject.Data.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class BankCard {
    @Id
    private String id;
    private String accountNumber;
    @Indexed(unique = true)
    private String cardNumber;
    private String cvv;
    private String firstName;
    private String lastName;
    private CardType cardType;
    private String pin;
}
