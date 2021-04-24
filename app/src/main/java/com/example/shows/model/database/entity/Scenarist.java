package com.example.shows.model.database.entity;


import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.List;

@Entity(tableName = "scenarist")
public class Scenarist extends CommonEntity{
    private String name;
    private String biography;

    @Ignore
    private List<Performance> performances;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }


}
