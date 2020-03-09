package com.shop.app.shop.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void isLoaded(){
        Assertions.assertNotNull(productRepository);
    }

    @Test
    public void saveProduct(){
        Assertions.assertNotNull(productRepository);
    }
}
