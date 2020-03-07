package com.shop.app.shop.service;

import com.shop.app.shop.dto.CreateOrderDto;
import com.shop.app.shop.dto.HistoricalOrderListDto;

import java.util.List;

public interface OrderService {
    void createOrderBasket(CreateOrderDto orderDto);
    List<HistoricalOrderListDto> getOrdersInDateRange(String startDate, String endDate);

}
