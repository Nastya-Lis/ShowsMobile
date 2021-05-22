package com.example.shows.repository;



//managing resources
//from what source(retrofit or db take data)
//when wifi off or on

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shows.model.database.asyncClasses.OperationAsyncClass;
import com.example.shows.model.database.asyncClasses.PerformanceAsyncClass;
import com.example.shows.model.database.asyncClasses.QueryAsyncClass;
import com.example.shows.model.database.asyncClasses.QueryForCollectionAsyncClass;
import com.example.shows.model.database.dao.PerformanceDao;
import com.example.shows.model.database.dao.PerformanceDao_Impl;
import com.example.shows.model.database.entity.Performance;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

//TODO change in dao returning value from usual list to livedata
public class PerformanceRepository extends CommonRepository<Performance>{

    private PerformanceDao performanceDao;

    private LiveData<List<Performance>> performancesList;
    private LiveData<Performance> performance;
    private MutableLiveData<Performance> performanceMutable;
    private int currentId = 0;
    private boolean isLastDefault = true;


    public PerformanceRepository(Context context) {
        super(context);
        performanceDao = new PerformanceDao_Impl(database);
        performancesList = new MutableLiveData<>();
        performance = new MutableLiveData<>();
    }


    public LiveData<Performance> getPerformanceFirst(){
        performance = performanceDao.getFirstPerformance();
        return performance;
    }

    public LiveData<List<Performance>> getAllPerformances(){
     /*   new QueryForCollectionAsyncClass<Performance,PerformanceDao>(performancesList,
                () -> performanceDao.getAll()).execute();*/
        performancesList = performanceDao.getAll();
      //  isLastDefault = false;
        return performancesList;
    }

    public LiveData<Performance> getPerformanceById(Integer id){
     /*   new QueryForCollectionAsyncClass<Performance,PerformanceDao>(performancesList,
                () -> performanceDao.getAll()).execute();*/
        performance = performanceDao.getById(id);
        //  isLastDefault = false;
        return performance;
    }


  /*  public MutableLiveData<Performance> getPerformanceMutableById(Integer id){
        performanceMutable = performanceDao.getByIdMutable(id);
        return performanceMutable;
    }*/

    @Override
    public void insert(Performance item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<PerformanceDao,Performance>
                (performanceDao,onError,PerformanceDao::insert).execute(item);
        return;
    }

    @Override
    public void update(Performance item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<PerformanceDao,Performance>
                (performanceDao,onError, PerformanceDao::update).execute(item);
        return;
    }

    @Override
    public void delete(Performance item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<PerformanceDao,Performance>(
                performanceDao,onError,PerformanceDao::delete).execute(item);
        return;
    }

}
