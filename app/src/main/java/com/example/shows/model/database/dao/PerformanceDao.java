package com.example.shows.model.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.shows.model.database.entity.Performance;

import java.util.List;

@Dao
public abstract class PerformanceDao extends CommonDao<Performance>{

    @Override
    @Query("SELECT * FROM performance")
    public abstract LiveData<List<Performance>> getAll();

    @Override
    @Query("Select * from performance Where id=:id")
    public abstract LiveData<Performance> getById(int id);

    @Query("SELECT * FROM performance WHERE genre_id=:genreId")
    public abstract LiveData<List<Performance>> getByGenre(int genreId);
}