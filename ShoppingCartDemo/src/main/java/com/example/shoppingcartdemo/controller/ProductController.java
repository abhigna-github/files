package com.example.shoppingcartdemo.controller;

import com.example.shoppingcartdemo.exception.ShoppingCartException;
import com.example.shoppingcartdemo.service.ProductService;
import com.example.shoppingcartdemo.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final List<ProductValidator> productValidatorList;

    @GetMapping(value = "/products/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> displayProducts() {
        if (Objects.nonNull(productService.getProducts())) {
            return new ResponseEntity<>(productService.getProducts(),
                    !productService.getProducts().isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
        } else {
            log.info("No Product Exists");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/products/create/{product}")
    public ResponseEntity<List<String>> addProducts(@PathVariable String product) {
        if (performValidation(product)) {
            return new ResponseEntity<>(productService.addProducts(product), HttpStatus.OK);
        }
        throw new ShoppingCartException("Validation failed");
    }

    private boolean performValidation(String product) {
        return productValidatorList.stream()
                .filter(productValidator -> productValidator.accepts(product))
                .anyMatch(productValidator -> productValidator.validate(product));
    }
}
