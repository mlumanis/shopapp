package com.shop.app.shop.service;

import com.shop.app.shop.dto.CreateProductDto;
import com.shop.app.shop.dto.ProductDto;
import com.shop.app.shop.dto.UpdateProductNameDto;
import com.shop.app.shop.dto.UpdateProductPriceDto;

import java.util.Set;

public interface ProductService {
    void createProduct(CreateProductDto createProductDto);
    void updateProductPrice(UpdateProductPriceDto updateProductPriceDto);
    void updateProductName(UpdateProductNameDto updateProductNameDto);
    Set<ProductDto> getAllProducts();
}
