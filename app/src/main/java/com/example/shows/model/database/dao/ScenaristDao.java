package com.example.shows.model.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.Scenarist;

import java.util.List;

@Dao
public abstract class ScenaristDao extends CommonDao<Scenarist>{
    @Override
    @Query("SELECT * FROM scenarist")
    public abstract List<Scenarist> getAll();

    @Override
    @Query("SELECT * FROM scenarist WHERE id=:id")
    public abstract LiveData<Scenarist> getById(int id);
}
