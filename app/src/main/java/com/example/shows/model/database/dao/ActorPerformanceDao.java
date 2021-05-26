package com.example.shows.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.ActorPerformance;
import com.example.shows.model.database.entity.Performance;

import java.util.List;

@Dao
public abstract class ActorPerformanceDao{
    @Insert
    public abstract void insert(ActorPerformance actorPerformance);

    @Update
    public abstract void update(ActorPerformance actorPerformance);

    @Delete
    public abstract void delete(ActorPerformance actorPerformance);

    @Query("SELECT * FROM actor Where id In (SELECT actor_id FROM actor_performance Where performance_id =:performanceId)")
    public abstract LiveData<List<Actor>> getActorsByPerformance(int performanceId);

    @Query("SELECT * FROM performance Where id In (SELECT performance_id FROM actor_performance Where actor_id =:actorId)")
    public abstract LiveData<List<Performance>> getPerformancesByActor(int actorId);


    @Query("SELECT * FROM performance Where id In (SELECT performance_id FROM actor_performance Where actor_id =:actorId)")
    public abstract List<Performance> getPerformancesByActorNotLiveData(int actorId);

}
