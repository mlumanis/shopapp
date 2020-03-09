package com.shop.app.shop.service;

import com.shop.app.shop.dto.*;
import com.shop.app.shop.exception.InvalidParameterException;
import com.shop.app.shop.exception.ItemNotFoundException;
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

public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    private static final Logger LOGGER= LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    @Transactional
    public void createProduct(CreateOrUpdateProductDto createProductDto) {

        Optional<Product> productOptional = ProductHelper.createFromDto(createProductDto);
        Product product = productOptional.orElseThrow(() -> {
            LOGGER.info("Some fields are empty");
            return new InvalidParameterException("Some fields are empty");
        });
        productRepository.save(product);
    }


    @Override
    @Transactional
    public void partialUpdateProduct(CreateOrUpdateProductDto updateProductDto, String uuid) {

        Optional<Product> productOptional = productRepository.findByUUID(UUID.fromString(uuid));

        Product product = productOptional.orElseThrow(() -> {
            LOGGER.info("Cannot find the product with UUID: " + UUID.fromString(uuid));
            return new ItemNotFoundException("Cannot find the product");
        });

        if(updateProductDto.getName() != null){
            product.setProductName(updateProductDto.getName());
        }

        if(updateProductDto.getPrice() != null){
            Price newPrice = Price.builder().
                    createdOn(new Timestamp(System.currentTimeMillis())).
                    price(updateProductDto.getPrice()).
                    product(product).
                    build();
            product.addPrice(newPrice);
        }

        productRepository.save(product);


    }

    @Override
    @Transactional
    public void updateProduct(CreateOrUpdateProductDto updateProductDto, String uuid) {
        Optional<Product> productOptional = productRepository.findByUUID(UUID.fromString(uuid));

        Product product = productOptional.orElseThrow(() -> {
            LOGGER.info("Cannot find the product with UUID: " + UUID.fromString(uuid));
            return new ItemNotFoundException("Cannot find the product");
        });

        if(updateProductDto.getName() != null){
            product.setProductName(updateProductDto.getName());
        }else{
            LOGGER.info("Cannot update Product. Name is null");
            throw new InvalidParameterException("Cannot update Product. Name is null");
        }

        if(updateProductDto.getPrice() != null){
            Price newPrice = Price.builder().
                    createdOn(new Timestamp(System.currentTimeMillis())).
                    price(updateProductDto.getPrice()).
                    product(product).
                    build();
            product.addPrice(newPrice);
        }else{
            LOGGER.info("Cannot update Product. Price is null");
            throw new InvalidParameterException("Cannot update Product. Price is null");
        }

        productRepository.save(product);


    }

    @Override
    @Transactional
    public Set<ProductDto> getAllProducts(){
        List<Product> all = productRepository.findAll();
        final Set<ProductDto> dtoList = all.stream().map(product -> {

            ProductDto dto = ProductDto.builder().
                    uuid(product.getUuid().toString()).
                    name(product.getProductName()).
                    build();

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
