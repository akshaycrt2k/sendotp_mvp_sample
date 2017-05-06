package com.akshaycrt2k.kisannetworkotp.serializers;

import com.activeandroid.serializer.TypeSerializer;

import java.util.TimeZone;

import hirondelle.date4j.DateTime;

/**
 * DateTimeSerializer
 * Required to serialize Date4J dateTime
 * -------------------------------------------------------------
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class DateTimeSerializer extends TypeSerializer {
    @Override
    public Class<?> getDeserializedType() {
        return DateTime.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public Object serialize(Object data) {
        String serializedStr  = ((DateTime)data).getNanosecondsInstant(TimeZone.getDefault())+"";

        return serializedStr;
    }

    @Override
    public Object deserialize(Object data) {

        DateTime dateTime = DateTime.forInstantNanos(Long.parseLong((String)data),TimeZone.getDefault());
        return dateTime;
    }
}
