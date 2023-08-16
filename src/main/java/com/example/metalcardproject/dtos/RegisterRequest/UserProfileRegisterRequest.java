package com.example.metalcardproject.dtos.RegisterRequest;

import com.example.metalcardproject.Data.Model.BankCard;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Builder
@Data
public class UserProfileRegisterRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String emailAddress;
    private List<BankCard> cards;
    private String phoneNumber;

}
