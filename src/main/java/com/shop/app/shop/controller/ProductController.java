package com.shop.app.shop.controller;
import com.shop.app.shop.dto.CreateProductDto;
import com.shop.app.shop.dto.ProductDto;
import com.shop.app.shop.dto.UpdateProductNameDto;
import com.shop.app.shop.dto.UpdateProductPriceDto;
import com.shop.app.shop.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("product")
@Api(description = "Endpoint responsible for creating orders and order buckets")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public void createProduct(
            @ApiParam(value = "Data transfer object containing parameters required for product creation", required = true) @RequestBody CreateProductDto createProductDto) {
        productService.createProduct(createProductDto);
    }

    @PutMapping("price")
    public void updateProductPrice(
            @ApiParam(value = "JSON object containing parameters required for product update", required = true) @RequestBody UpdateProductPriceDto updateProductPriceDto) {
        productService.updateProductPrice(updateProductPriceDto);
    }

    @PutMapping("name")
    public void updateProductName(
            @ApiParam(value = "JSON object containing parameters required for product update", required = true) @RequestBody UpdateProductNameDto updateProductNameDto) {
        productService.updateProductName(updateProductNameDto);
    }

    @GetMapping
    public Set<ProductDto> getAllProducts() {
        return productService.getAllProducts();

    }

}
