package com.example.metalcardproject.security.user;

import com.example.metalcardproject.Data.Model.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class User implements UserDetails {

    private final UserProfile userProfile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userProfile.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name())).toList();
    }

    @Override
    public String getPassword() {
        return userProfile.getPassword();
    }

    @Override
    public String getUsername() {
        return userProfile.getEmailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
