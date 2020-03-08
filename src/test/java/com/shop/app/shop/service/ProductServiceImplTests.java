package com.shop.app.shop.service;

import com.shop.app.shop.dto.CreateOrUpdateProductDto;
import com.shop.app.shop.dto.ProductDto;
import com.shop.app.shop.exception.InvalidParameterException;
import com.shop.app.shop.exception.ItemNotFoundException;
import com.shop.app.shop.model.Price;
import com.shop.app.shop.model.Product;
import com.shop.app.shop.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTests {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Captor
    ArgumentCaptor<Product> productCaptor;

    private List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList();
        //Product 1

        Product product1 = new Product();
        Set priceList1 = new HashSet();
        Price price1 = new Price();

        price1.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        price1.setProduct(product1);

        price1.setId(UUID.randomUUID());
        price1.setPrice(new BigDecimal("12.90"));

        Price price2 = new Price();
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

        Price price3 = new Price();
        price2.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        price2.setProduct(product2);
        price2.setId(UUID.randomUUID());
        price2.setPrice(new BigDecimal("45.90"));

        priceList2.add(price3);

        product2.setProductName("water");
        product2.setId(UUID.randomUUID());
        product2.setPriceList(priceList2);


        allProducts.add(product1);
        allProducts.add(product2);


        return allProducts;
    }

    @Test
    public void createProductNullNameTest() {
        CreateOrUpdateProductDto dto = new CreateOrUpdateProductDto();


        assertThrows(InvalidParameterException.class,
                () -> productService.createProduct(dto), "Should throw exception");

        verify(productRepository, never()).save(any());
    }

    @Test
    public void shouldSaveProductTest() {
        CreateOrUpdateProductDto carDto = CreateOrUpdateProductDto.
                builder().name("car").price(new BigDecimal("120.00")).build();

        productService.createProduct(carDto);

        verify(productRepository, times(1)).save(productCaptor.capture());
        Product productCaptorValue = productCaptor.getValue();
        
        //Check if productRepository.save() was invoked with proper arguments
        assertEquals(productCaptorValue.getProductName(),carDto.getName());
        assertEquals(productCaptorValue.getPriceList().size(),1);
        assertEquals(productCaptorValue.getPriceList().stream().findFirst().get().getPrice(), carDto.getPrice());

    }

    @Test
    public void getAllProductsTest() {
        when(productRepository.findAll()).thenReturn(getAllProducts());

        assertEquals(2, productService.getAllProducts().size());
    }

    @Test
    public void getAllProductsDtoTest() {
        when(productRepository.findAll()).thenReturn(getAllProducts());

        Set<ProductDto> dtos = productService.getAllProducts();
        dtos.stream().
                filter((x) -> {
                    return (new BigDecimal("45.90")).equals(x.getPrice());
                }).
                findFirst().
                ifPresent(dto -> {
                    assertEquals("water", dto.getName());
                });
        assertNotNull(dtos);
    }

    @Test
    public void updateProductNameItemNotFoundExceptionTest() {
        CreateOrUpdateProductDto dto = new CreateOrUpdateProductDto();
        UUID uuid = UUID.randomUUID();


        when(productRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class,
                () -> productService.partialUpdateProduct(dto, uuid.toString()), "Should throw exception");
    }

    @Test
    public void updateProductPriceItemNotFoundExceptionTest() {
        CreateOrUpdateProductDto dto = new CreateOrUpdateProductDto();
        UUID uuid = UUID.randomUUID();

        when(productRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class,
                () -> productService.partialUpdateProduct(dto, uuid.toString()), "Should throw exception");
    }

    @Test
    public void productUpdateNullPriceTest() {
        CreateOrUpdateProductDto dto = new CreateOrUpdateProductDto();
        dto.setName("productName");
        UUID uuid = UUID.randomUUID();

        when(productRepository.findById(uuid)).
                thenReturn(
                        Optional.of(Product.builder().id(UUID.randomUUID()).productName("productName").build()));

        assertThrows(InvalidParameterException.class,
                () -> productService.updateProduct(dto, uuid.toString()), "Should throw exception");
    }


    @Test
    public void productUpdateNullNameTest() {
        CreateOrUpdateProductDto dto = new CreateOrUpdateProductDto();
        dto.setPrice(new BigDecimal("12.80"));
        UUID uuid = UUID.randomUUID();

        when(productRepository.findById(uuid)).
                thenReturn(
                        Optional.of(Product.builder().id(UUID.randomUUID()).productName("productName").build()));

        assertThrows(InvalidParameterException.class,
                () -> productService.updateProduct(dto, uuid.toString()), "Should throw exception");
    }

    @Test
    public void productUpdateInvalidUUIDTest() {
        CreateOrUpdateProductDto dto = new CreateOrUpdateProductDto();
        dto.setPrice(new BigDecimal("12.80"));
        String invalidUUID = "2342;234234";
        UUID uuid = UUID.randomUUID();

        assertThrows(IllegalArgumentException .class,
                () -> productService.updateProduct(dto, invalidUUID), "Should throw exception");
    }


}
