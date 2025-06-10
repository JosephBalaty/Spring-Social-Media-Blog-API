package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.repository.*;
import com.example.entity.*;
import com.example.exception.*;

@Service
@Transactional
public class AccountService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;

    @Autowired
    public AccountService(AccountRepository accRepo){
        this.accountRepository = accRepo;
    }

    public Account registerAccount(Account acc) throws UsernameAlreadyExistsException, BadRegistrationRequestException {
        if (acc.getUsername().trim().length() > 0) {
            if (acc.getPassword().length() > 4) {
                if (this.accountRepository.findAccountByUsername(acc.getUsername()) == null) {
                    return this.accountRepository.save(acc);
                } else {
                    throw new UsernameAlreadyExistsException("Conflict: Username already exists.");
                }
            }
        }
        throw new BadRegistrationRequestException("Bad Request: Update registration to meet requirements.");
    }

    public Account login(Account acc) throws LoginUnsuccessfulException {
        Account recAcc = this.accountRepository.findAccountByUsernameAndPassword(acc.getUsername(), acc.getPassword());
        if (recAcc != null) {
            return recAcc;
        } else {
            throw new LoginUnsuccessfulException("Login Unsuccessful: Incorrect username or password.");
        }
    }
}
