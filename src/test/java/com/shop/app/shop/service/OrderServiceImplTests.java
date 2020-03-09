package com.shop.app.shop.service;

import com.shop.app.shop.dto.CreateOrderDto;
import com.shop.app.shop.dto.HistoricalOrderListDto;
import com.shop.app.shop.exception.IncorrectEmailException;
import com.shop.app.shop.exception.InvalidParameterException;
import com.shop.app.shop.exception.ItemNotFoundException;
import com.shop.app.shop.model.OrderBasket;
import com.shop.app.shop.model.Price;
import com.shop.app.shop.model.Product;
import com.shop.app.shop.model.SingleOrder;
import com.shop.app.shop.repository.OrderRepository;
import com.shop.app.shop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTests {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ProductRepository productRepository;

    @Captor
    ArgumentCaptor<OrderBasket> orderBasketCaptor;

    @Test
    public void createOrderBasketEmptyListTest() {
        List<String> emptyList = new ArrayList();
        CreateOrderDto orderDto = CreateOrderDto.builder().email("simple@email.com").productUuidList(emptyList).build();
        assertThrows(InvalidParameterException.class,
                () -> orderService.createOrderBasket(orderDto), "Should throw exception");
    }

    @Test
    public void createOrderBasketWrongEmailTest() {
        List<String> uuidList = new ArrayList();
        uuidList.add(UUID.randomUUID().toString());
        uuidList.add(UUID.randomUUID().toString());
        CreateOrderDto orderDto = CreateOrderDto.builder().email("simple-email.com").productUuidList(uuidList).build();
        assertThrows(IncorrectEmailException.class,
                () -> orderService.createOrderBasket(orderDto), "Should throw exception");
    }

    @Test
    public void createOrderBasketSuccessTest() {
        List<String> uuidList = new ArrayList();
        UUID productUuid = UUID.randomUUID();

        uuidList.add(productUuid.toString());

        UUID priceUuid = UUID.randomUUID();

        Price price = Price.builder().
                createdOn(new Timestamp(System.currentTimeMillis())).
                price(new BigDecimal("123.00")).
                id(priceUuid).
                build();

        Set<Price> priceList = new HashSet<Price>();
        priceList.add(price);

        Product product = Product.builder().productName("car").id(productUuid).priceList(priceList).build();
        price.setProduct(product);

        CreateOrderDto orderDto = CreateOrderDto.builder().email("email@gmail.com").productUuidList(uuidList).build();

        //when
        when(productRepository.findById(productUuid)).
                thenReturn(Optional.of(product));

        orderService.createOrderBasket(orderDto);
        verify(orderRepository, times(1)).save(any());

    }

    @Test
    public void createOrderMostRecentPriceTest() {
        List<String> uuidList = new ArrayList();
        UUID productUuid = UUID.randomUUID();

        uuidList.add(productUuid.toString());

        UUID priceUuid1 = UUID.randomUUID();
        UUID priceUuid2 = UUID.randomUUID();

        Price price1 = Price.builder().
                createdOn(new Timestamp(System.currentTimeMillis())).
                price(new BigDecimal("123.00")).
                id(priceUuid1).
                build();

        Price price2 = Price.builder().
                createdOn(new Timestamp(System.currentTimeMillis() + 1000)).
                price(new BigDecimal("200.00")).
                id(priceUuid2).
                build();

        Set<Price> priceList = new HashSet<Price>();
        priceList.add(price1);
        priceList.add(price2);

        Product product = Product.builder().productName("car").id(productUuid).priceList(priceList).build();
        price1.setProduct(product);
        price2.setProduct(product);

        CreateOrderDto orderDto = CreateOrderDto.builder().email("email@gmail.com").productUuidList(uuidList).build();

        //when
        when(productRepository.findById(productUuid)).
                thenReturn(Optional.of(product));

        orderService.createOrderBasket(orderDto);
        verify(orderRepository, times(1)).save(orderBasketCaptor.capture());

        assertEquals(orderBasketCaptor.getValue().getOrders().get(0).getPrice().getPrice(), new BigDecimal("200.00"));

    }

    @Test
    public void createOrderEmptyPriceListTest() {
        List<String> uuidList = new ArrayList();
        UUID productUuid = UUID.randomUUID();

        uuidList.add(productUuid.toString());


        Set<Price> priceList = new HashSet<Price>();

        Product product = Product.builder().productName("car").id(productUuid).priceList(priceList).build();

        CreateOrderDto orderDto = CreateOrderDto.builder().email("email@gmail.com").productUuidList(uuidList).build();

        //when
        when(productRepository.findById(productUuid)).
                thenReturn(Optional.of(product));

        assertThrows(ItemNotFoundException.class,
                () -> orderService.createOrderBasket(orderDto), "Should throw exception");

    }

    @Test
    public void getOrdersInDateRangeInvalidDateFormatTest() {
        String dateStart = "sdfsfsd";
        String dateEnd = "3872394jsdf";

        assertThrows(InvalidParameterException.class,
                () -> orderService.getOrdersInDateRange(dateStart, dateEnd), "Should throw exception");
    }

    @Test
    public void getOrdersInDateRangeWhenNoOrdersInRepositoryTest() {
        String dateStart = "2018-09-09";
        String dateEnd = "2018-10-10";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedStartDate = dateFormat.parse(dateStart);
            Date parsedEndDate = dateFormat.parse(dateEnd);
            Timestamp startDateTimestamp = new java.sql.Timestamp(parsedStartDate.getTime());
            Timestamp endDateTimestamp = new java.sql.Timestamp(parsedEndDate.getTime());

            when(orderRepository.findOrdersInDateRange(startDateTimestamp, endDateTimestamp)).
                    thenReturn(new ArrayList<>());

            List<HistoricalOrderListDto> orders = orderService.getOrdersInDateRange(dateStart, dateEnd);
            assertEquals(orders.size(), 0);

        } catch (Exception e) {
            throw new RuntimeException("Couldn't parse dates");
        }

    }

    @Test
    public void getOrdersInDateRangeSuccessTest() {
        String dateStartString = "2090-09-09";
        String dateEndString = "2022-10-10";
        String orderDateString = "2020-02-02";


        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedStartDate = dateFormat.parse(dateStartString);
            Date parsedEndDate = dateFormat.parse(dateEndString);
            Timestamp startDateTimestamp = new java.sql.Timestamp(parsedStartDate.getTime());
            Timestamp endDateTimestamp = new java.sql.Timestamp(parsedEndDate.getTime());

            Date orderDate = dateFormat.parse(orderDateString);
            Timestamp orderDateTimestamp = new java.sql.Timestamp(orderDate.getTime());

            Price price = Price.builder().
                    id(UUID.randomUUID()).
                    createdOn(new Timestamp(System.currentTimeMillis())).
                    price(new BigDecimal("200.00")).
                    build();

            Set<Price> priceList = new HashSet<>();
            priceList.add(price);

            Product product = Product.builder().
                    id(UUID.randomUUID()).
                    productName("car").
                    priceList(priceList).
                    build();

            price.setProduct(product);

            SingleOrder singleOrder = SingleOrder.builder().id(UUID.randomUUID()).price(price).product(product).build();

            List<SingleOrder> singleOrderList = new ArrayList<SingleOrder>();
            singleOrderList.add(singleOrder);

            OrderBasket order = OrderBasket.builder().id(UUID.randomUUID()).orderTimestamp(orderDateTimestamp).orders(singleOrderList).build();

            List<OrderBasket> listOrderBaskets = new ArrayList<OrderBasket>();

            listOrderBaskets.add(order);
            when(orderRepository.findOrdersInDateRange(startDateTimestamp, endDateTimestamp)).
                    thenReturn(listOrderBaskets);

            List<HistoricalOrderListDto> orders = orderService.getOrdersInDateRange(dateStartString, dateEndString);
            assertEquals(orders.size(), 1);
            assertEquals(orders.get(0).getOrderedProducts().size(), 1);
            assertEquals(orders.get(0).getOrderedProducts().get(0).getName(), "car");
            assertEquals(orders.get(0).getOrderedProducts().get(0).getPrice(), new BigDecimal("200.00"));


        } catch (Exception e) {
            throw new RuntimeException("Couldn't parse dates");
        }


    }


}
