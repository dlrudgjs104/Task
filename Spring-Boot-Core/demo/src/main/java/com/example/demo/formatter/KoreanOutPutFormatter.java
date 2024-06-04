package com.example.demo.formatter;

import com.example.demo.price.Price;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("kor")
@Component
public class KoreanOutPutFormatter implements OutPutFormatter {

    @Override
    public String format(Price price, int total) {
        return String.format("지자체명: %s, 업종: %s, 구간금액(원): %d, 총금액(원): %d", price.getCity(), price.getSector(), price.getUnitPrice(), total);
    }
}
