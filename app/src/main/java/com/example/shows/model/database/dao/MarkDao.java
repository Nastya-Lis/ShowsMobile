package com.example.shows.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.Mark;

import java.util.List;

@Dao
public abstract class MarkDao extends CommonDao<Mark> {

    @Query("SELECT * FROM mark Where performance_id =:id ")
    public abstract Mark getMarkByPerformanceId(Integer id);

    @Query("SELECT * FROM mark Where performance_id =:performanceId And user_id =:userId ")
    public abstract LiveData<Mark> getMarkByPerformanceId(Integer performanceId, Integer userId);

    @Query("SELECT * FROM mark Where performance_id =:performanceId And user_id =:userId ")
    public abstract Mark getMarkByPerformanceAndUserIdNotLiveData(Integer performanceId, Integer userId);

    @Override
    @Query("SELECT * FROM mark")
    public abstract LiveData<List<Mark>> getAll();

    @Override
    @Query("SELECT *FROM mark WHERE id =:id")
    public abstract LiveData<Mark> getById(int id);

    @Query("DELETE FROM mark")
    public abstract void deleteAllMarks();
}
