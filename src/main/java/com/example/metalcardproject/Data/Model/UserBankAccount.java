package com.example.metalcardproject.Data.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Document

public class UserBankAccount {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String accountNumber;
    private String password;
    private String emailAddress;
    private Set<BankCard> ATMCard = new HashSet<>();
    private String phoneNumber;
    private LocalDate dob;
    private String pin;
    private BigDecimal balance = BigDecimal.valueOf(0);

    public void setATMCard(BankCard ATMCard) {
        this.ATMCard.add(ATMCard);
    }
}
