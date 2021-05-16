package com.example.shows.model.network.dto;

import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.Role;

import java.util.List;

import lombok.Data;

@Data
public class UserDto extends AbstractDto{
    private String login;
    private String email;
    private String password;
    private List<Booking> bookings;
    private Role role;
}
