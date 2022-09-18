package com.example.shoppingcartdemo.controller;

import com.example.shoppingcartdemo.exception.ShoppingCartException;
import com.example.shoppingcartdemo.service.ProductService;
import com.example.shoppingcartdemo.validator.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    private ProductController productController;
    @Mock
    private ProductService productService;
    @Mock private List<ProductValidator> productValidatorList;

    @BeforeEach
    void setUp(){
        productValidatorList = new ArrayList<>();
        productValidatorList.add(new ProductValidator());
        productController = new ProductController(productService, productValidatorList);
    }

    @Test
    void validateDisplayProducts() {
        // given
        List<String> expectedProductList = Arrays.asList("Utensils", "Mobile", "LED Bulbs");
        when(productService.getProducts()).thenReturn(expectedProductList);
        // when
        ResponseEntity<List<String>> responseEntity = productController.displayProducts();
        // then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expectedProductList);
    }

    @Test
    void validateDisplayProductsWhenEmpty() {
        // given
        when(productService.getProducts()).thenReturn(null);
        // when
        ResponseEntity<List<String>> responseEntity = productController.displayProducts();
        // then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(responseEntity.getBody()).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"#4","@_)"})
    void validateAddProductsWhenInvalidProductAdded(String product) {
        // given // then
        assertThatThrownBy(() -> productController.addProducts(product)).
                isInstanceOf(ShoppingCartException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Chocolates","43Chocolates453", "643"})
    void validateAddProducts(String product){
        // given // when
        ResponseEntity<List<String>> responseEntity = productController.addProducts(product);
        // then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEmpty();
    }
}
