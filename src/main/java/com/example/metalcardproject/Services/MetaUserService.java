package com.example.metalcardproject.Services;

import com.example.metalcardproject.Data.Model.UserProfile;
import com.example.metalcardproject.Data.Repositories.UserProfileRepository;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import com.example.metalcardproject.security.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import static com.example.metalcardproject.utils.AppUtils.USER_WITH_EMAIL_NOT_FOUND;

@AllArgsConstructor
@Repository
public class MetaUserService implements UserDetailsService {

  private final UserProfileRepository userProfileRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileRepository.findByEmailAddress(email);
        if (userProfile.equals(null)) try {
            throw new NoUserAccountException(String.format(USER_WITH_EMAIL_NOT_FOUND, email));
        } catch (NoUserAccountException e) {
            throw new RuntimeException(e);
        }
        return new User(userProfile);
    }
}
