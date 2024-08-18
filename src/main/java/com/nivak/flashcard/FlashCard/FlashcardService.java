package com.nivak.flashcard.FlashCard;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlashcardService{
    @Autowired
    private FlashcardRepository repository;

    public boolean register(FlashcardUser user) {
        FlashcardUser cardUser = repository.findByUserName(user.getUserName());
        if(cardUser == null){
            user.setPassword(enCodePassword(user.getPassword()));
            user.setFlashcards(new ArrayList<>());
            repository.save(user);
            return true;
        }
        return false;
    }

    public FlashcardUser login(String userName, String password) {
        FlashcardUser user = repository.findByUserName(userName);
        if (user != null && password.equals(deCodePassword(user.getPassword()))) {
            return user;
        }
        return null;
    }

    public List<Flashcard> fetchCards(String token){
        FlashcardUser user = repository.findByUserName(deCodePassword(token));
        return user.getFlashcards();
    }

    public List<String> fetchCategory(String token){
        FlashcardUser user = repository.findByUserName(deCodePassword(token));
        return user.getCategory();
    }

    public String enCodePassword(String str){
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    private String deCodePassword(String str){
        return new String(Base64.getDecoder().decode(str));
    }

    public void saveFlashcard(Flashcard flashcard, String token) {
        FlashcardUser user = repository.findByUserName(deCodePassword(token));
        List<String> newCategory = user.getCategory();
        if(!newCategory.contains(flashcard.getCategory())) newCategory.add(flashcard.getCategory());

        List<Flashcard> allFlashcards = user.getFlashcards();
        flashcard.setCardId(allFlashcards.size()+1);
        allFlashcards.add(flashcard);

        user.setFlashcards(allFlashcards);
        user.setCategory(newCategory);
        repository.save(user);
    }

    public void deleteFlashcard(String token, int cardId) {
        FlashcardUser user = repository.findByUserName(deCodePassword(token));
        List<Flashcard> allFlashcards = user.getFlashcards();
    
        Flashcard flashcardToRemove = allFlashcards.stream()
            .filter(flashcard -> flashcard.getCardId() == cardId)
            .findFirst()
            .orElse(null);
    
        if (flashcardToRemove != null) {
            allFlashcards.remove(flashcardToRemove);
            user.setFlashcards(allFlashcards);
            repository.save(user);
        } else {
            throw new IllegalArgumentException("Flashcard with id " + cardId + " not found");
        }
    }
    

    public void updateFlashcard(Flashcard card, String token) {
        FlashcardUser user = repository.findByUserName(deCodePassword(token));
        List<Flashcard> allFlashcards = user.getFlashcards();
    
        Flashcard flashcardToUpdate = allFlashcards.stream()
            .filter(flashcard -> flashcard.getCardId() == card.getCardId())
            .findFirst()
            .orElse(null);
    
        if (flashcardToUpdate != null) {
            flashcardToUpdate.setAnswer(card.getAnswer());
            flashcardToUpdate.setCategory(card.getCategory());
            flashcardToUpdate.setCode(card.getCode());
            flashcardToUpdate.setLanguage(card.getLanguage());

            List<String> newCategory = user.getCategory();
            if(!newCategory.contains(flashcardToUpdate.getCategory())) newCategory.add(flashcardToUpdate.getCategory());
    
            user.setFlashcards(allFlashcards);
            user.setCategory(newCategory);
            repository.save(user);
        } else {
            throw new IllegalArgumentException("Flashcard with id " + card.getCardId() + " not found");
        }
    }
    
}

