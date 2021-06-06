package com.example.shows.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shows.model.database.entity.CommonEntity;
import com.example.shows.model.database.entity.Performance;

import java.util.List;

public abstract class CommonDao<E extends CommonEntity> {
    @Insert
    public abstract void insert(E entity);

    @Update
    public abstract void update(E entity);

    @Delete
    public abstract void delete(E entity);

    public abstract LiveData<List<E>> getAll();
    public abstract LiveData<E> getById(int id);
}
