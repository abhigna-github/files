package com.example.shoppingcartdemo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Test
    void validateGetProducts(){
        // given // then
        List<String> productList = productService.getProducts();
        // then
        assertThat(productList).isNotNull();
    }

    @Test
    void validateAddProducts(){
        // given
        String product = "EarRing";
        // when
        List<String> productList = productService.addProducts(product);
        // then
        assertThat(productList).isNotNull().hasSize(1).contains(product);
    }
}
