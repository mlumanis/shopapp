package com.shop.app.shop.helper;

import com.shop.app.shop.exception.InvalidParameterException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderHelper {

    public static Timestamp getTimestampFromString(String date){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedStartDate = dateFormat.parse(date);
            Timestamp startDateTimestamp = new java.sql.Timestamp(parsedStartDate.getTime());
            return startDateTimestamp;
        } catch(Exception e) {
            throw new InvalidParameterException("Cannot parse dates");
        }
    }
}
