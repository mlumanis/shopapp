package com.shop.app.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
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
@Builder
@AllArgsConstructor
public class OrderBasket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_basket_generator")
    @SequenceGenerator(name = "order_basket_generator", sequenceName = "order_basket_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    @Builder.Default
    private UUID uuid = UUID.randomUUID();

    @Column(name = "email", nullable = false)
    private String email;

    public OrderBasket() {
        this.uuid = UUID.randomUUID();
    }

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "basket"
    )
    @Builder.Default
    private List<SingleOrder> orders = new ArrayList<>();

    @Column(name = "ORDER_TIMESTAMP", updatable = false, nullable = false)
    private Timestamp orderTimestamp;

    public void addSingleOrder(SingleOrder singleOrder) {
        orders.add(singleOrder);
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
        OrderBasket other = (OrderBasket) obj;
        return uuid.equals(other.getUuid());
    }
}
