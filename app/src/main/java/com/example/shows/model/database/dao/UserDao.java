package com.example.shows.model.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.shows.model.database.entity.User;

import org.mapstruct.control.MappingControl;

import java.util.List;

@Dao
public abstract class UserDao extends CommonDao<User>{
    @Override
    @Query("SELECT * FROM user")
    public abstract LiveData<List<User>> getAll();

    @Override
    @Query("SELECT * FROM user WHERE id=:id")
    public abstract LiveData<User> getById(int id);

    @Query("SELECT * FROM user WHERE email =:email And password=:password Limit 1")
    public abstract LiveData<User> getByPasswordAndEmail(String email, String password);

}
