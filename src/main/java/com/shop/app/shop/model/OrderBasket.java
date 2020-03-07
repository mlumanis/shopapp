package com.shop.app.shop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class OrderBasket {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "basket"
    )
    private List<SingleOrder> orders= new ArrayList<>();

    @Column(name = "ORDER_TIMESTAMP", updatable = false, nullable = false)
    private Timestamp orderTimestamp;

    public void addSingleOrder(SingleOrder singleOrder){
        orders.add(singleOrder);
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
        OrderBasket other = (OrderBasket) obj;
        return id.equals(other.getId());
    }
}
