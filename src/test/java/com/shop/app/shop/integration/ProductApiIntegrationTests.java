package com.shop.app.shop.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.shop.app.shop.dto.CreateOrUpdateProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.LinkedHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void testIfCreated() throws Exception {

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        CreateOrUpdateProductDto createDto1 = CreateOrUpdateProductDto.builder().price(new BigDecimal("120.00")).name("car").build();
        String requestJson1 = ow.writeValueAsString(createDto1);

        mockMvc.perform(post("/product").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson1))
                .andExpect(status().isOk());

        CreateOrUpdateProductDto createDto2 = CreateOrUpdateProductDto.builder().price(new BigDecimal("200.00")).name("pen").build();
        String requestJson2 = ow.writeValueAsString(createDto2);

        mockMvc.perform(post("/product").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson2))
                .andExpect(status().isOk());


        ResultActions resultActions = mockMvc.perform(get("/product/"))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        HashSet<LinkedHashMap> response = mapper.readValue(contentAsString, HashSet.class);
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void testIfUpdated() throws Exception {

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        CreateOrUpdateProductDto createDto1 = CreateOrUpdateProductDto.builder().price(new BigDecimal("120.00")).name("car").build();
        String requestJson1 = ow.writeValueAsString(createDto1);

        mockMvc.perform(post("/product").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson1))
                .andExpect(status().isOk());


        ResultActions resultActions = mockMvc.perform(get("/product/"))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();


        HashSet<LinkedHashMap> response = mapper.readValue(contentAsString, HashSet.class);
        Assertions.assertEquals(1, response.size());

        String uuid = response.iterator().next().get("uuid").toString();

        CreateOrUpdateProductDto updateDto = CreateOrUpdateProductDto.builder().price(new BigDecimal("500.02")).name("pen").build();
        String requestJsonUpdate = ow.writeValueAsString(updateDto);


        mockMvc.perform(put("/product/" + uuid).contentType(APPLICATION_JSON_UTF8)
                .content(requestJsonUpdate))
                .andExpect(status().isOk());

        ResultActions resultActionsGet = mockMvc.perform(get("/product/"))
                .andExpect(status().isOk());

        MvcResult resultGet = resultActionsGet.andReturn();
        String contentAsStringGet = resultGet.getResponse().getContentAsString();

        HashSet<LinkedHashMap> responseGet = mapper.readValue(contentAsStringGet, HashSet.class);
        Assertions.assertEquals(1, response.size());


        Assertions.assertEquals("500.02", responseGet.iterator().next().get("price").toString());
        Assertions.assertEquals("pen", responseGet.iterator().next().get("name").toString());

    }

    @Test
    public void testIfPartiallyUpdated() throws Exception {

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        CreateOrUpdateProductDto createDto1 = CreateOrUpdateProductDto.builder().price(new BigDecimal("120.00")).name("car").build();
        String requestJson1 = ow.writeValueAsString(createDto1);

        mockMvc.perform(post("/product").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson1))
                .andExpect(status().isOk());


        ResultActions resultActions = mockMvc.perform(get("/product/"))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();


        HashSet<LinkedHashMap> response = mapper.readValue(contentAsString, HashSet.class);
        Assertions.assertEquals(1, response.size());

        String uuid = response.iterator().next().get("uuid").toString();

        CreateOrUpdateProductDto updateDto = CreateOrUpdateProductDto.builder().price(new BigDecimal("50.13")).build();
        String requestJsonUpdate = ow.writeValueAsString(updateDto);


        mockMvc.perform(patch("/product/" + uuid).contentType(APPLICATION_JSON_UTF8)
                .content(requestJsonUpdate))
                .andExpect(status().isOk());

        ResultActions resultActionsGet = mockMvc.perform(get("/product/"))
                .andExpect(status().isOk());

        MvcResult resultGet = resultActionsGet.andReturn();
        String contentAsStringGet = resultGet.getResponse().getContentAsString();

        HashSet<LinkedHashMap> responseGet = mapper.readValue(contentAsStringGet, HashSet.class);
        Assertions.assertEquals(1, response.size());


        Assertions.assertEquals("50.13", responseGet.iterator().next().get("price").toString());

    }

}
