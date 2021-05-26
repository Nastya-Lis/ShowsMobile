package com.example.shows.model.database.asyncClasses;

import android.os.AsyncTask;

import com.example.shows.model.database.dao.ActorPerformanceDao;
import com.example.shows.model.database.dao.ScenaristPerformanceDao;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.ScenaristPerformance;

import java.util.List;

public class ScenaristPerformForSearch extends AsyncTask<Integer,Void, List<Performance>> {
    private ScenaristPerformanceDao dao;

    public ScenaristPerformForSearch(ScenaristPerformanceDao dao) {
        this.dao = dao;
    }

    @Override
    protected List<Performance> doInBackground(Integer... integers) {
        Integer id = integers[0];
        return dao.getPerformancesByScenaristNotLiveData(id);
    }
}
