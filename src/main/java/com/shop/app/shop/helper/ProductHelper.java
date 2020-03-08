package com.shop.app.shop.helper;

import com.shop.app.shop.dto.CreateOrUpdateProductDto;
import com.shop.app.shop.model.Price;
import com.shop.app.shop.model.Product;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Optional;


public class ProductHelper {

    public static Optional<Product> createFromDto(CreateOrUpdateProductDto dto){

        if(dto.getName() == null || dto.getPrice() == null){
            return Optional.empty();
        }

        Product product =new Product();
        product.setProductName(dto.getName());
        Price price = Price.builder().
                price(dto.getPrice()).
                createdOn(new Timestamp(System.currentTimeMillis())).
                product(product).
                build();

        product.addPrice(price);

        return  Optional.ofNullable(product);
    }

    public static Comparator<Price> priceDateComparator(){
        Comparator<Price> cmp = new Comparator<Price>() {
            @Override
            public int compare(Price a, Price b) {
                return a.getCreatedOn().compareTo(b.getCreatedOn());
            }
        };

        return cmp;
    }
}
