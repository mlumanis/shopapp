package com.shop.app.shop.integration.DtoWrapper;

import com.shop.app.shop.dto.HistoricalOrderListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalOrderListDtoListWrapper {

    ArrayList<HistoricalOrderListDto> orders;
}
