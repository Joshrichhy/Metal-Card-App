package com.example.metalcardproject.Services;

import com.example.metalcardproject.Data.Model.BankCard;
import com.example.metalcardproject.Data.Model.MetalCard;
import com.example.metalcardproject.Data.Model.UserBankAccount;
import com.example.metalcardproject.Data.Model.UserProfile;
import com.example.metalcardproject.Data.Repositories.UserProfileRepository;
import com.example.metalcardproject.Exceptions.InsufficientFundException;
import com.example.metalcardproject.Exceptions.InvalidPinException;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import com.example.metalcardproject.Exceptions.UserExistsException;
import com.example.metalcardproject.dtos.RegisterRequest.AddATMCardRequest;
import com.example.metalcardproject.dtos.RegisterRequest.MetalCardTransferRequest;
import com.example.metalcardproject.dtos.RegisterRequest.MetalCardWithdrawalRequest;
import com.example.metalcardproject.dtos.RegisterRequest.UserProfileRegisterRequest;
import com.example.metalcardproject.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserProfileServiceImpl  implements  UserProfileService{
    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserBankAccountServices userBankAccountServices;

    @Autowired
    private BankService bankService;


    @Override
    public String registerUser(UserProfileRegisterRequest userProfileRegisterRequest) throws UserExistsException {
        if(userProfileRepository.existsByEmailAddress(userProfileRegisterRequest.getEmailAddress())) throw new UserExistsException("User already exists");
        userProfileRepository.save(Mapper.mapUserProfile(userProfileRegisterRequest));
        return "Registration Successful";
    }

    @Override
    public long countUsers() {
        return userProfileRepository.count();
    }

    @Override
    public String addATMCard(AddATMCardRequest addATMCardRequest) {
        if(userProfileRepository.existsByEmailAddress(addATMCardRequest.getEmailAddress())){
          BankCard aTMCard =  bankCardService.findATMCard(addATMCardRequest.getCardNumber());
            UserProfile userProfile = userProfileRepository.findByEmailAddress(addATMCardRequest.getEmailAddress());
            userProfile.setCards(aTMCard);
            userProfileRepository.save(userProfile);
            return "Card Added Successfully";
        }
        return "Card Added not Successful";
    }

    @Override
    public int countATMCard(String emailAddress) {
       return userProfileRepository.findByEmailAddress(emailAddress).getCards().size();
    }

    @Override
    public BankCard findAtmCard(String emailAddress, String cardNumber) {
        UserProfile user = userProfileRepository.findByEmailAddress(emailAddress);
        for (BankCard card: user.getCards()) {
            if(card.getCardNumber().equals(cardNumber)) return card;}
        return null;
    }

    @Override
    public UserProfile findUserByEmailAddress(String emailAddress) throws NoUserAccountException {
        UserProfile user = userProfileRepository.findByEmailAddress(emailAddress);
        if(user == null){throw new NoUserAccountException("User does not exist");}
        return user;
    }

    public String saveUserUpdate(UserProfile userProfile){
        userProfileRepository.save(userProfile);
        return "Update Successful";
    }

    @Override
    public String withdrawWithMetalCard(MetalCardWithdrawalRequest metalCardWithdrawalRequest) throws NoUserAccountException, InsufficientFundException, InvalidPinException {
       MetalCard metalCard = userProfileRepository.findByEmailAddress(metalCardWithdrawalRequest.getEmailAddress()).getMetalCard();
       if (Objects.equals(metalCard.getCardNumber(), metalCardWithdrawalRequest.getMetalCardNumber()) && Objects.equals(metalCard.getCvv(), metalCardWithdrawalRequest.getMetalCardCvv())
       && Objects.equals(metalCard.getPin(), metalCardWithdrawalRequest.getMetalCardPin())){
           bankCardService.withdrawWithATMCard(metalCard.getUserCard().getCardNumber(),metalCard.getUserCard().getCvv(),
                    metalCardWithdrawalRequest.getAmount(),metalCardWithdrawalRequest.getAtmCardPin());

            return "Withdrawal Successful";
       }

        return null;}

    @Override
    public String transferWithMetalCard(MetalCardTransferRequest metalCardTransferRequest) throws NoUserAccountException, InsufficientFundException, InvalidPinException {
        UserBankAccount foundReceiverAccount = userBankAccountServices.findAccountByAccountNumber(metalCardTransferRequest.getReceiverAccountNumber());
        MetalCard metalCard = userProfileRepository.findByEmailAddress(metalCardTransferRequest.getEmailAddress()).getMetalCard();
        if (Objects.equals(metalCard.getCardNumber(), metalCardTransferRequest.getMetalCardNumber()) && Objects.equals(metalCard.getCvv(), metalCardTransferRequest.getMetalCardCvv())
                && Objects.equals(metalCard.getPin(), metalCardTransferRequest.getMetalCardPin())){
            bankCardService.withdrawWithATMCard(metalCard.getUserCard().getCardNumber(),metalCard.getUserCard().getCvv(),
                    metalCardTransferRequest.getAmount(),metalCardTransferRequest.getAtmCardPin());

            bankService.deposit(metalCardTransferRequest.getReceiverAccountNumber(), metalCardTransferRequest.getAmount());
            return "Transfer Successful";
        }

        return null;

    }
}
