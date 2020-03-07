package com.shop.app.shop.service;

import com.shop.app.shop.dto.CreateProductDto;
import com.shop.app.shop.dto.ProductDto;
import com.shop.app.shop.dto.UpdateProductPriceDto;
import com.shop.app.shop.exception.ItemNotFoundException;
import com.shop.app.shop.dto.UpdateProductNameDto;
import com.shop.app.shop.helper.ProductHelper;
import com.shop.app.shop.model.Price;
import com.shop.app.shop.model.Product;
import com.shop.app.shop.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    private static final Logger LOGGER= LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public void createProduct(CreateProductDto createProductDto){
        Product product = ProductHelper.createFromDto(createProductDto);
        productRepository.save(product);
    }

    @Override
    public void updateProductPrice(UpdateProductPriceDto updateProductPriceDto){
        Optional<Product> productOptional = productRepository.findById(UUID.fromString(updateProductPriceDto.getUuid()));

        Product product = productOptional.orElseThrow(() -> {
            LOGGER.info("Cannot find the product with UUID: " + UUID.fromString(updateProductPriceDto.getUuid()));
            return new ItemNotFoundException("Cannot find the product");
        });

        Price newPrice=new Price();
        newPrice.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        newPrice.setProduct(product);
        newPrice.setPrice(updateProductPriceDto.getPrice());
        product.addPrice(newPrice);
        productRepository.save(product);

    }

    @Override
    public void updateProductName(UpdateProductNameDto updateProductNameDto){
        Optional<Product> productOptional = productRepository.findById(UUID.fromString(updateProductNameDto.getUuid()));

        Product product = productOptional.orElseThrow(() -> {
            LOGGER.info("Cannot find the product with UUID: " + UUID.fromString(updateProductNameDto.getUuid()));
            return new ItemNotFoundException("Cannot find the product");
        });
        product.setProductName(updateProductNameDto.getName());
        productRepository.save(product);


    }

    @Override
    public Set<ProductDto> getAllProducts(){
        List<Product> all = productRepository.findAll();
        final Set<ProductDto> dtoList = all.stream().map(product -> {
            ProductDto dto = new ProductDto();
            dto.setUuid(product.getId().toString());
            dto.setName(product.getProductName());
            product.getPriceList().stream()
                    .max(ProductHelper.priceDateComparator())
                    .ifPresent(
                            (x) -> dto.setPrice(x.getPrice())
                    );

            return dto;
        }).collect(Collectors.toSet());

        return dtoList;
    }


}
