package com.shop.app.shop.helper;

import com.shop.app.shop.dto.CreateOrUpdateProductDto;
import com.shop.app.shop.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class ProductHelperTests {

    @Test
    public void testConvertionWithEmptyName(){
        CreateOrUpdateProductDto productDto = CreateOrUpdateProductDto.builder().price(new BigDecimal("120.0")).build();
        Optional<Product> productOptional = ProductHelper.createFromDto(productDto);
        Assertions.assertTrue(!productOptional.isPresent());
    }

    @Test
    public void testConvertionWithEmptyPrice(){
        CreateOrUpdateProductDto productDto = CreateOrUpdateProductDto.builder().name("name").build();
        Optional<Product> productOptional = ProductHelper.createFromDto(productDto);
        Assertions.assertTrue(!productOptional.isPresent());
    }

    @Test
    public void testConvertionSuccess(){
        CreateOrUpdateProductDto productDto = CreateOrUpdateProductDto.builder().name("name").price(new BigDecimal("120.0")).build();
        Optional<Product> productOptional = ProductHelper.createFromDto(productDto);
        Assertions.assertTrue(productOptional.isPresent());
        Assertions.assertEquals(productOptional.get().getProductName(),"name");

        Assertions.assertEquals(productOptional.get().getPriceList().stream().collect(Collectors.toList()).get(0).getPrice(),new BigDecimal("120.0"));
    }
}
