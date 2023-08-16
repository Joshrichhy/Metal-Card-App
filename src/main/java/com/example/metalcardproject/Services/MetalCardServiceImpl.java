package com.example.metalcardproject.Services;

import com.example.metalcardproject.Data.Model.BankCard;
import com.example.metalcardproject.Data.Model.MetalCard;
import com.example.metalcardproject.Data.Model.UserProfile;
import com.example.metalcardproject.Data.Repositories.MetalCardRepository;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import com.example.metalcardproject.dtos.RegisterRequest.RegisterMetalCardRequest;
import com.example.metalcardproject.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MetalCardServiceImpl implements MetalCardService {
    @Autowired
    private MetalCardRepository metalCardRepository;

    @Autowired
    private UserProfileService userProfileService;

    private Random random = new Random();
    @Override
    public String requestMetalCard(RegisterMetalCardRequest registerMetalCardRequest, String emailAddress) throws NoUserAccountException {
        registerMetalCardRequest.setCardNumber(generateCardNumber());
        registerMetalCardRequest.setCvv(generateCvv());
       MetalCard metalCard = metalCardRepository.save(Mapper.mapMetalCard(registerMetalCardRequest));
       UserProfile userProfile = userProfileService.findUserByEmailAddress(emailAddress);
       userProfile.setMetalCard(metalCard);
      userProfileService.saveUserUpdate(userProfile);
       return "MetalCard Request Successful";
    }

    @Override
    public long countMetalCards() {
        return metalCardRepository.count();
    }

    @Override
    public String changeAtmCardOnMetalCard(String emailAddress, BankCard atmCard) throws NoUserAccountException {
        UserProfile foundUser = userProfileService.findUserByEmailAddress(emailAddress);
        MetalCard metalCard =  foundUser.getMetalCard();
        metalCard.setUserCard(atmCard);
        metalCard.setAtmCardPin(atmCard.getPin());
        metalCardRepository.save(metalCard);
        userProfileService.saveUserUpdate(foundUser);
        return "Swap Successful";
    }

    @Override
    public BankCard findAtmCardOnMetalCards(String emailAddress) throws NoUserAccountException {
        UserProfile foundUser = userProfileService.findUserByEmailAddress(emailAddress);
        MetalCard metalCard =  foundUser.getMetalCard();
        return metalCard.getUserCard();
    }

    @Override
    public MetalCard findMetalCardWithMetalCardNumberAndCvv(String metalCardNumber, String metalCardCvv) throws NoUserAccountException {
        MetalCard metalCard = metalCardRepository.findByCardNumberAndCvv(metalCardNumber, metalCardCvv);
        if(metalCard == null){throw  new NoUserAccountException("Invalid card details");}
        return metalCard;
    }

    private String generateCvv() {
        StringBuilder cardNumber = new StringBuilder();
        for(int i = 0 ;  i < 3 ; i++) cardNumber.append(random.nextInt(10));
        return cardNumber.toString();

    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for(int i = 0 ;  i < 10 ; i++) cardNumber.append(random.nextInt(10));
        return cardNumber.toString();
    }
}
