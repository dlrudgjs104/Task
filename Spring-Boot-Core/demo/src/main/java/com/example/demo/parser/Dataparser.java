package com.example.demo.parser;

import com.example.demo.account.Account;
import com.example.demo.price.Price;

import java.util.List;

public interface Dataparser {
    public Price price(String city, String sector);

    public List<Account> accounts();

    public List<String> sectors(String city);

    public List<String> cities();
}
