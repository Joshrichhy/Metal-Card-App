package com.example.metalcardproject.Services;

import com.example.metalcardproject.Data.Model.BankCard;
import com.example.metalcardproject.Data.Model.MetalCard;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import com.example.metalcardproject.dtos.RegisterRequest.RegisterMetalCardRequest;

public interface MetalCardService {
    String requestMetalCard(RegisterMetalCardRequest registerMetalCardRequest, String emailAddress) throws NoUserAccountException;

    long countMetalCards();

    String changeAtmCardOnMetalCard(String emailAddress, BankCard atmCard) throws NoUserAccountException;

    BankCard findAtmCardOnMetalCards(String emailAddress) throws NoUserAccountException;

    MetalCard findMetalCardWithMetalCardNumberAndCvv(String metalCardNumber, String metalCardCvv) throws NoUserAccountException;
}
