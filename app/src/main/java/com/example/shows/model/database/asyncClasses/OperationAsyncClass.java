package com.example.shows.model.database.asyncClasses;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import androidx.room.Entity;

import com.example.shows.model.database.dao.CommonDao;
import com.example.shows.model.database.entity.CommonEntity;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class OperationAsyncClass<D extends CommonDao<E>, E extends CommonEntity> extends AsyncTask<E, Void, SQLiteException> {

    private D dao;
    private Consumer<SQLiteException> onError;
    private BiConsumer<D, E> doInBackground;

    public OperationAsyncClass(D dao, Consumer<SQLiteException> onError, BiConsumer<D, E> doInBackground) {
        this.dao = dao;
        this.onError = onError;
        this.doInBackground = doInBackground;
    }

    @SafeVarargs
    @Override
    protected final SQLiteException doInBackground(E... items) {
        try {
            if(items[0]!=null)
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
