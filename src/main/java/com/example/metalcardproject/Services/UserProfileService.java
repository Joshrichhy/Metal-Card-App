package com.example.metalcardproject.Services;

import com.example.metalcardproject.Data.Model.BankCard;
import com.example.metalcardproject.Data.Model.UserProfile;
import com.example.metalcardproject.Exceptions.InsufficientFundException;
import com.example.metalcardproject.Exceptions.InvalidPinException;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import com.example.metalcardproject.Exceptions.UserExistsException;
import com.example.metalcardproject.dtos.RegisterRequest.AddATMCardRequest;
import com.example.metalcardproject.dtos.RegisterRequest.MetalCardTransferRequest;
import com.example.metalcardproject.dtos.RegisterRequest.MetalCardWithdrawalRequest;
import com.example.metalcardproject.dtos.RegisterRequest.UserProfileRegisterRequest;

public interface UserProfileService {
    String registerUser(UserProfileRegisterRequest userProfileRegisterRequest) throws UserExistsException;

    long countUsers();

    String addATMCard(AddATMCardRequest addATMCardRequest);

    int countATMCard(String emailAddress);

    BankCard findAtmCard(String emailAddress, String cardNumber);
    UserProfile findUserByEmailAddress(String emailAddress) throws NoUserAccountException;
    String saveUserUpdate(UserProfile userProfile);

    String withdrawWithMetalCard(MetalCardWithdrawalRequest metalCardWithdrawalRequest) throws NoUserAccountException, InsufficientFundException, InvalidPinException;

    String transferWithMetalCard(MetalCardTransferRequest metalCardTransferRequest) throws NoUserAccountException, InsufficientFundException, InvalidPinException;
}
