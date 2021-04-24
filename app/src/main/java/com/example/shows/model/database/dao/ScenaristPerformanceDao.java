package com.example.shows.model.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shows.model.database.entity.ActorPerformance;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.ScenaristPerformance;

import java.util.List;

@Dao
public abstract class ScenaristPerformanceDao {
    @Insert
    public abstract void insert(ScenaristPerformance scenaristPerformance);

    @Update
    public abstract void update(ScenaristPerformance scenaristPerformance);

    @Delete
    public abstract void delete(ScenaristPerformance scenaristPerformance);

    @Query("SELECT * FROM performance Where id In (SELECT performance_id FROM scenarist_performance Where scenarist_id =:scenaristId)")
    public abstract LiveData<List<Performance>> getPerformancesByScenarist(int scenaristId);
}
