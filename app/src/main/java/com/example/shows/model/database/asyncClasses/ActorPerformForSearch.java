package com.example.shows.model.database.asyncClasses;

import android.os.AsyncTask;

import com.example.shows.model.database.dao.ActorDao;
import com.example.shows.model.database.dao.ActorPerformanceDao;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.ActorPerformance;
import com.example.shows.model.database.entity.Performance;

import java.util.ArrayList;
import java.util.List;

public class ActorPerformForSearch extends AsyncTask<Integer,Void, List<Performance>> {

    private ActorPerformanceDao dao;

    public ActorPerformForSearch(ActorPerformanceDao dao) {
        this.dao = dao;
    }

    @Override
    protected List<Performance> doInBackground(Integer... integers) {
        Integer id = integers[0];
        return dao.getPerformancesByActorNotLiveData(id);
    }
}
