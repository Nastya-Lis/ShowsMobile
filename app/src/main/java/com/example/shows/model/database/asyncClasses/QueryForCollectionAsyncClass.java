package com.example.shows.model.database.asyncClasses;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.shows.model.database.dao.CommonDao;
import com.example.shows.model.database.entity.CommonEntity;

import java.util.List;
import java.util.function.Supplier;

public class QueryForCollectionAsyncClass<E extends CommonEntity,D extends CommonDao> extends AsyncTask<Void,Void, List<E>> {
    private final MutableLiveData<List<E>> liveData;
    private final Supplier<List<E>> doInBackGround;

    public QueryForCollectionAsyncClass(MutableLiveData<List<E>> liveData, Supplier<List<E>> doInBackground) {
        this.liveData = liveData;
        this.doInBackGround = doInBackground;
    }

    @Override
    protected List<E> doInBackground(Void... voids) {
        return doInBackGround.get();
    }

    @Override
    protected void onPostExecute(List<E> e) {
        super.onPostExecute(e);
        liveData.postValue(e);
    }
}
