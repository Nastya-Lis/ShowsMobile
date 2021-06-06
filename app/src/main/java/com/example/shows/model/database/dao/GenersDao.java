package com.example.shows.model.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.database.entity.Performance;

import java.util.List;

@Dao
public abstract class GenersDao extends CommonDao<Geners>{

    @Override
    @Query("SELECT * FROM gener")
    public abstract LiveData<List<Geners>> getAll();

    @Override
    @Query("SELECT * FROM gener WHERE id =:id")
    public abstract LiveData<Geners> getById(int id);

    @Query("SELECT * FROM gener LIMIT 1")
    public abstract LiveData<Geners> getFirst();

    @Query("DELETE FROM gener")
    public abstract void deleteAllGeners();
}
