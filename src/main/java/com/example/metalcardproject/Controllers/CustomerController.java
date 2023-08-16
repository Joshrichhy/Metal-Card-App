package com.example.metalcardproject.Controllers;

import com.example.metalcardproject.Exceptions.UserExistsException;
import com.example.metalcardproject.Services.UserProfileService;
import com.example.metalcardproject.Services.UserProfileServiceImpl;
import com.example.metalcardproject.dtos.RegisterRequest.UserProfileRegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {
    private UserProfileService userProfileService;

@PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserProfileRegisterRequest userProfileRegisterRequest) throws UserExistsException {
        try{
            System.out.println("It entered here");
            var response = userProfileService.registerUser(userProfileRegisterRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }catch(UserExistsException userExistsException){
            var response = "Registration not successful";
            return ResponseEntity.badRequest().body(response);
        }
    }
}
