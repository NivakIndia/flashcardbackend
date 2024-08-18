package com.nivak.flashcard.FlashCard;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "flashcards")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlashcardUser {
    private ObjectId id;
    private String userName;
    private String password;
    private List<String> category;
    private List<Flashcard> flashcards;
}
