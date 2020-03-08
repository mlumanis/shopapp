package com.shop.app.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrUpdateProductDto {

    @ApiModelProperty(notes = "Product price")
    BigDecimal price;

    @ApiModelProperty(notes = "Product name")
    String name;


}


