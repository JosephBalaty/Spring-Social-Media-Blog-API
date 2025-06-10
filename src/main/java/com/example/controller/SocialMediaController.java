package com.example.controller;

import com.example.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import com.example.service.*;
import com.example.exception.*;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accService, MessageService msgService) {
        this.accountService = accService;
        this.messageService = msgService;
    }

    @GetMapping("/accounts/{accountId}/messages")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAccountMessages(@PathVariable String accountId) throws NumberFormatException{
        List<Message> userMsgs = this.messageService.getAccountMessages(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(userMsgs);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity registerAccount(@RequestBody Account account) throws UsernameAlreadyExistsException, BadRegistrationRequestException {
        Account registeredAccount = this.accountService.registerAccount(account);
        return ResponseEntity.status(HttpStatus.OK).body(registeredAccount);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity login(@RequestBody Account account) throws LoginUnsuccessfulException {
        Account loginSuccess = this.accountService.login(account);
        return ResponseEntity.status(HttpStatus.OK).body(loginSuccess);
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity postMessage(@RequestBody Message message) throws NewMessageCreationException {
        Message postedMessage = this.messageService.postMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(postedMessage);
    }
    
    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getMessages() {
        List<Message> msgs = this.messageService.getMessages();
        return ResponseEntity.status(HttpStatus.OK).body(msgs);
    }

    @GetMapping("/messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getMessageById(@PathVariable String messageId) throws NumberFormatException {
        Message msg = this.messageService.getMessageById(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @DeleteMapping("/messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deleteMessageById(@PathVariable String messageId) throws NumberFormatException {
        int rowsDeleted = this.messageService.deleteMessageById(messageId);
        String msgBody = (rowsDeleted == 1) ? "1" : "";
        return ResponseEntity.status(HttpStatus.OK).body(msgBody);
    }

    @PatchMapping("messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateMessageById(@PathVariable String messageId, @RequestBody Message message) throws NumberFormatException, NoMessageFoundException {
        Message updatedMsg = this.messageService.updateMessageById(messageId, message);
        String bodyMsg = (updatedMsg != null) ? "1" : "";
        return ResponseEntity.status(HttpStatus.OK).body(bodyMsg);
    }



    /**
     * EXCEPTION HANDLERS
     * Handles issues in endpoints.
     */
    
    @ExceptionHandler(
        {NewMessageCreationException.class, 
        NoMessageFoundException.class, 
        NumberFormatException.class, 
        BadRegistrationRequestException.class}
        )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleBadRequestExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(LoginUnsuccessfulException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handleUsernameAlreadyExistsException(LoginUnsuccessfulException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
