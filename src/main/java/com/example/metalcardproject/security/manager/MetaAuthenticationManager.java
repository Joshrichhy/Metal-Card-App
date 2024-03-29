package com.example.metalcardproject.security.manager;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MetaAuthenticationManager implements AuthenticationManager {

    private final AuthenticationProvider authenticationProvider;

    public Authentication authenticate(Authentication authentication){
        Authentication authResult = null;
        if (authenticationProvider.supports(authentication.getClass())){
            authResult = authenticationProvider.authenticate(authentication);
            return authResult;
        }
        else throw new BadCredentialsException("Bad Credentials");
    }
}
