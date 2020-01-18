package com.example.concedii;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date fromTimeStamp(Long timestamp){
        if(timestamp!=null ){
            return new Date(timestamp);
        }
        else return null;
    }

    @TypeConverter
    public static Long fromDate(Date date){
        if(date!=null){
            return date.getTime();
        }
        else return null;
    }
}
