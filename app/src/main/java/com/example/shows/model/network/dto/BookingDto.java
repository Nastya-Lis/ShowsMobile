package com.example.shows.model.network.dto;

import lombok.Data;

@Data
public class BookingDto extends AbstractDto{
    private int amount;
    private int user;
    private int performance;
}
