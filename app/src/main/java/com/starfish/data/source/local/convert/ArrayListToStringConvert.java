package com.starfish.data.source.local.convert;

import android.text.TextUtils;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListToStringConvert implements PropertyConverter<ArrayList<String>,String> {
    @Override
    public ArrayList<String> convertToEntityProperty(String databaseValue) {
        if(TextUtils.isEmpty(databaseValue)){
            return null;
        }

        String [] arr = databaseValue.split(",");

        if(arr.length == 0){
            return null;
        }
        return (ArrayList<String>) Arrays.asList(arr);
    }

    @Override
    public String convertToDatabaseValue(ArrayList<String> entityProperty) {
        if(entityProperty == null || entityProperty.size() == 0){
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < entityProperty.size(); i++){
            buffer.append(entityProperty.get(i));
            if(i != entityProperty.size() -1){
                buffer.append(",");
            }
        }

        return buffer.toString();
    }
}
