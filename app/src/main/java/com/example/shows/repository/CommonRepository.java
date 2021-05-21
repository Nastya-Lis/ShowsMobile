package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.example.shows.model.database.DatabaseShows;

import java.util.function.Consumer;

public abstract class CommonRepository<E> {
    protected DatabaseShows database;

    public CommonRepository(Context context) {
        database = DatabaseShows.getInstance(context);
    }

    public abstract void insert(E item, Consumer<SQLiteException> onError);
    public abstract void update(E item, Consumer<SQLiteException> onError);
    public abstract void delete(E item, Consumer<SQLiteException> onError);

}
