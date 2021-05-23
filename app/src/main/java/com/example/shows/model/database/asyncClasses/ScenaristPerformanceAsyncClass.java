package com.example.shows.model.database.asyncClasses;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import com.example.shows.model.database.dao.ActorPerformanceDao;
import com.example.shows.model.database.dao.ScenaristPerformanceDao;
import com.example.shows.model.database.entity.ActorPerformance;
import com.example.shows.model.database.entity.ScenaristPerformance;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ScenaristPerformanceAsyncClass extends AsyncTask<ScenaristPerformance,Void,SQLiteException> {

    private ScenaristPerformanceDao dao;
    private Consumer<SQLiteException> onError;
    private BiConsumer<ScenaristPerformanceDao, ScenaristPerformance> doInBackground;

    public ScenaristPerformanceAsyncClass(ScenaristPerformanceDao dao, Consumer<SQLiteException> onError, BiConsumer<ScenaristPerformanceDao, ScenaristPerformance> doInBackground) {
        this.dao = dao;
        this.onError = onError;
        this.doInBackground = doInBackground;
    }

    @SafeVarargs
    @Override
    protected final SQLiteException doInBackground(ScenaristPerformance... items) {
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
