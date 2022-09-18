package com.example.shoppingcartdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private static List<String> productList = new ArrayList<>();

    public List<String> getProducts(){
        log.info("Listing the products with size : {}", productList.size());
        return productList;
    }

    public List<String> addProducts(String product){
         productList.add(product);
         log.info("Added product : {}" , product);
         return productList;
    }
}
