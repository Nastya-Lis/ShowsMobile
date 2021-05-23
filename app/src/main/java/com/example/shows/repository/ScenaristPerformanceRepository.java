package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.asyncClasses.ActorPerformanceAsyncClass;
import com.example.shows.model.database.asyncClasses.ScenaristPerformanceAsyncClass;
import com.example.shows.model.database.dao.ActorPerformanceDao;
import com.example.shows.model.database.dao.ScenaristPerformanceDao;
import com.example.shows.model.database.entity.ActorPerformance;
import com.example.shows.model.database.entity.ScenaristPerformance;

import java.util.function.Consumer;

public class ScenaristPerformanceRepository {
    DatabaseShows database;
    ScenaristPerformanceDao scenaristPerformanceDao;

    public ScenaristPerformanceRepository(Context context) {
        database = DatabaseShows.getInstance(context);
        scenaristPerformanceDao = database.scenaristPerformanceDao();
    }

    public void insert(ScenaristPerformance item, Consumer<SQLiteException> onError) {
        new ScenaristPerformanceAsyncClass(scenaristPerformanceDao,onError,ScenaristPerformanceDao::insert).execute(item);
        return;
    }

    public void update(ScenaristPerformance item, Consumer<SQLiteException> onError) {
        new ScenaristPerformanceAsyncClass(scenaristPerformanceDao,onError,ScenaristPerformanceDao::insert).execute(item);
        return;
    }

    public void delete(ScenaristPerformance item, Consumer<SQLiteException> onError) {
        new ScenaristPerformanceAsyncClass(scenaristPerformanceDao,onError,ScenaristPerformanceDao::insert).execute(item);
        return;
    }
}
