package com.example.metalcardproject.Data.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
@Document
public class UserProfile {
    @Id
    private String id;
    private String firstName;
    private String lastName;
   private String password;
    private String emailAddress;
    private Set<BankCard> cards = new TreeSet<>();
    private String phoneNumber;
    private String pin;
    private MetalCard metalCard;
    private Set<Role> roles;

    public void setCards(BankCard bankCard){
        cards.add(bankCard);
    }
}
