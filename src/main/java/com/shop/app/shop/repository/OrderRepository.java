package com.shop.app.shop.repository;

import com.shop.app.shop.model.OrderBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderBasket, UUID> {

    @Query("SELECT basket FROM OrderBasket basket WHERE basket.orderTimestamp > :startDate AND basket.orderTimestamp < :endDate")
    List<OrderBasket> findOrdersInDateRange(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}
