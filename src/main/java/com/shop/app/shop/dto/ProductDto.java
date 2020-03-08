package com.shop.app.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {

    @ApiModelProperty(notes = "Product price")
    BigDecimal price;

    @ApiModelProperty(notes = "Product UUID")
    String uuid;

    @ApiModelProperty(notes = "Product name")
    String name;


}


