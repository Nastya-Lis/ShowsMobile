package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;

import com.example.shows.model.database.dao.UserDao;
import com.example.shows.model.database.entity.User;

import java.util.function.Consumer;

public class UserRepository extends CommonRepository<User>{
    UserDao userDao;

    LiveData<User> userLiveData;

    public UserRepository(Context context) {
        super(context);
        userDao = database.userDao();
    }

    public LiveData<User> getUserById(Integer id){
        userLiveData = userDao.getById(id);
        return userLiveData;
    }

    @Override
    public void insert(User item, Consumer<SQLiteException> onError) {

    }

    @Override
    public void update(User item, Consumer<SQLiteException> onError) {

    }

    @Override
    public void delete(User item, Consumer<SQLiteException> onError) {

    }
}
