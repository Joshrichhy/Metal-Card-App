package com.example.metalcardproject.Data.Repositories;

import com.example.metalcardproject.Data.Model.MetalCard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetalCardRepository extends MongoRepository <MetalCard, String> {
}
