package com.example.demo.formatter;

import com.example.demo.price.Price;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("eng")
@Component
public class EnglishOutPutFormatter implements OutPutFormatter{

    @Override
    public String format(Price price, int total) {
        return String.format("city: %s, sector: %s, unit price(won): %d, bill total(won): %d", price.getCity(), price.getSector(), price.getUnitPrice(), total);
    }
}
