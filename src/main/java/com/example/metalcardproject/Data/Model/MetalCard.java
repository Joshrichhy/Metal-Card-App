package com.example.metalcardproject.Data.Model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class MetalCard {
    @Id
    private String id;
    private String cardNumber;
    private String cvv;
    private String firstName;
    private String lastName;
    private String pin;
    private String atmCardPin;
    private BankCard userCard;

}
