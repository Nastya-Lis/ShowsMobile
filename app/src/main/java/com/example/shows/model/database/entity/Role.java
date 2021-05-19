package com.example.shows.model.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import java.util.List;
import java.util.Set;

import lombok.Data;


@Data
@Entity(tableName = "role")
public class Role extends CommonEntity {
    private String name;
    @Ignore
    private Set<User> users;
}
