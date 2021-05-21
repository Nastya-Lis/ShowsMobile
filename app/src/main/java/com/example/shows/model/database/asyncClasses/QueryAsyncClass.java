package com.example.shows.model.database.asyncClasses;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.util.function.Supplier;

public class QueryAsyncClass<E> extends AsyncTask<Void,Void,E> {
    private final MutableLiveData<E> liveData;
    private final Supplier<E> doInBackground;

    public QueryAsyncClass(MutableLiveData<E> liveData, Supplier<E> doInBackground) {
        this.liveData = liveData;
        this.doInBackground = doInBackground;
    }

    @SafeVarargs
    @Override
    protected final E doInBackground(Void... items)
    {
        return doInBackground.get();
    }

    @Override
    protected void onPostExecute(E e) {
        super.onPostExecute(e);
        liveData.postValue(e);
    }
}
