package com.example.shows.model.database.entity.converter;


import androidx.room.TypeConverter;

import java.sql.Timestamp;

public class ConverterDateType {

    @TypeConverter
    public Timestamp toTimestamp(Long time){
        return new Timestamp(time);
    }

    @TypeConverter
    public Long toLong(Timestamp timestamp){
        return timestamp.getTime();
    }
}
