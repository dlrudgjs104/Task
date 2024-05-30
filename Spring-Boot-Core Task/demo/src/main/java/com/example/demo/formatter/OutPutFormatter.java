package com.example.demo.formatter;

import com.example.demo.price.Price;

public interface OutPutFormatter {
    public String format(Price price, int total);
}
