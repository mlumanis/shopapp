package com.shop.app.shop.service;

import com.shop.app.shop.dto.*;

import java.util.Set;

public interface ProductService {
    void createProduct(CreateOrUpdateProductDto createProductDto);
    void partialUpdateProduct(CreateOrUpdateProductDto updateProductNameDto, String uuid);
    void updateProduct(CreateOrUpdateProductDto updateProductNameDto, String uuid);
    Set<ProductDto> getAllProducts();
}
