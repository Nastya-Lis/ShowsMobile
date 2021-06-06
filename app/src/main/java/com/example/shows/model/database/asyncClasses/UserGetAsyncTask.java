package com.example.shows.model.database.asyncClasses;

import android.os.AsyncTask;

import com.example.shows.model.database.dao.ScenaristDao;
import com.example.shows.model.database.dao.UserDao;
import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.database.entity.User;

public class UserGetAsyncTask extends AsyncTask<Void,Void, User> {
    private UserDao dao;

    public UserGetAsyncTask(UserDao dao) {
        this.dao = dao;
    }

    @Override
    protected User doInBackground(Void... voids) {
        User user = dao.getCurrentAsync();
        return user;
    }

/*    @Override
    protected User doInBackground(... strings) {
        String email = strings[0];
        User user = dao.getByEmail(email);
        return user;
    }*/
}
