package Data.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Account {
    private String id;
    private String firstName;
    private String lastName;
    private String AccountNumber;
    private String password;
    private String emailAddress;
    private BankCard ATMCard;
    private String phoneNumber;
    private LocalDate dob;
    private String pin;
    private BigDecimal balance = BigDecimal.ZERO;


}
