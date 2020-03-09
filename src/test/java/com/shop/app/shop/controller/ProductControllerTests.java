package com.shop.app.shop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.shop.app.shop.dto.CreateOrUpdateProductDto;
import com.shop.app.shop.dto.ProductDto;
import com.shop.app.shop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper mapper;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void createProduct_thenReturns200() throws Exception {
        CreateOrUpdateProductDto createDto = CreateOrUpdateProductDto.builder().price(new BigDecimal("120.00")).name("car").build();

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(createDto);

        mockMvc.perform(post("/product").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void createProduct_invalidJSON() throws Exception {

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString("gdhk;;453ggss");

        mockMvc.perform(post("/product").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProduct_thenReturns200() throws Exception {
        CreateOrUpdateProductDto createDto = CreateOrUpdateProductDto.builder().price(new BigDecimal("120.00")).name("car").build();

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(createDto);

        mockMvc.perform(put("/product/f7f3c9b7-2783-48b9-8bc9-6bb7b6990213").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void partialUpdateProduct_thenReturns200() throws Exception {
        CreateOrUpdateProductDto createDto = CreateOrUpdateProductDto.builder().name("car").build();

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(createDto);

        mockMvc.perform(patch("/product/f7f3c9b7-2783-48b9-8bc9-6bb7b6990213").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void partialUpdateProduct_InvalidDto() throws Exception {
        List<String> stringList= new ArrayList<>();
        stringList.add("string1");

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(stringList);

        mockMvc.perform(patch("/product/f7f3c9b7-2783-48b9-8bc9-6bb7b6990213").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllProducts_thenReturns200() throws Exception {

        ProductDto productDto1 = ProductDto.builder().
                uuid("d9122098-5b1f-4080-a216-26ea25a788c0").
                price(new BigDecimal("120.0")).
                name("car").build();

        ProductDto productDto2 = ProductDto.builder().
                uuid("d9189098-5b1f-4080-a296-26ea25a788c0").
                price(new BigDecimal("200.0")).
                name("table").build();

        Set<ProductDto> productDtos= new HashSet<ProductDto>();
        productDtos.add(productDto1);
        productDtos.add(productDto2);

        when(productService.getAllProducts()).thenReturn(productDtos);
        mockMvc.perform(get("/product/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"price\":200.0,\"uuid\":\"d9189098-5b1f-4080-a296-26ea25a788c0\",\"name\":\"table\"},{\"price\":120.0,\"uuid\":\"d9122098-5b1f-4080-a216-26ea25a788c0\",\"name\":\"car\"}]\n"));
    }

    @Test
    void getAllProductsEmptyList_thenReturns200() throws Exception {

        Set<ProductDto> productDtos= new HashSet<ProductDto>();

        when(productService.getAllProducts()).thenReturn(productDtos);
        mockMvc.perform(get("/product/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}


