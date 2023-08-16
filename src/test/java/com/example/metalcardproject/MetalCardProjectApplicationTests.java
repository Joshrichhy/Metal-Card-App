package com.example.metalcardproject;

import com.example.metalcardproject.Data.Model.BankCard;
import com.example.metalcardproject.Data.Model.CardType;
import com.example.metalcardproject.Data.Model.UserBankAccount;
import com.example.metalcardproject.Data.Repositories.BankCardRepository;
import com.example.metalcardproject.Data.Repositories.MetalCardRepository;
import com.example.metalcardproject.Data.Repositories.UserBankAccountRepository;
import com.example.metalcardproject.Data.Repositories.UserProfileRepository;
import com.example.metalcardproject.Exceptions.InsufficientFundException;
import com.example.metalcardproject.Exceptions.InvalidPinException;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import com.example.metalcardproject.Exceptions.UserExistsException;
import com.example.metalcardproject.Services.*;
import com.example.metalcardproject.dtos.RegisterRequest.*;
import com.example.metalcardproject.dtos.ResponseRequest.FindUserBankAccountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MetalCardProjectApplicationTests {
        @Autowired
    private UserBankAccountServices userBankAccountServices;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private UserBankAccountRepository userBankAccountRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    private  RegisterUserBankAccount registerUserBankAccount;
    private RegisterATMCardRequest registerATMCardRequest;
    @Autowired
    private BankService bankService;

    @Autowired
    private MetalCardService metalCardService;

    @Autowired
    private BankCardRepository bankCardRepository;

    @Autowired
    private MetalCardRepository metalCardRepository;

    @BeforeEach
    void setUp(){
        userBankAccountRepository.deleteAll();
        userProfileRepository.deleteAll();
        bankCardRepository.deleteAll();
        metalCardRepository.deleteAll();

        registerUserBankAccount = RegisterUserBankAccount.builder()
                .firstName("Joshua")
                .lastName("Oluwakuse")
                //  .dob(LocalDate.parse("17-12-1994"))
                .emailAddress("kuseJoshua@gmail.com")
                .phoneNumber("07033490197")
                .password("12345")
                .pin("1234")
                .build();

    }
    @Test
    void contextLoads() {

    }

    @Test
    public  void openUserBankAccountAndSaveAccountTest(){
        String expected = "Registration Successful";
       assertEquals(expected,  userBankAccountServices.registerUserAccount(registerUserBankAccount));
    }
    @Test
    public  void findUserBankAccountWithAccountNumberTest(){

    }
      //  userBankAccountServices.registerUserAccount(registerUserBankAccount);
//        FindUserBankAccountResponse findUserBankAccountResponse = new FindUserBankAccountResponse();
//        findUserBankAccountResponse.setFullName("Joshua Oluwakuse");
//        findUserBankAccountResponse.setAccountNumber("20231");
//        findUserBankAccountResponse.setDob(null);
//        findUserBankAccountResponse.setPhoneNumber("07033490197");
//        findUserBankAccountResponse.setEmailAddress("kuseJoshua@gmail.com");
//        findUserBankAccountResponse.setBalance(BigDecimal.ZERO);
//
//        assertEquals(findUserBankAccountResponse.toString(),   userBankAccountServices.findUserAccountWithAccountNumber("20231"));
//    }
      @Test
      public  void registerTwoUserBankAccountsCountTwoUsersTest(){
          userBankAccountServices.registerUserAccount(registerUserBankAccount);
          userBankAccountServices.registerUserAccount(registerUserBankAccount);
          assertEquals(2, userBankAccountServices.numbersOfAccounts());
      }

    @Test
    public  void registerOneUserBankAccountDepositIntoAccountTest() throws NoUserAccountException {
       userBankAccountServices.registerUserAccount(registerUserBankAccount);
       String accountNumber = registerUserBankAccount.getAccountNumber();
       String pin = registerUserBankAccount.getPin();
       bankService.deposit(accountNumber, BigDecimal.valueOf(10000));
        bankService.deposit(accountNumber, BigDecimal.valueOf(10000));
        assertEquals(BigDecimal.valueOf(20000), bankService.checkBalance(accountNumber, pin));
    }

    @Test
    public  void registerOneUserBankAccountDepositAndWithdrawFromAccountTest() throws InsufficientFundException, NoUserAccountException, InvalidPinException {
        userBankAccountServices.registerUserAccount(registerUserBankAccount);
        String accountNumber = registerUserBankAccount.getAccountNumber();
        String pin = registerUserBankAccount.getPin();
        bankService.deposit(accountNumber, BigDecimal.valueOf(10000));
        bankService.withdraw(accountNumber, BigDecimal.valueOf(5000), pin);
        assertEquals(BigDecimal.valueOf(5000), bankService.checkBalance(accountNumber, pin));
    }

    @Test
    public  void registerTwoUserBankAccountsTransferFromAccountToAnotherTest() throws InsufficientFundException, NoUserAccountException, InvalidPinException {
        userBankAccountServices.registerUserAccount(registerUserBankAccount);
       RegisterUserBankAccount registerUserBankAccount2 = RegisterUserBankAccount.builder()
                .firstName("Felix")
                .lastName("Spencer")
                //  .dob(LocalDate.parse("17-12-1994"))
                .emailAddress("felix@gmail.com")
                .phoneNumber("07013490197")
                .password("12345")
                .pin("1234")
                .build();
       userBankAccountServices.registerUserAccount(registerUserBankAccount2);
        String senderAccountNumber = registerUserBankAccount.getAccountNumber();
        String receiverAccountNumber = registerUserBankAccount2.getAccountNumber();
        String senderPin = registerUserBankAccount.getPin();
        String receiverPin = registerUserBankAccount2.getPin();
        bankService.deposit(senderAccountNumber, BigDecimal.valueOf(10000));
        bankService.transfer(senderAccountNumber, BigDecimal.valueOf(5000), receiverAccountNumber, senderPin);
        assertEquals(BigDecimal.valueOf(5000), bankService.checkBalance(receiverAccountNumber, receiverPin));
    }
    @Test
    public  void registerOneUserBankAccountWithdrawBelowBalanceFromAccountThrowExceptionTest() throws NoUserAccountException {
        userBankAccountServices.registerUserAccount(registerUserBankAccount);
        String accountNumber = registerUserBankAccount.getAccountNumber();
        String pin = registerUserBankAccount.getPin();
        bankService.deposit(accountNumber, BigDecimal.valueOf(2000));
       assertThrows(InsufficientFundException.class, ()-> bankService.withdraw(accountNumber, BigDecimal.valueOf(10000), pin));
    }

    @Test
    public  void registerOneUserBankAccountTransferToANonExistingAccountThrowExceptionTest() throws NoUserAccountException {
        userBankAccountServices.registerUserAccount(registerUserBankAccount);
        String senderAccountNumber = registerUserBankAccount.getAccountNumber();
        String senderPin = registerUserBankAccount.getPin();
        bankService.deposit(senderAccountNumber, BigDecimal.valueOf(2000));
        assertThrows(NoUserAccountException.class, ()-> bankService.transfer(senderAccountNumber, BigDecimal.valueOf(5000), "32123", senderPin));
    }
    @Test
    public  void registerOneUserBankAccountWithdrawWithWrongPinThrowExceptionTest() throws NoUserAccountException {
        userBankAccountServices.registerUserAccount(registerUserBankAccount);
        String senderAccountNumber = registerUserBankAccount.getAccountNumber();
        bankService.deposit(senderAccountNumber, BigDecimal.valueOf(5000));
        assertThrows(InvalidPinException.class, ()-> bankService.withdraw(senderAccountNumber, BigDecimal.valueOf(5000), "32123"));
    }

    @Test
    public  void registerOneUserBankAccountRequestATMCardTest() throws NoUserAccountException {
        userBankAccountServices.registerUserAccount(registerUserBankAccount);
        registerATMCardRequest = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount.getFirstName())
                .lastName(registerUserBankAccount.getLastName())
                .cardType(CardType.VISA).pin(registerUserBankAccount.getPin())
                .accountNumber(registerUserBankAccount.getAccountNumber())
                .build();

        bankCardService.requestATMCard(registerATMCardRequest);
        assertNotNull(userBankAccountServices.findATMCardbyCardNumber(
                registerATMCardRequest.getAccountNumber(), registerATMCardRequest.getCardNumber()));
    }

    @Test
    public  void registerOneUserBankAccountRequestTwoATMCardsTest() throws NoUserAccountException {
        userBankAccountServices.registerUserAccount(registerUserBankAccount);
       RegisterATMCardRequest registerATMCardRequest2 = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount.getFirstName())
                .lastName(registerUserBankAccount.getLastName())
                .cardType(CardType.VISA).pin(registerUserBankAccount.getPin())
                .accountNumber(registerUserBankAccount.getAccountNumber())
                .build();
        registerATMCardRequest = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount.getFirstName())
                .lastName(registerUserBankAccount.getLastName())
                .cardType(CardType.VISA).pin(registerUserBankAccount.getPin())
                .accountNumber(registerUserBankAccount.getAccountNumber())
                .build();
        bankCardService.requestATMCard(registerATMCardRequest);
        bankCardService.requestATMCard(registerATMCardRequest2);
        assertEquals(2, userBankAccountServices.countUserATMCards(registerATMCardRequest.getAccountNumber()));
    }

    @Test
    public  void registerTwoUserBankAccountsRequestTwoATMCardsForEachUserTest() throws NoUserAccountException {
        userBankAccountServices.registerUserAccount(registerUserBankAccount);
        RegisterUserBankAccount registerUserBankAccount2 = RegisterUserBankAccount.builder()
                .firstName("Felix")
                .lastName("Spencer")
                //  .dob(LocalDate.parse("17-12-1994"))
                .emailAddress("felix@gmail.com")
                .phoneNumber("07013490197")
                .password("12345")
                .pin("1234")
                .build();

        userBankAccountServices.registerUserAccount(registerUserBankAccount2);
        RegisterATMCardRequest registerATMCardRequest2 = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount2.getFirstName())
                .lastName(registerUserBankAccount2.getLastName())
                .cardType(CardType.VISA).pin(registerUserBankAccount2.getPin())
                .accountNumber(registerUserBankAccount2.getAccountNumber())
                .build();

        registerATMCardRequest = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount.getFirstName())
                .lastName(registerUserBankAccount.getLastName())
                .cardType(CardType.VISA).pin(registerUserBankAccount.getPin())
                .accountNumber(registerUserBankAccount.getAccountNumber())
                .build();

        bankCardService.requestATMCard(registerATMCardRequest);
        bankCardService.requestATMCard(registerATMCardRequest2);
        assertEquals(1, userBankAccountServices.countUserATMCards(registerATMCardRequest.getAccountNumber()));
        assertEquals(1, userBankAccountServices.countUserATMCards(registerATMCardRequest2.getAccountNumber()));
    }

    @Test
    public  void registerOneUserBankAccountRequestATMCardAndWithdrawWithCardTest() throws NoUserAccountException, InvalidPinException, InsufficientFundException {
        userBankAccountServices.registerUserAccount(registerUserBankAccount);

        registerATMCardRequest = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount.getFirstName())
                .lastName(registerUserBankAccount.getLastName())
                .cardType(CardType.MASTER).pin(registerUserBankAccount.getPin())
                .accountNumber(registerUserBankAccount.getAccountNumber())
                .build();

        bankCardService.requestATMCard(registerATMCardRequest);
        bankService.deposit(registerUserBankAccount.getAccountNumber(), BigDecimal.valueOf(4000));

        bankCardService.withdrawWithATMCard(registerATMCardRequest.getCardNumber(), registerATMCardRequest.getCvv(),
                BigDecimal.valueOf(2000), registerATMCardRequest.getPin());

        assertEquals(BigDecimal.valueOf(2000), bankService.checkBalance(registerUserBankAccount.getAccountNumber(), registerUserBankAccount.getPin()));
    }

    @Test
    public  void registerTwoUserBankAccountsRequestOneUserATMCardAndTransferToOtherUserWithCardTest() throws NoUserAccountException, InvalidPinException, InsufficientFundException {
        userBankAccountServices.registerUserAccount(registerUserBankAccount);

        registerATMCardRequest = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount.getFirstName())
                .lastName(registerUserBankAccount.getLastName())
                .cardType(CardType.VISA).pin(registerUserBankAccount.getPin())
                .accountNumber(registerUserBankAccount.getAccountNumber())
                .build();


        RegisterUserBankAccount registerUserBankAccount2 = RegisterUserBankAccount.builder()
                .firstName("Felix")
                .lastName("Spencer")
                //  .dob(LocalDate.parse("17-12-1994"))
                .emailAddress("felix@gmail.com")
                .phoneNumber("07013490197")
                .password("12345")
                .pin("1234")
                .build();
        userBankAccountServices.registerUserAccount(registerUserBankAccount2);

        bankCardService.requestATMCard(registerATMCardRequest);
        bankService.deposit(registerUserBankAccount.getAccountNumber(), BigDecimal.valueOf(4000));

        bankCardService.transferWithATMCard(registerATMCardRequest.getCardNumber(), registerATMCardRequest.getCvv(),
                registerUserBankAccount2.getAccountNumber(), BigDecimal.valueOf(2000), registerATMCardRequest.getPin());

        assertEquals(BigDecimal.valueOf(2000), bankService.checkBalance(registerUserBankAccount.getAccountNumber(), registerUserBankAccount.getPin()));
        assertEquals(BigDecimal.valueOf(2000), bankService.checkBalance(registerUserBankAccount2.getAccountNumber(), registerUserBankAccount2.getPin()));
    }

    @Test
    public void registerUserOnMetalCardWebsiteTest() throws UserExistsException {
        UserProfileRegisterRequest userProfileRegisterRequest = UserProfileRegisterRequest.builder()
                .firstName("Joshua").lastName("Oluwakuse").phoneNumber("07033490197").password("12345")
                .emailAddress("kuseJoshua@gmail.com")
                .build();
        userProfileService.registerUser(userProfileRegisterRequest);
        assertEquals(1, userProfileService.countUsers());
    }

    @Test
    public void registerTwoUsersOnMetalCardWebsiteWithSameEmailAddressThrowExceptionTest() throws UserExistsException {
        UserProfileRegisterRequest userProfileRegisterRequest = UserProfileRegisterRequest.builder()
                .firstName("Joshua").lastName("Oluwakuse").phoneNumber("07033490197").password("12345")
                .emailAddress("kuseJoshua@gmail.com")
                .build();
        userProfileService.registerUser(userProfileRegisterRequest);
        assertThrows(UserExistsException.class, ()->  userProfileService.registerUser(userProfileRegisterRequest));
    }

    @Test
    public void registerOneUserOnMetalCardWebsiteAddATMCardTest() throws UserExistsException, NoUserAccountException {
        UserProfileRegisterRequest userProfileRegisterRequest = UserProfileRegisterRequest.builder()
                .firstName("Joshua").lastName("Oluwakuse").phoneNumber("07033490197").password("12345")
                .emailAddress("kuseJoshua@gmail.com")
                .build();
        userProfileService.registerUser(userProfileRegisterRequest);

        userBankAccountServices.registerUserAccount(registerUserBankAccount);

        registerATMCardRequest = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount.getFirstName())
                .lastName(registerUserBankAccount.getLastName())
                .cardType(CardType.VISA).pin(registerUserBankAccount.getPin())
                .accountNumber(registerUserBankAccount.getAccountNumber())
                .build();
        bankCardService.requestATMCard(registerATMCardRequest);

        AddATMCardRequest addATMCardRequest = AddATMCardRequest.builder()
                .emailAddress(userProfileRegisterRequest.getEmailAddress())
                .cardNumber(registerATMCardRequest.getCardNumber())
                .cvv(registerATMCardRequest.getCvv()).build();
        userProfileService.addATMCard(addATMCardRequest);
        userProfileService.addATMCard(addATMCardRequest);

        assertEquals(1, userProfileService.countATMCard(userProfileRegisterRequest.getEmailAddress()));
    }


    @Test
    public void registerOneUserOnMetalCardWebsiteAddATMCardLinkCardWithMetalCardTest() throws UserExistsException, NoUserAccountException {
        UserProfileRegisterRequest userProfileRegisterRequest = UserProfileRegisterRequest.builder()
                .firstName("Joshua").lastName("Oluwakuse").phoneNumber("07033490197").password("12345")
                .emailAddress("kuseJoshua@gmail.com")
                .build();
        userProfileService.registerUser(userProfileRegisterRequest);

        userBankAccountServices.registerUserAccount(registerUserBankAccount);

        registerATMCardRequest = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount.getFirstName())
                .lastName(registerUserBankAccount.getLastName())
                .cardType(CardType.VISA).pin(registerUserBankAccount.getPin())
                .accountNumber(registerUserBankAccount.getAccountNumber())
                .build();
        bankCardService.requestATMCard(registerATMCardRequest);

        AddATMCardRequest addATMCardRequest = AddATMCardRequest.builder()
                .emailAddress(userProfileRegisterRequest.getEmailAddress())
                .cardNumber(registerATMCardRequest.getCardNumber())
                .cvv(registerATMCardRequest.getCvv()).build();
        userProfileService.addATMCard(addATMCardRequest);
        userProfileService.addATMCard(addATMCardRequest); //incase user adds same card

        assertEquals(1, userProfileService.countATMCard(userProfileRegisterRequest.getEmailAddress()));

        BankCard atmCard = userProfileService.findAtmCard(userProfileRegisterRequest.getEmailAddress(), addATMCardRequest.getCardNumber());

        RegisterMetalCardRequest registerMetalCardRequest = RegisterMetalCardRequest.builder()
                .pin("1111").firstName("Joshua").lastName("Oluwakuse")
                .userCard(atmCard).build();

        metalCardService.requestMetalCard(registerMetalCardRequest, userProfileRegisterRequest.getEmailAddress());

        assertEquals(1, metalCardService.countMetalCards());

     //   metalCardService.linkAtmCardWithMetalCard(metalCardLinkRequest);

    }

    @Test
    public void registerOneUserOnMetalCardWebsiteAdd2ATMCardsChangeCardOnMetalCardTest() throws UserExistsException, NoUserAccountException {
        UserProfileRegisterRequest userProfileRegisterRequest = UserProfileRegisterRequest.builder()
                .firstName("Joshua").lastName("Oluwakuse").phoneNumber("07033490197").password("12345")
                .emailAddress("kuseJoshua@gmail.com")
                .build();
        userProfileService.registerUser(userProfileRegisterRequest);

        userBankAccountServices.registerUserAccount(registerUserBankAccount);

        registerATMCardRequest = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount.getFirstName())
                .lastName(registerUserBankAccount.getLastName())
                .cardType(CardType.VISA).pin(registerUserBankAccount.getPin())
                .accountNumber(registerUserBankAccount.getAccountNumber())
                .build();
        bankCardService.requestATMCard(registerATMCardRequest);

      RegisterATMCardRequest  registerATMCardRequest2 = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount.getFirstName())
                .lastName(registerUserBankAccount.getLastName())
                .cardType(CardType.VISA).pin(registerUserBankAccount.getPin())
                .accountNumber(registerUserBankAccount.getAccountNumber())
                .build();
        bankCardService.requestATMCard(registerATMCardRequest2);

        AddATMCardRequest addATMCardRequest = AddATMCardRequest.builder()
                .emailAddress(userProfileRegisterRequest.getEmailAddress())
                .cardNumber(registerATMCardRequest.getCardNumber())
                .cvv(registerATMCardRequest.getCvv()).build();
        userProfileService.addATMCard(addATMCardRequest);

        AddATMCardRequest addATMCardRequest2 = AddATMCardRequest.builder()
                .emailAddress(userProfileRegisterRequest.getEmailAddress())
                .cardNumber(registerATMCardRequest2.getCardNumber())
                .cvv(registerATMCardRequest2.getCvv()).build();
        userProfileService.addATMCard(addATMCardRequest2);

        assertEquals(2, userProfileService.countATMCard(userProfileRegisterRequest.getEmailAddress()));

        BankCard atmCard = userProfileService.findAtmCard(userProfileRegisterRequest.getEmailAddress(), addATMCardRequest.getCardNumber());

        RegisterMetalCardRequest registerMetalCardRequest = RegisterMetalCardRequest.builder()
                .pin("1111").firstName("Joshua").lastName("Oluwakuse")
                .userCard(atmCard).build();
        metalCardService.requestMetalCard(registerMetalCardRequest, userProfileRegisterRequest.getEmailAddress());

        BankCard atmCard2 = userProfileService.findAtmCard(userProfileRegisterRequest.getEmailAddress(), addATMCardRequest2.getCardNumber());

        metalCardService.changeAtmCardOnMetalCard(userProfileRegisterRequest.getEmailAddress(), atmCard2);

        assertEquals(atmCard2, metalCardService.findAtmCardOnMetalCards(userProfileRegisterRequest.getEmailAddress()));

    }

    @Test
    public void registerOneUserOnMetalCardWebsiteAddATMCardLinkCardWithMetalCardAndWithdrawTest() throws UserExistsException, NoUserAccountException, InsufficientFundException, InvalidPinException {
        UserProfileRegisterRequest userProfileRegisterRequest = UserProfileRegisterRequest.builder()
                .firstName("Joshua").lastName("Oluwakuse").phoneNumber("07033490197").password("12345")
                .emailAddress("kuseJoshua@gmail.com")
                .build();
        userProfileService.registerUser(userProfileRegisterRequest);

        userBankAccountServices.registerUserAccount(registerUserBankAccount);

        registerATMCardRequest = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount.getFirstName())
                .lastName(registerUserBankAccount.getLastName())
                .cardType(CardType.VISA).pin(registerUserBankAccount.getPin())
                .accountNumber(registerUserBankAccount.getAccountNumber())
                .build();
        bankCardService.requestATMCard(registerATMCardRequest);

        AddATMCardRequest addATMCardRequest = AddATMCardRequest.builder()
                .emailAddress(userProfileRegisterRequest.getEmailAddress())
                .cardNumber(registerATMCardRequest.getCardNumber())
                .cvv(registerATMCardRequest.getCvv()).build();
        userProfileService.addATMCard(addATMCardRequest);
        userProfileService.addATMCard(addATMCardRequest); //incase user adds same card

        assertEquals(1, userProfileService.countATMCard(userProfileRegisterRequest.getEmailAddress()));

        BankCard atmCard = userProfileService.findAtmCard(userProfileRegisterRequest.getEmailAddress(), addATMCardRequest.getCardNumber());

        RegisterMetalCardRequest registerMetalCardRequest = RegisterMetalCardRequest.builder()
                .pin("1111").firstName("Joshua").lastName("Oluwakuse")
                .userCard(atmCard).build();

        metalCardService.requestMetalCard(registerMetalCardRequest, userProfileRegisterRequest.getEmailAddress());

        assertEquals(1, metalCardService.countMetalCards());

        MetalCardWithdrawalRequest metalCardWithdrawalRequest = MetalCardWithdrawalRequest.builder()
                .metalCardNumber(registerMetalCardRequest.getCardNumber()).metalCardCvv(registerMetalCardRequest.getCvv())
                .metalCardPin(registerMetalCardRequest.getPin()).atmCardPin(atmCard.getPin()).amount(BigDecimal.valueOf(5000))
                .emailAddress(userProfileRegisterRequest.getEmailAddress()).build();


        bankService.deposit(registerUserBankAccount.getAccountNumber(), BigDecimal.valueOf(4000));
     assertThrows(InsufficientFundException.class, ()-> userProfileService.withdrawWithMetalCard(metalCardWithdrawalRequest));

    }

    @Test
    public void registerOneUserOnMetalCardWebsiteAddATMCardLinkCardWithMetalCardAndTransferToABankAccountTest() throws UserExistsException, NoUserAccountException, InsufficientFundException, InvalidPinException {
        RegisterUserBankAccount registerUserBankAccount2 = RegisterUserBankAccount.builder()
                .firstName("Felix")
                .lastName("Spencer")
                //  .dob(LocalDate.parse("17-12-1994"))
                .emailAddress("felix@gmail.com")
                .phoneNumber("07013490197")
                .password("12345")
                .pin("1234")
                .build();
        userBankAccountServices.registerUserAccount(registerUserBankAccount2);

        UserProfileRegisterRequest userProfileRegisterRequest = UserProfileRegisterRequest.builder()
                .firstName("Joshua").lastName("Oluwakuse").phoneNumber("07033490197").password("12345")
                .emailAddress("kuseJoshua@gmail.com")
                .build();
        userProfileService.registerUser(userProfileRegisterRequest);

        userBankAccountServices.registerUserAccount(registerUserBankAccount);

        registerATMCardRequest = RegisterATMCardRequest.builder()
                .firstName(registerUserBankAccount.getFirstName())
                .lastName(registerUserBankAccount.getLastName())
                .cardType(CardType.VISA).pin(registerUserBankAccount.getPin())
                .accountNumber(registerUserBankAccount.getAccountNumber())
                .build();
        bankCardService.requestATMCard(registerATMCardRequest);

        AddATMCardRequest addATMCardRequest = AddATMCardRequest.builder()
                .emailAddress(userProfileRegisterRequest.getEmailAddress())
                .cardNumber(registerATMCardRequest.getCardNumber())
                .cvv(registerATMCardRequest.getCvv()).build();
        userProfileService.addATMCard(addATMCardRequest);

        assertEquals(1, userProfileService.countATMCard(userProfileRegisterRequest.getEmailAddress()));

        BankCard atmCard = userProfileService.findAtmCard(userProfileRegisterRequest.getEmailAddress(), addATMCardRequest.getCardNumber());

        RegisterMetalCardRequest registerMetalCardRequest = RegisterMetalCardRequest.builder()
                .pin("1111").firstName("Joshua").lastName("Oluwakuse")
                .userCard(atmCard).build();

        metalCardService.requestMetalCard(registerMetalCardRequest, userProfileRegisterRequest.getEmailAddress());

        assertEquals(1, metalCardService.countMetalCards());

        MetalCardTransferRequest metalCardTransferRequest = MetalCardTransferRequest.builder()
                .metalCardNumber(registerMetalCardRequest.getCardNumber()).metalCardCvv(registerMetalCardRequest.getCvv())
                .metalCardPin(registerMetalCardRequest.getPin()).atmCardPin(atmCard.getPin()).amount(BigDecimal.valueOf(5000))
                .emailAddress(userProfileRegisterRequest.getEmailAddress()).receiverAccountNumber(registerUserBankAccount2.getAccountNumber()).build();


        bankService.deposit(registerUserBankAccount.getAccountNumber(), BigDecimal.valueOf(6000));
         userProfileService.transferWithMetalCard(metalCardTransferRequest);

         assertEquals(BigDecimal.valueOf(5000), bankService.checkBalance(registerUserBankAccount2.getAccountNumber(), registerUserBankAccount2.getPin()));


    }
}
