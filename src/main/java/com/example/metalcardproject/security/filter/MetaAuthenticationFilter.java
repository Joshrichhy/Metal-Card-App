package com.example.metalcardproject.security.filter;

import com.auth0.jwt.JWT;
import static java.time.Instant.now;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.metalcardproject.dtos.LoginRequest;
import com.example.metalcardproject.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.example.metalcardproject.utils.AppUtils.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class MetaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private ObjectMapper mapper = new ObjectMapper();

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
            String email = EMPTY_SPACE_VALUE;
        try{
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);
            email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

            Authentication authResult = authenticationManager.authenticate(authentication);

            SecurityContextHolder.getContext().setAuthentication(authResult);
            return authResult;
        }catch(IOException exception){
            throw new BadCredentialsException(String.format(AUTHENTICATION_FAILED_FOR_USER_WITH_EMAIL, email));
        }
    }

    protected void successfulAuthentication(HttpServlet request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        String accessToken = generateAccessToken(authResult.getAuthorities());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getOutputStream().write(mapper.writeValueAsBytes(
                Map.of(ACCESS_TOKEN_VALUE, accessToken)
        ));

    }

    private String generateAccessToken(Collection<? extends GrantedAuthority> authorities) {
    Map <String, String> map = new HashMap<>();
    for(GrantedAuthority authority: authorities){
        map.put(CLAIMS_VALUE, authority.getAuthority());
    }
    return JWT.create().withIssuedAt(now())
            .withExpiresAt(now().plusSeconds(1200L))
            .withClaim(CLAIMS_VALUE, map)
            .sign(Algorithm.HMAC512(jwtUtil.getSecret().getBytes()));


    }
}
