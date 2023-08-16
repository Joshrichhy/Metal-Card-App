package com.example.metalcardproject.Data.Repositories;

import com.example.metalcardproject.Data.Model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
    boolean existsByEmailAddress(String emailAddress);

    UserProfile findByEmailAddress(String emailAddress);
}
