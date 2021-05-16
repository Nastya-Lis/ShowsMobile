package com.example.shows.model.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.shows.model.database.entity.Role;

@Dao
public abstract class RoleDao extends CommonDao<Role> {

    @Query("SELECT * FROM ROLE WHERE name =:name")
    abstract Role findFirstByName(String name);

}
