package com.example.shows.model.database.asyncClasses;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.asyncClasses.constForAsync.ConstVariable;
import com.example.shows.model.database.entity.Performance;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PerformanceAsyncClass extends AsyncTask<Map<String, Context>,LiveData<List<Performance>>,LiveData<List<Performance>>> {

    @Override
    protected LiveData<List<Performance>> doInBackground(Map<String, Context>... maps) {
        Map<String,Context> map = maps[0];
        Optional<String> str = map.keySet().stream().findFirst();
        Optional<Context> context = map.values().stream().findFirst();

        if(str.get() == ConstVariable.INSERT){
            LiveData<List<Performance>> listLiveData  = DatabaseShows.getInstance(context.get()).performanceDao().getAll();
            return listLiveData;
        }
        else
            return null;
    }

    @Override
    protected void onPostExecute(LiveData<List<Performance>> listLiveData) {
        super.onPostExecute(listLiveData);
    }

}
