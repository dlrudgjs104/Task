package com.example.demo.service;

import com.example.demo.Property;
import com.example.demo.formatter.OutPutFormatter;
import com.example.demo.parser.Dataparser;
import com.example.demo.price.Price;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceService {
    Property property;
    Dataparser dataparser;
    OutPutFormatter outPutFormatter;

    public PriceService(Property property, Dataparser dataparser, OutPutFormatter outPutFormatter) {
        this.property = property;
        this.dataparser = dataparser;
        this.outPutFormatter = outPutFormatter;
    }

    public List<String> cities (){
        return dataparser.cities();
    }

    public List<String> sectors(String city){
        return dataparser.sectors(city);
    }

    public Price price(String city, String sector){
        return dataparser.price(city, sector);
    }

    public String billTotal(String city, String sector, int usage){
        List<Price> priceList = Price.parse(property.getPricePath());
        int total = priceList.stream()
                .filter(price -> price.getCity().equals(city) && price.getSector().equals(sector))
                .findFirst()
                .map(price -> price.getUnitPrice() * price.getUnitPrice() * usage)
                .orElse(0); // 일치하는 가격이 없으면 0 반환

        return outPutFormatter.format(price(city, sector), total);
    }
}
