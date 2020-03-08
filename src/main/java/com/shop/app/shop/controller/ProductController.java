package com.shop.app.shop.controller;
import com.shop.app.shop.dto.*;
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
            @ApiParam(value = "Data transfer object containing parameters required for product creation", required = true)
            @RequestBody CreateOrUpdateProductDto createProductDto) {
        productService.createProduct(createProductDto);
    }

    @PatchMapping("/{uuid}")
    public void partialProductUpdate(
            @ApiParam(value = "JSON object containing parameters required for product's name update", required = true)
            @RequestBody CreateOrUpdateProductDto updateProductDto,
            @ApiParam(value = "Product's UUID", required = true)
            @PathVariable("uuid") String uuid) {
        productService.partialUpdateProduct(updateProductDto, uuid);
    }

    @PutMapping("/{uuid}")
    public void productUpdate(
            @ApiParam(value = "JSON object containing parameters required for product's name update", required = true)
            @RequestBody CreateOrUpdateProductDto updateProductDto,
            @ApiParam(value = "Product's UUID", required = true)
            @PathVariable("uuid") String uuid) {
        productService.updateProduct(updateProductDto, uuid);
    }

    @GetMapping
    public Set<ProductDto> getAllProducts() {
        return productService.getAllProducts();

    }

}
