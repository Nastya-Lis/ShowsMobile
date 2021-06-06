package com.example.shows.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.shows.model.database.entity.Mark;
import com.example.shows.model.database.entity.Role;

import java.util.List;

@Dao
public abstract class RoleDao extends CommonDao<Role> {
    @Query("SELECT * FROM ROLE WHERE name =:name")
    abstract Role findFirstByName(String name);

    @Override
    @Query("SELECT * FROM role")
    public abstract LiveData<List<Role>> getAll();

    @Override
    @Query("SELECT *FROM role WHERE id =:id")
    public abstract LiveData<Role> getById(int id);

}
