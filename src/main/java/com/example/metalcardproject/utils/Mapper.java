package com.example.metalcardproject.utils;

import com.example.metalcardproject.Data.Model.BankCard;
import com.example.metalcardproject.Data.Model.MetalCard;
import com.example.metalcardproject.Data.Model.UserBankAccount;
import com.example.metalcardproject.Data.Model.UserProfile;
import com.example.metalcardproject.dtos.RegisterRequest.RegisterATMCardRequest;
import com.example.metalcardproject.dtos.RegisterRequest.RegisterMetalCardRequest;
import com.example.metalcardproject.dtos.RegisterRequest.RegisterUserBankAccount;
import com.example.metalcardproject.dtos.RegisterRequest.UserProfileRegisterRequest;
import com.example.metalcardproject.dtos.ResponseRequest.FindUserBankAccountResponse;

import java.util.Optional;

public class Mapper {
    public static UserBankAccount map(RegisterUserBankAccount registerUserBankAccount) {
        UserBankAccount userBankAccount = new UserBankAccount();
        userBankAccount.setFirstName(registerUserBankAccount.getFirstName());
        userBankAccount.setAccountNumber(registerUserBankAccount.getAccountNumber());
        userBankAccount.setLastName(registerUserBankAccount.getLastName());
        userBankAccount.setEmailAddress(registerUserBankAccount.getEmailAddress());
        userBankAccount.setDob(registerUserBankAccount.getDob());
        userBankAccount.setPassword(registerUserBankAccount.getPassword());
        userBankAccount.setPin(registerUserBankAccount.getPin());
       userBankAccount.setPhoneNumber(registerUserBankAccount.getPhoneNumber());
        return userBankAccount;
    }

    public static FindUserBankAccountResponse map(UserBankAccount userBankAccount) {
        FindUserBankAccountResponse findUserBankAccountResponse = new FindUserBankAccountResponse();
        findUserBankAccountResponse.setFullName(userBankAccount.getFirstName()+ " " + userBankAccount.getLastName());
        findUserBankAccountResponse.setEmailAddress(userBankAccount.getEmailAddress());
        findUserBankAccountResponse.setPhoneNumber(userBankAccount.getPhoneNumber());
       findUserBankAccountResponse.setAccountNumber(userBankAccount.getAccountNumber());
       findUserBankAccountResponse.setBalance(userBankAccount.getBalance());
       findUserBankAccountResponse.setDob(userBankAccount.getDob());
       return findUserBankAccountResponse;

    }


    public static void mapATMcard(RegisterATMCardRequest registerATMCardRequest, BankCard bankCard) {
        bankCard.setFirstName(registerATMCardRequest.getFirstName());
        bankCard.setLastName(registerATMCardRequest.getLastName());
        bankCard.setCardNumber(registerATMCardRequest.getCardNumber());
        bankCard.setCvv(registerATMCardRequest.getCvv());
        bankCard.setCardType(registerATMCardRequest.getCardType());
        bankCard.setPin(registerATMCardRequest.getPin());
        bankCard.setAccountNumber(registerATMCardRequest.getAccountNumber());

    }

    public static UserProfile mapUserProfile(UserProfileRegisterRequest userProfileRegisterRequest) {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(userProfileRegisterRequest.getFirstName());
        userProfile.setLastName(userProfileRegisterRequest.getLastName());
        userProfile.setEmailAddress(userProfileRegisterRequest.getEmailAddress());
        userProfile.setPhoneNumber(userProfileRegisterRequest.getPhoneNumber());
        userProfile.setPassword(userProfileRegisterRequest.getPassword());
        return userProfile;

    }

    public static MetalCard mapMetalCard(RegisterMetalCardRequest registerMetalCardRequest) {
        MetalCard metalCard = new MetalCard();
        metalCard.setFirstName(registerMetalCardRequest.getFirstName());
        metalCard.setLastName(registerMetalCardRequest.getLastName());
        metalCard.setUserCard(registerMetalCardRequest.getUserCard());
        metalCard.setPin(registerMetalCardRequest.getPin());
        metalCard.setCardNumber(registerMetalCardRequest.getCardNumber());
        metalCard.setCvv(registerMetalCardRequest.getCvv());
        metalCard.setAtmCardPin(registerMetalCardRequest.getUserCard().getPin());
        return metalCard;    }
}
