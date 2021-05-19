package com.example.shows.model.database.entity;


import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Collection;
import java.util.List;

@Entity(tableName = "gener")
public class Geners extends CommonEntity{
    private String nameType;

    @Ignore
    private Collection<Performance> performances;

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public Collection<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(Collection<Performance> performances) {
        this.performances = performances;
    }

}
