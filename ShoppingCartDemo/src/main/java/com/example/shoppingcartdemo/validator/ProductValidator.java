package com.example.shoppingcartdemo.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ProductValidator implements RequestValidator {

    @Override
    public boolean accepts(Object product) {
        return product instanceof String;
    }

    @Override
    public boolean validate(Object product) {
        return Pattern.matches("\\w*", (String) product);
    }
}
