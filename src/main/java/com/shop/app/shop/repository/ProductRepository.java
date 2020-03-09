package com.shop.app.shop.repository;

import com.shop.app.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT product FROM Product product WHERE product.uuid = :uuid")
    Optional<Product> findByUUID(@Param("uuid") UUID uuid);
}
