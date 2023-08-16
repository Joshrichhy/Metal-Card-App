package Data.Model;

import com.example.metalcardproject.dtos.RegisterRequest.RegisterUserBankAccount;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


@SpringBootTest
class AccountTest {

    @Test
    public  void openUserBankAccount(){
        RegisterUserBankAccount registerUserBankAccount = RegisterUserBankAccount.builder()
                .firstName("Joshua")
                .lastName("Oluwakuse")
                .dob(LocalDate.parse("17-12-1994"))
                .emailAddress("kuseJoshua@gmail.com")
                .phoneNumber("07033490197")
                .password("12345")
                .pin("1234")
                .build();
        System.out.println(registerUserBankAccount);
    }

}