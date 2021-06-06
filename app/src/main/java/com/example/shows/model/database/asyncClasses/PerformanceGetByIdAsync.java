package com.example.shows.model.database.asyncClasses;

import android.os.AsyncTask;

import com.example.shows.model.database.dao.PerformanceDao;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.User;


//пожалуй, следует это удалить
public class PerformanceGetByIdAsync extends AsyncTask<Integer,Void, Performance> {
    PerformanceDao dao;

    public PerformanceGetByIdAsync(PerformanceDao dao)
    {
        this.dao = dao;
    }


    @Override
    protected Performance doInBackground(Integer... integers) {
        Performance performance = dao.getByIdWithoutLiveData(integers[0]);

        return performance;
    }
}
