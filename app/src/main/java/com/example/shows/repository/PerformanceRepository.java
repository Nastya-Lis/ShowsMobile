package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.graphics.Path;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.shows.model.database.asyncClasses.OperationAsyncClass;
import com.example.shows.model.database.asyncClasses.PerformanceAsyncClass;
import com.example.shows.model.database.asyncClasses.QueryAsyncClass;
import com.example.shows.model.database.asyncClasses.QueryForCollectionAsyncClass;
import com.example.shows.model.database.dao.ActorDao;

import com.example.shows.model.database.dao.ActorDao_Impl;
import com.example.shows.model.database.dao.ActorPerformanceDao;


import com.example.shows.model.database.dao.ActorPerformanceDao_Impl;
import com.example.shows.model.database.dao.MarkDao;
import com.example.shows.model.database.dao.PerformanceDao;

import com.example.shows.model.database.dao.PerformanceDao_Impl;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.layerAboveNetwork.mapping.PerformanceMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.PerformanceApi;
import com.example.shows.model.network.dto.PerformanceDto;

import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerformanceRepository extends CommonRepository<Performance>{

    private PerformanceDao performanceDao;
    private ActorDao actorDao;
    private ActorPerformanceDao actorPerformanceDao;

    private LiveData<Actor> actorLiveData;

    private Optional<Performance> optionalPerformance;

    private LiveData<List<Performance>> performancesList;
    private LiveData<Performance> performance;
    private MutableLiveData<Performance> performanceMutable;
    private int currentId = 0;
    private boolean isLastDefault = true;


    //спектакли по имени актера
    public LiveData<List<Performance>> getPerformancesByActorId(Integer idActor){
        performancesList = actorPerformanceDao.getPerformancesByActor(idActor);
        return performancesList;
    }

    public PerformanceRepository(Context context) {
        super(context);
       performanceDao = new PerformanceDao_Impl(database);
       actorDao = new ActorDao_Impl(database);
        actorPerformanceDao = new ActorPerformanceDao_Impl(database);
        performancesList = new MutableLiveData<>();
        actorLiveData = new MutableLiveData<>();
        performance = new MutableLiveData<>();
    }

    public Optional<Performance> checkIsExistFirstPerform(){
        optionalPerformance= performanceDao.checkIsExistPerformFirst();
        return optionalPerformance;
    }

    public LiveData<Performance> getPerformanceFirst(){
        performance = performanceDao.getFirstPerformance();
        return performance;
    }

    public void deleteAllPerformance(){
        performanceDao.deleteAllPerformances();
    }

    public LiveData<List<Performance>> getAllPerformances(){
        performancesList = performanceDao.getAll();
        return performancesList;
    }

    public LiveData<Performance> getPerformanceById(Integer id){
        performance = performanceDao.getById(id);
        return performance;
    }


    public void getAllPerformancesFromApi(Context context){

        NetworkSmth networkSmth = new NetworkSmth();
        PerformanceApi performanceApi = networkSmth.performanceApi();
        performanceApi.getAllPerformances().enqueue(new Callback<List<PerformanceDto>>() {

            @Override
            public void onResponse(Call<List<PerformanceDto>> call, Response<List<PerformanceDto>> response) {

                PerformanceMapping mapper = Mappers.getMapper(PerformanceMapping.class);
                List<Performance> performances = mapper.dtoesToEntities(response.body());

                PerformanceRepository repository = new PerformanceRepository(context);

                for (Performance performance: performances) {
                    performance.setGenreId(performance.getGenre().getId());
                    repository.insert(performance,null);
                }

            }

            @Override
            public void onFailure(Call<List<PerformanceDto>> call, Throwable t) {
                Log.d("performance repository","Something is going wrong "+t.getMessage() +t.getCause());
            }
        });


    }


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
