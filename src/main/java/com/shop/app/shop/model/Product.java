package com.shop.app.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name = "product_generator", sequenceName = "product_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;


    @Column(name = "uuid", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    @Builder.Default
    private UUID uuid = UUID.randomUUID();


    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;


    public Product(){
        this.uuid = UUID.randomUUID();
    }

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "product"
    )
    private Set<Price> priceList = new HashSet<Price>();


    public void addPrice(Price price) {
        priceList.add(price);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
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
        return uuid.equals(other.getUuid());
    }
}
