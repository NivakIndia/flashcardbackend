package com.nivak.flashcard.FlashCard;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface FlashcardRepository extends MongoRepository<FlashcardUser ,ObjectId> {
    FlashcardUser findByUserName(String userName);
}

