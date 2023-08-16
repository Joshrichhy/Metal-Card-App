package com.example.metalcardproject.security;


import com.example.metalcardproject.security.filter.MetaAuthenticationFilter;
import com.example.metalcardproject.security.filter.MetaAuthorizationFilter;
import com.example.metalcardproject.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.metalcardproject.Data.Model.Role.CUSTOMER;
import static com.example.metalcardproject.utils.AppUtils.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity hhtp) throws Exception {
        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new MetaAuthenticationFilter(authenticationManager, jwtUtil);
        usernamePasswordAuthenticationFilter.setFilterProcessesUrl(LOGIN_ENDPOINT);
        return hhtp.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new MetaAuthorizationFilter(jwtUtil),  MetaAuthenticationFilter.class)
                .addFilterAt(usernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(c -> c.requestMatchers(POST, CUSTOMER_API_VALUE )
                        .permitAll())
                .authorizeHttpRequests(c -> c.requestMatchers(POST, LOGIN_ENDPOINT)
                        .permitAll())
                .authorizeHttpRequests(c -> c.requestMatchers(GET,  GET_ALL_CARDS_ENDPOINT )
                        .hasRole(CUSTOMER.name()))
                .authorizeHttpRequests(c -> c.anyRequest().authenticated())
                .build();

    }
}
