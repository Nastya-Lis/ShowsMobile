package com.example.shows.model.database.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import java.util.List;

import lombok.Data;

@Data
@Entity(tableName = "user")
@ForeignKey(parentColumns = "id", childColumns = "role_id", entity = Role.class)
public class User extends CommonEntity{
    private String login;
    private String email;
    private String password;

    @ColumnInfo(name = "role_id")
    private int roleId;

    @Ignore
    private Role role;

    @Ignore
    private List<Booking> bookings;

}
