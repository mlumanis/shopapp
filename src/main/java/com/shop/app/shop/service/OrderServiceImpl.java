package com.shop.app.shop.service;


import com.shop.app.shop.dto.CreateOrderDto;
import com.shop.app.shop.dto.HistoricalOrderListDto;
import com.shop.app.shop.dto.ProductDto;
import com.shop.app.shop.exception.IncorrectEmailException;
import com.shop.app.shop.exception.ItemNotFoundException;
import com.shop.app.shop.exception.InvalidParameterException;
import com.shop.app.shop.helper.EmailValidator;
import com.shop.app.shop.helper.ProductHelper;
import com.shop.app.shop.model.OrderBasket;
import com.shop.app.shop.model.Price;
import com.shop.app.shop.model.Product;
import com.shop.app.shop.model.SingleOrder;
import com.shop.app.shop.repository.OrderRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.shop.app.shop.helper.OrderHelper.getTimestampFromString;

/**
 * This service is responsible for updating  and creating incoming orders
 *
 * @author  Maciej Lumanisha
 *
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    private static final Logger LOGGER=LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public void createOrderBasket(CreateOrderDto orderDto) {

        if(orderDto.getProductUuidList().isEmpty()) {
            LOGGER.info("List of products is empty");
            throw new InvalidParameterException("List of products is empty");
        }

        if(!EmailValidator.isValid(orderDto.getEmail())) {
            LOGGER.info("Incorrect email format");
            throw new IncorrectEmailException("Incorrect email format");
        }
        OrderBasket orderBasket = new OrderBasket();
        orderBasket.setEmail(orderDto.getEmail());
        List<SingleOrder> singleOrderList = orderDto.getProductUuidList().stream().map(x -> {
            Optional<Product> productOptional = productRepository.findByUUID(UUID.fromString(x));

            Product product = productOptional.orElseThrow(() -> {
                LOGGER.info("Cannot find product with UIID: " + x);
                return new ItemNotFoundException("Cannot find product");
            });


            SingleOrder singleOrder = new SingleOrder();
            singleOrder.setProduct(product);
            Price actualPrice = product.getPriceList().stream().max(ProductHelper.priceDateComparator()).orElseThrow(
                    () -> {
                        LOGGER.info("Cannot find current price");
                        return new ItemNotFoundException("Cannot find current price");
                    }
            );

            singleOrder.setPrice(actualPrice);
            singleOrder.setBasket(orderBasket);
            return singleOrder;

        }).collect(Collectors.toList());
        orderBasket.setOrders(singleOrderList);
        orderBasket.setOrderTimestamp(new Timestamp(System.currentTimeMillis()));

        orderRepository.save(orderBasket);
    }

    @Override
    public List<HistoricalOrderListDto> getOrdersInDateRange(String startDate, String endDate) {
        Timestamp startDateTimestamp = getTimestampFromString(startDate);
        Timestamp endDateTimestamp = getTimestampFromString(endDate);
        List<OrderBasket> orderBasketList = orderRepository.findOrdersInDateRange(startDateTimestamp, endDateTimestamp);


        List<HistoricalOrderListDto> finalDto = orderBasketList.stream().map(basketOrder -> {
            HistoricalOrderListDto basketDtoList = new HistoricalOrderListDto();
            basketDtoList.setOrderTimestamp(basketOrder.getOrderTimestamp());

            List<ProductDto> updateProductNameDtoList = basketOrder.getOrders().stream().map(
                    singleOrder -> {
                        ProductDto productDto = new ProductDto();
                        productDto.setPrice(singleOrder.getPrice().getPrice());
                        productDto.setName(singleOrder.getProduct().getProductName());
                        productDto.setUuid(singleOrder.getProduct().getUuid().toString());
                        return productDto;
            }).collect(Collectors.toList());

            basketDtoList.setOrderedProducts(updateProductNameDtoList);
            return basketDtoList;

        }).collect(Collectors.toList());

        return finalDto;
    }
}
