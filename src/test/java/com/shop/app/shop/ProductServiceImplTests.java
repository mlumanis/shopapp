package com.shop.app.shop;

import com.shop.app.shop.dto.ProductDto;
import com.shop.app.shop.dto.UpdateProductNameDto;
import com.shop.app.shop.dto.UpdateProductPriceDto;
import com.shop.app.shop.exception.ItemNotFoundException;
import com.shop.app.shop.model.Price;
import com.shop.app.shop.model.Product;
import com.shop.app.shop.repository.ProductRepository;

import com.shop.app.shop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceImplTests {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @BeforeTestClass
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    List<Product> all = new ArrayList();

    private void  initData(){

        //Product 1

        Product product1 = new Product();
        Set priceList1 = new HashSet();
        Price price1= new Price();

        price1.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        price1.setProduct(product1);

        price1.setId(UUID.randomUUID());
        price1.setPrice(new BigDecimal("12.90"));

        Price price2= new Price();
        price2.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        price2.setProduct(product1);
        price2.setId(UUID.randomUUID());
        price2.setPrice(new BigDecimal("45.90"));

        priceList1.add(price1);
        priceList1.add(price2);

        product1.setProductName("water");
        product1.setId(UUID.randomUUID());
        product1.setPriceList(priceList1);

        //Product 2
        Product product2 = new Product();

        Set priceList2 = new HashSet();

        Price price3= new Price();
        price2.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        price2.setProduct(product2);
        price2.setId(UUID.randomUUID());
        price2.setPrice(new BigDecimal("45.90"));

        priceList2.add(price3);

        product2.setProductName("water");
        product2.setId(UUID.randomUUID());
        product2.setPriceList(priceList2);


        all.add(product1);
        all.add(product2);

    }

    @Test
    public void getAllProductsTest(){
        initData();
        when(productRepository.findAll()).thenReturn(all);

        assertEquals(2, productService.getAllProducts().size());
    }

    @Test
    public void getAllProductsDtoTest(){
        initData();
        when(productRepository.findAll()).thenReturn(all);

        Set<ProductDto> dtos = productService.getAllProducts();
        dtos.stream().
                filter((x)->{
                    return (new BigDecimal("45.90")).equals(x.getPrice()); }).
                findFirst().
                ifPresent(dto->{
                    assertEquals("water", dto.getName());
                });
        assertNotNull(dtos);
    }

    @Test
    public void updateProductNameItemNotFoundExceptionTest(){
        UpdateProductNameDto dto =new UpdateProductNameDto();
        UUID uuid = UUID.randomUUID();
        dto.setUuid(uuid.toString());

        when(productRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class,
                ()->productService.updateProductName(dto),"Should throw exception");
    }

    @Test
    public void updateProductPriceItemNotFoundExceptionTest(){
        UpdateProductPriceDto dto =new UpdateProductPriceDto();
        UUID uuid = UUID.randomUUID();
        dto.setUuid(uuid.toString());

        when(productRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class,
                ()->productService.updateProductPrice(dto),"Should throw exception");
    }


}
