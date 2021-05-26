package com.example.shows.model.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.Performance;

import java.util.List;
import java.util.Optional;

@Dao
public abstract class ActorDao extends CommonDao<Actor>{
    @Override
    @Query("SELECT * FROM actor")
    public abstract LiveData<List<Actor>> getAll();

    @Query("DELETE FROM actor")
    public abstract void deleteAllActors();

    @Override
    @Query("SELECT *FROM actor WHERE id =:id")
    public abstract LiveData<Actor> getById(int id);

    @Query("SELECT * FROM actor WHERE name like :name")
    public abstract Actor findActorByName(String name);
}
