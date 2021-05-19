package com.example.shows.model.database.entity;


import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.List;
import java.util.Set;

@Entity(tableName = "scenarist")
public class Scenarist extends CommonEntity{
    private String name;
    private String biography;

    @Ignore
    private Set<Performance> performances;
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

    public Set<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(Set<Performance> performances) {
        this.performances = performances;
    }


}
