package com.shop.app.shop.helper;

import com.shop.app.shop.dto.CreateProductDto;
import com.shop.app.shop.model.Price;
import com.shop.app.shop.model.Product;

import java.sql.Timestamp;
import java.util.Comparator;


public class ProductHelper {

    public static Product createFromDto(CreateProductDto dto){
        Product product =new Product();
        product.setProductName(dto.getName());
        Price price = new Price();
        price.setPrice(dto.getPrice());
        price.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        price.setProduct(product);
        product.addPrice(price);

        return product;
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
