package com.example.shows.model.database.asyncClasses;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import com.example.shows.model.database.dao.ActorDao;
import com.example.shows.model.database.dao.ActorPerformanceDao;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.ActorPerformance;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ActorForSearch extends AsyncTask<String,Void, Actor> {

    private ActorDao dao;

    public ActorForSearch(ActorDao dao) {
        this.dao = dao;
    }

    @Override
    protected Actor doInBackground(String... strings) {
        String name = strings[0];
        Actor actor = dao.findActorByName(name);
        return actor;
    }
}
