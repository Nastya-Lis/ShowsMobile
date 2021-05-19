package com.example.shows.model.network.dto;

import androidx.room.TypeConverters;

import com.example.shows.model.database.entity.converter.ConverterDateType;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import lombok.Data;

@Data
public class PerformanceDto extends AbstractDto{

    private String name;
    private String description;
    @TypeConverters(ConverterDateType.class)
    private Timestamp date;
    private String duration;
    private int amountTickets;
    private double price;
    private double rating;

    private Collection<Integer> bookingsId;
    private GenersDto genre;

    private Collection<ActorDto> actors;
    private Collection<ScenaristDto> scenarists;
}
