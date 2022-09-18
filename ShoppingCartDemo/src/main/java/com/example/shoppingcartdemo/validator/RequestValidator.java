package com.example.shoppingcartdemo.validator;

public interface RequestValidator {

    boolean accepts(Object product);

    boolean validate(Object product);
}
