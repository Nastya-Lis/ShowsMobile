package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;

import com.example.shows.model.database.dao.ActorDao;
import com.example.shows.model.database.dao.ActorPerformanceDao;
import com.example.shows.model.database.entity.Actor;


import java.util.List;
import java.util.function.Consumer;

public class ActorRepository extends CommonRepository<Actor>{

    ActorDao actorDao;
    ActorPerformanceDao actorPerformanceDao;
    private  LiveData<List<Actor>> actorsByPerformanceId;

    private LiveData<List<Actor>> actorList;
    private LiveData<Actor> actorLiveData;

    public ActorRepository(Context context) {
        super(context);
        actorDao = database.actorDao();
        actorPerformanceDao = database.actorPerformanceDao();
    }


    public LiveData<List<Actor>> getActorsByPerformanceId(Integer idPerform){
        actorsByPerformanceId = actorPerformanceDao.getActorsByPerformance(idPerform);
        return actorsByPerformanceId;
    }



    @Override
    public void insert(Actor item, Consumer<SQLiteException> onError) {

    }

    @Override
    public void update(Actor item, Consumer<SQLiteException> onError) {

    }

    @Override
    public void delete(Actor item, Consumer<SQLiteException> onError) {

    }
}
