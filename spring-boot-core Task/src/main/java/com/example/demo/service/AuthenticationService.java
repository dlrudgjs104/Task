package com.example.demo.service;

import com.example.demo.account.Account;
import com.example.demo.parser.Dataparser;
import com.example.demo.price.Price;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    Dataparser dataparser;
    @Getter
    Account currentAccount;

    public AuthenticationService(Dataparser dataparser) {
        this.dataparser = dataparser;
    }

    public void logout() {
        currentAccount = null;
    }

    public Account login(Long id, String password) {
        List<Account> accountList = dataparser.accounts();

        for (Account account : accountList) {
            if (account.getId().equals(id) && account.getPassword().equals(password)) {
                currentAccount = account;
                return account;
            }
        }
        throw new IllegalArgumentException("id or password not correct");
    }
}