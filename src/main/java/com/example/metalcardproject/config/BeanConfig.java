package com.example.metalcardproject.config;

import com.example.metalcardproject.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.metalcardproject.utils.AppUtils.JWT_SIGNING_SECRET;

@Configuration
public class BeanConfig {

    @Value(JWT_SIGNING_SECRET)
    private String jwt_secret;


    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(jwt_secret);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
