package com.shop.app.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProductPriceDto {

    @ApiModelProperty(notes = "Product price")
    BigDecimal price;

    @ApiModelProperty(notes = "Product UUID")
    String uuid;


}


