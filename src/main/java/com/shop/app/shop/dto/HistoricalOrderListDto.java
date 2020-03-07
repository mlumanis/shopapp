package com.shop.app.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoricalOrderListDto {

    @ApiModelProperty(notes = "Timestamp that contains order creation date")
    Timestamp orderTimestamp;

    @ApiModelProperty(notes = "List of products that have been order in the same order bucket")
    List<ProductDto> orderedProducts= new ArrayList<>();

    public void addProductDto(ProductDto dto){
        orderedProducts.add(dto);
    }

}
