package com.example.demo.parser;

import com.example.demo.Property;
import com.example.demo.account.Account;
import com.example.demo.price.Price;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnProperty(name = "file.type", havingValue = "json")
@Component
@AllArgsConstructor
public class JsonDataParser implements Dataparser{
    Property property;

    @Override
    public Price price(String city, String sector) {
        return Price.readFromJson(property.getPricePath()).stream()
                .filter(price -> city.equals(price.getCity()) && sector.equals(price.getSector()))
                .map(Price::getPrice)
                .distinct()
                .toList()
                .getFirst();
    }

    @Override
    public List<Account> accounts() {
        return Account.readFromJson(property.getAccountPath());
    }

    @Override
    public List<String> sectors(String city) {
        return Price.readFromJson(property.getPricePath()).stream()
                .filter(price -> city.equals(price.getCity()))
                .map(Price::getSector)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> cities() {
        return Price.readFromJson(property.getPricePath()).stream()
                .map(Price::getCity)
                .distinct()
                .collect(Collectors.toList());
    }

}
