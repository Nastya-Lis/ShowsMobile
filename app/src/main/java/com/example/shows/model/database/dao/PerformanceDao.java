package com.example.shows.model.database.dao;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.converter.ConverterDateType;

import java.util.List;
import java.util.Optional;

@Dao
@TypeConverters({ConverterDateType.class})
public abstract class PerformanceDao extends CommonDao<Performance>{

    @Override
    @Query("SELECT * FROM performance")
    public abstract LiveData<List<Performance>> getAll();

    @Query("Select * from performance limit 1")
    public abstract LiveData<Performance> getFirstPerformance();

    @Override
    @Query("Select * from performance Where id=:id")
    public abstract LiveData<Performance> getById(int id);

/*    @Query("Select * from performance Where id=:id")
    public abstract MutableLiveData<Performance> getByIdMutable(int id);*/

    @Query("Select * from performance Where id=:id")
    public abstract Optional<Performance> getByIdVersionTwo(int id);

    @Query("SELECT * FROM performance WHERE genre_id=:genreId")
    public abstract LiveData<List<Performance>> getByGenre(int genreId);


}
