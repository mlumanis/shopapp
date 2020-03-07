package com.shop.app.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProductNameDto {

    @ApiModelProperty(notes = "Product name")
    String name;

    @ApiModelProperty(notes = "Product UUID")
    String uuid;

}
