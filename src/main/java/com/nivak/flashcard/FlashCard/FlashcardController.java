package com.nivak.flashcard.FlashCard;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nivak.flashcard.Response.ApiResponse;

@Controller
@RequestMapping("/nivak/flashcard")
public class FlashcardController {
    @Autowired
    private FlashcardService service;

    private ApiResponse<FlashcardUser> response;


    @PostMapping("/auth/register/")
    public ResponseEntity<ApiResponse<FlashcardUser>> registerUser(@RequestBody FlashcardUser user) {
        try {
            boolean isRegistered = service.register(user);
            if(!isRegistered){
                response = new ApiResponse<FlashcardUser>(false, "User Already Exists", null);
                return ResponseEntity.ok(response);
            }
            response = new ApiResponse<FlashcardUser>(true, "Registered Successfully", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response = new ApiResponse<FlashcardUser>(false, "Registration Failed", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/auth/login/")
    public ResponseEntity<ApiResponse<String>> loginUser(@RequestBody FlashcardUser user) {
        try {
            FlashcardUser currUser = service.login(user.getUserName(), user.getPassword());
            if(currUser == null){
                ApiResponse<String> response = new ApiResponse<String>(false, "Invalid Credentials", null);
                return ResponseEntity.ok(response);
            }

            String token = service.enCodePassword(currUser.getUserName());

            ApiResponse<String> response = new ApiResponse<String>(true, token, currUser.getPassword());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<String>(false, "Login Failed", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/auth/loginnoauth/")
    public ResponseEntity<ApiResponse<String>> loginUserWithoutAuth(@RequestBody FlashcardUser user) {
        try {
            FlashcardUser currUser = service.loginwithoutAuth(user.getUserName());
            if(currUser == null){
                ApiResponse<String> response = new ApiResponse<String>(false, "Invalid Credentials", "null");
                return ResponseEntity.ok(response);
            }

            String token = service.enCodePassword(currUser.getUserName());

            ApiResponse<String> response = new ApiResponse<String>(true, token, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<String>(false, "Login Failed", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/auth/isadmin/")
    public ResponseEntity<ApiResponse<String>> isAdmin(@RequestParam("token") String token, @RequestParam("password") String password) {
        try {
            FlashcardUser currUser = service.isadmin(token, password);
            if(currUser == null){
                ApiResponse<String> response = new ApiResponse<String>(false, "Invalid Credentials", "null");
                return ResponseEntity.ok(response);
            }

            ApiResponse<String> response = new ApiResponse<String>(true, token, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<String>(false, "Login Failed", null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    
    

    @PostMapping("/getflashcards/")
    public ResponseEntity<ApiResponse<List<Flashcard>>> getAllFlashcards(@RequestParam("token") String token) {
        try {
            List<Flashcard> card = service.fetchCards(token);
            ApiResponse<List<Flashcard>> response = new ApiResponse<>(true, "Flashcards fetched successfully", card);
            return ResponseEntity.ok(response); 
        } catch (Exception e) {
            ApiResponse<List<Flashcard>> response = new ApiResponse<>(false, "Error in fetching flashcards", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/getusercategory/")
    public ResponseEntity<ApiResponse<List<String>>> getUserCategory(@RequestParam("token") String token) {
        try {
            List<String> category = service.fetchCategory(token);
            ApiResponse<List<String>> response = new ApiResponse<>(true, "Flashcards fetched successfully", category);
            return ResponseEntity.ok(response); 
        } catch (Exception e) {
            ApiResponse<List<String>> response = new ApiResponse<>(false, "Error in fetching flashcards", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/addcard/")
    public ResponseEntity<ApiResponse<List<Flashcard>>> createFlashcard(@RequestBody Flashcard flashcard, @RequestParam("token") String token) {
        try {
            service.saveFlashcard(flashcard,token); 
            ApiResponse<List<Flashcard>> response = new ApiResponse<>(true, "Flashcards add successfully", null);
            return ResponseEntity.ok(response); 
        } catch (Exception e) {
            ApiResponse<List<Flashcard>> response = new ApiResponse<>(false, "Error in adding flashcards", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/deletecard/")
    public ResponseEntity<ApiResponse<List<Flashcard>>> deletFlashcard(@RequestParam("token") String token, @RequestParam("cardId") int cardId) {
        try {
            service.deleteFlashcard(token , cardId); 
            ApiResponse<List<Flashcard>> response = new ApiResponse<>(true, "Flashcards deleted successfully", null);
            return ResponseEntity.ok(response); 
        } catch (Exception e) {
            ApiResponse<List<Flashcard>> response = new ApiResponse<>(false, "Error in deleting flashcards", null);
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/updatecard/")
    public ResponseEntity<ApiResponse<List<Flashcard>>> updateFlashcard(@RequestBody Flashcard flashcard, @RequestParam("token") String token) {
        try {
            service.updateFlashcard(flashcard,token); 
            ApiResponse<List<Flashcard>> response = new ApiResponse<>(true, "Flashcards Updated successfully", null);
            return ResponseEntity.ok(response); 
        } catch (Exception e) {
            ApiResponse<List<Flashcard>> response = new ApiResponse<>(false, "Error in Updating flashcards", null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
}

