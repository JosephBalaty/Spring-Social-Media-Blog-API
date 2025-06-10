package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.repository.*;
import com.example.entity.*;
import com.example.exception.*;
import java.util.List;

@Service
@Transactional
public class MessageService {
    
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    public MessageService(MessageRepository msgRepo, AccountRepository accRepo) {
        this.messageRepository = msgRepo;
        this.accountRepository = accRepo;
    }
    
    public Message postMessage(Message msg) throws NewMessageCreationException {
        if (msg.getMessageText().trim().length() > 0) {
            if (msg.getMessageText().length() <= 255) {
                if (this.accountRepository.findAccountByAccountId(msg.getPostedBy()) != null) {
                    return this.messageRepository.save(msg);
                }
                throw new NewMessageCreationException("Client Error: New message is from an unknown user. Please verify PostedBy.");
            }
            throw new NewMessageCreationException("Client Error: Message length cannot exceed 255 characters.");
        }
        throw new NewMessageCreationException("Client Error: Message text cannot be empty or non-blank.");
    }

    public List<Message> getMessages() {
        return this.messageRepository.findAll();
    }

    public Message getMessageById(String msgId) throws NumberFormatException {
        return this.messageRepository.findMessageByMessageId(Integer.valueOf(msgId));
    }

    public int deleteMessageById(String msgId) throws NumberFormatException {
        return this.messageRepository.deleteMessageByMessageId(Integer.valueOf(msgId));
    }

    public Message updateMessageById(String msgId, Message msg) throws NumberFormatException {
        Message oldMsg = this.messageRepository.findMessageByMessageId(Integer.valueOf(msgId));
        if (oldMsg != null) {
            if (msg.getMessageText().trim().length() > 0) {
                if (msg.getMessageText().length() <= 255) {
                    oldMsg.setMessageText(msg.getMessageText());
                    this.messageRepository.save(oldMsg);
                    return oldMsg;
                }
                throw new NewMessageCreationException("Client Error: Message length cannot exceed 255 characters."); 
            }            
            throw new NewMessageCreationException("Client Error: Message text cannot be empty or non-blank.");
        }                
        throw new NoMessageFoundException("Client Error: No message found with provided message id.");   
    }

    public List<Message> getAccountMessages(String postedById) throws NumberFormatException{
        return this.messageRepository.findMessageByPostedBy(Integer.valueOf(postedById));
    }
}
