package com.shop.app.shop.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.shop.app.shop.dto.CreateOrUpdateProductDto;
import com.shop.app.shop.dto.CreateOrderDto;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void testIfOrderCreated() throws Exception {

        //create product
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        //create product
        CreateOrUpdateProductDto createDto1 = CreateOrUpdateProductDto.builder().price(new BigDecimal("120.00")).name("car").build();
        String requestJson1 = ow.writeValueAsString(createDto1);

        mockMvc.perform(post("/product").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson1))
                .andExpect(status().isOk());


        ResultActions resultActions = mockMvc.perform(get("/product/"))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        //get UUID
        HashSet<LinkedHashMap> response = mapper.readValue(contentAsString, HashSet.class);
        Assertions.assertEquals(1, response.size());

        String uuid = response.iterator().next().get("uuid").toString();

        //create order
        List<String> productUuids = new ArrayList<>();
        productUuids.add(uuid);

        CreateOrderDto createOrderDto = CreateOrderDto.builder().productUuidList(productUuids).email("mailemaplme@gmail.com").build();
        String createOrderRequestJson = ow.writeValueAsString(createOrderDto);

        mockMvc.perform(post("/order").contentType(APPLICATION_JSON_UTF8)
                .content(createOrderRequestJson))
                .andExpect(status().isOk());

        ResultActions resultOrderActions = mockMvc.perform(get("/order/2010-02-01/2080-01-04"))
                .andExpect(status().isOk());

        MvcResult resultOrder = resultOrderActions.andReturn();
        String ordersContentAsString = resultOrder.getResponse().getContentAsString();

        HashSet<LinkedHashMap> responseOrders = mapper.readValue(ordersContentAsString, HashSet.class);
        Assertions.assertEquals(1, responseOrders.size());

        String retrievedUuid = ((String) ((LinkedHashMap) ((ArrayList) responseOrders.iterator().next().get("orderedProducts")).iterator().next()).get("uuid")).toString();
        Assertions.assertEquals(retrievedUuid, uuid);

    }

}
