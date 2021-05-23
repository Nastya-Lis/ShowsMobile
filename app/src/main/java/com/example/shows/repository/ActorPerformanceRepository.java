package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.asyncClasses.ActorPerformanceAsyncClass;
import com.example.shows.model.database.asyncClasses.OperationAsyncClass;
import com.example.shows.model.database.dao.ActorDao;
import com.example.shows.model.database.dao.ActorPerformanceDao;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.ActorPerformance;

import java.util.function.Consumer;

public class ActorPerformanceRepository {

    DatabaseShows database;
    ActorPerformanceDao actorPerformanceDao;

    public ActorPerformanceRepository(Context context) {
        database = DatabaseShows.getInstance(context);
        actorPerformanceDao = database.actorPerformanceDao();
    }

    public void insert(ActorPerformance item, Consumer<SQLiteException> onError) {
        new ActorPerformanceAsyncClass(actorPerformanceDao,onError,ActorPerformanceDao::insert).execute(item);
        return;
    }

    public void update(ActorPerformance item, Consumer<SQLiteException> onError) {
        new ActorPerformanceAsyncClass(actorPerformanceDao,onError,ActorPerformanceDao::update).execute(item);
        return;
    }

    public void delete(ActorPerformance item, Consumer<SQLiteException> onError) {
        new ActorPerformanceAsyncClass(actorPerformanceDao,onError,ActorPerformanceDao::delete).execute(item);
        return;
    }
}
