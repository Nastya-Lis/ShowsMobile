package com.example.shows.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.Performance;

import java.util.List;

@Dao
public abstract class BookingDao extends CommonDao<Booking>{

    @Override
    @Query("Select * from booking")
    public abstract LiveData<List<Booking>> getAll();

    @Override
    @Query("SELECT * FROM booking WHERE id=:id")
    public abstract LiveData<Booking> getById(int id);

    @Query("SELECT * FROM booking WHERE user_id =:userId")
    public abstract LiveData<List<Booking>> getBookingByUser(Integer userId);

    @Query("SELECT * FROM booking WHERE user_id =:userId and performance_id =:performId")
    public abstract LiveData<List<Booking>> getBookingByUserAndPerform(Integer userId, Integer performId);

//    @Transaction
//    public LiveData<List<Booking>> getBookingByUser(User user){
//        return getBookingByUser(user.getId());
//    }
}
