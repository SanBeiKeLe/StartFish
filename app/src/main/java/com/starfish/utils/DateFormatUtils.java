package com.starfish.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateFormatUtils {

    public static final String FORMAT_1 = "MM月dd日 E";
    public static final String FORMAT_2 = "yyyyMMdd";




    public static String getFormattedDateString(String value,String format){

        Date date = getDateFromString(value);
        if(date != null){
           return new SimpleDateFormat(FORMAT_1,Locale.CHINA).format(date);
        }

        return null;
    }


    public static Date getDateFromString(String value){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_2, Locale.CHINA);
        try {
           return simpleDateFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }
}
