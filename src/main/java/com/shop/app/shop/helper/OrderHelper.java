package com.shop.app.shop.helper;

import com.shop.app.shop.exception.InvalidParameterException;
import com.shop.app.shop.service.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderHelper {

    private static final Logger LOGGER= LoggerFactory.getLogger(OrderServiceImpl.class);

    public static Timestamp getTimestampFromString(String date){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(date);
            Timestamp dateTimestamp = new java.sql.Timestamp(parsedDate.getTime());
            return dateTimestamp;
        } catch(Exception e) {
            LOGGER.info("Cannot parse dates");
            throw new InvalidParameterException("Cannot parse dates");
        }
    }
}
