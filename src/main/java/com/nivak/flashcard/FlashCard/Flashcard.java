package com.nivak.flashcard.FlashCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Flashcard {
    private int cardId;
    private String question;
    private String answer;
    private boolean code;
    private String language;

    // Getters and Setters
    public boolean getCode(){
        return this.code;
    }

    public void setCode(boolean code){
        this.code = code;
    }

}
