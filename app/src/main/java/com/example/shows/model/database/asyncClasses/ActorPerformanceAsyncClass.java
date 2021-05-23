package com.example.shows.model.database.asyncClasses;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import com.example.shows.model.database.dao.ActorPerformanceDao;
import com.example.shows.model.database.entity.ActorPerformance;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ActorPerformanceAsyncClass extends AsyncTask<ActorPerformance, Void, SQLiteException> {

    private ActorPerformanceDao dao;
    private Consumer<SQLiteException> onError;
    private BiConsumer<ActorPerformanceDao, ActorPerformance> doInBackground;

    public ActorPerformanceAsyncClass(ActorPerformanceDao dao, Consumer<SQLiteException> onError, BiConsumer<ActorPerformanceDao, ActorPerformance> doInBackground) {
        this.dao = dao;
        this.onError = onError;
        this.doInBackground = doInBackground;
    }

    @SafeVarargs
    @Override
    protected final SQLiteException doInBackground(ActorPerformance... items) {
        try {
            doInBackground.accept(dao, items[0]);
            return null;
        } catch (SQLiteException e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(SQLiteException e) {
        super.onPostExecute(e);
        if (onError != null && e != null) {
            onError.accept(e);
        }
    }

}
