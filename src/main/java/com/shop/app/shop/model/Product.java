package com.shop.app.shop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type="uuid-char")
    private UUID id;


    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "product"
    )
    private Set<Price> priceList=new HashSet<Price>();


    public void addPrice(Price price){
        priceList.add(price);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        return id.equals(other.getId());
    }
}
