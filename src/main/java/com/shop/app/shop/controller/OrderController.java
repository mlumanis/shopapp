package com.shop.app.shop.controller;

import com.shop.app.shop.dto.CreateOrderDto;
import com.shop.app.shop.dto.HistoricalOrderListDto;
import com.shop.app.shop.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@Api(description = "Endpoint responsible for creating and retrieving orders ")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public void createOrder(
            @ApiParam(value = "JSON object containing data required for creation", required = true) @RequestBody CreateOrderDto orderDto) {
        orderService.createOrderBasket(orderDto);
    }

    @GetMapping(value = "/{dateFrom}/{dateTo}")
    public List<HistoricalOrderListDto> getOrdersInDateRange(
            @ApiParam(value = "Date after the orders were created", required = true) @PathVariable("dateFrom") String startDate,
            @ApiParam(value = "End date before the orders were created", required = true) @PathVariable("dateTo") String endDate) {
        return orderService.getOrdersInDateRange(startDate, endDate);
    }

}
