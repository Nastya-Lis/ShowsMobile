package com.example.shows.model.database.entity;


import androidx.room.PrimaryKey;

import java.io.Serializable;

public abstract class CommonEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
