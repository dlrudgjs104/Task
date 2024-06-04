package com.example.demo.parser;

import com.example.demo.Property;
import com.example.demo.account.Account;
import com.example.demo.price.Price;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@ConditionalOnProperty(name = "file.type", havingValue = "csv")
@Component
@AllArgsConstructor
public class CsvDataParser implements Dataparser{
    Property property;

    @Override
    public Price price(String city, String sector) {
        return Price.parse(property.getPricePath()).stream()
                .filter(price -> city.equals(price.getCity()) && sector.equals(price.getSector()))
                .map(Price::getPrice)
                .distinct()
                .toList()
                .getFirst();
    }

    @Override
    public List<Account> accounts() {
        return Account.parse(property.getAccountPath());
    }

    @Override
    public List<String> sectors(String city) {
        return Price.parse(property.getPricePath()).stream()
                .filter(price -> city.equals(price.getCity()))
                .map(Price::getSector)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> cities() {
        return Price.parse(property.getPricePath()).stream()
                .map(Price::getCity)
                .distinct()
                .collect(Collectors.toList());
    }

}
