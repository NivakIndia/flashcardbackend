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

    public String enCodePassword(String str){
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    private String deCodePassword(String str){
        return new String(Base64.getDecoder().decode(str));
    }

    public void saveFlashcard(Flashcard flashcard, String token) {
        FlashcardUser user = repository.findByUserName(deCodePassword(token));
        List<Flashcard> allFlashcards = user.getFlashcards();
        flashcard.setCardId(allFlashcards.size()+1);
        allFlashcards.add(flashcard);
        user.setFlashcards(allFlashcards);
        repository.save(user);
    }

    public void deletFlashcard(String token ,int cardId){
        FlashcardUser user = repository.findByUserName(deCodePassword(token));
        List<Flashcard> allFlashcards = user.getFlashcards();
        allFlashcards.remove(cardId-1);
        user.setFlashcards(allFlashcards);
        repository.save(user);
    }
}

