package com.example.shows.model.network.dto;

import java.io.Serializable;

public abstract class AbstractDto implements Serializable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
