package com.shop.app.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderDto {

    @ApiModelProperty(notes = "List of UUIDs of product that user want to order")
    private List<String> productUuidList;

    @ApiModelProperty(notes = "User's email address")
    private String email;


}
