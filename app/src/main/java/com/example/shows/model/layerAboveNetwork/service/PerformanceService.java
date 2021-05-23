package com.example.shows.model.layerAboveNetwork.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.shows.R;
import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.asyncClasses.PerformanceAsyncClass;
import com.example.shows.model.database.asyncClasses.constForAsync.ConstVariable;
import com.example.shows.model.database.dao.PerformanceDao;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.layerAboveNetwork.mapping.PerformanceMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.PerformanceApi;
import com.example.shows.model.network.dto.PerformanceDto;
import com.example.shows.repository.PerformanceRepository;
import com.google.common.collect.Multiset;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.security.KeyStore;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//не используется

public class PerformanceService {
    private final DatabaseShows databaseShows;
    private final PerformanceApi performanceApi = NetworkSmth.getInstance().performanceApi();
    Context context;

    public PerformanceService(Context context) {
        databaseShows = DatabaseShows.getInstance(context);
        this.context = context;
    }

    public void getAllPerformancesFromApi(){

      // List<PerformanceDto> performanceList = performanceApi.getAllPerformances().execute().body();

        //PerformanceMapping mapper = Mappers.getMapper(PerformanceMapping.class);
        //List<Performance> performances = new ArrayList<>();

       performanceApi.getAllPerformances().enqueue(new Callback<List<PerformanceDto>>() {

            @Override
            public void onResponse(Call<List<PerformanceDto>> call, Response<List<PerformanceDto>> response) {

                    PerformanceMapping mapper = Mappers.getMapper(PerformanceMapping.class);
                    List<Performance> performances = mapper.dtoesToEntities(response.body());
                    if(response.body().isEmpty()){
                        Log.d("Padla pustaiaa","yeeep");
                    }
                    Log.d("apiPerform fuccc","yeeep");



                  //  insertPerformAsyncTask .execute(performances);
                 //   PerformanceRepository repository = new PerformanceRepository(context);

                   /* AsyncTask.execute(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Optional<Performance> opt = repository.checkIsExistFirstPerform();
                                    if(opt != null){
                                        repository.deleteAllPerformance();
                                    }
                                    for (Performance performance:performances) {
                                        repository.insert(performance,null);
                                    }
                                }
                            }
                    );*/


               // Performance performance = mapper.dtoToEntity(response.body().get(0));
//                Log.d("performance","wooot:"
//                        + performance.getDescription() + performance.getId());

//                 if(performances.size()!=0){
//                    for (Performance performance: performances) {
//                       Log.d(String.valueOf("pipi"),"performances:"
//                                + performance.getName()+ " " + performance.getAmountTickets() + " "
//                                +performance.getId());
//                    }
//                }
             //    addToDb(performances);

            }

            @Override
            public void onFailure(Call<List<PerformanceDto>> call, Throwable t) {
                Log.d("apiPerform suckk","Something is going wrong"+t.getMessage() +t.getCause());
            }
        });



    }


    private static class insertPerformAsyncTask extends AsyncTask<List<Performance>, Void, Void> {

        private PerformanceDao mAsyncTaskDao;

        insertPerformAsyncTask(PerformanceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Performance>... params) {
            List<Performance> performanceList = params[0];
            for (Performance performance: performanceList) {
                mAsyncTaskDao.insert(performance);
            }
            //mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    void addToDb(List<Performance> performanceList){

        Performance performance = new Performance();
        performance.setAmountTickets(666);
        performance.setDescription("lalalalala papapapap");
        performance.setName("hehe");
        performance.setDuration("66");
        performance.setGenreId(1);
        performance.setPrice(43);
        performance.setRating(94.2);
        performance.setDate(Timestamp.valueOf("2015-07-24 09:45:44"));

        Performance performance2 = new Performance();
        performance2.setId(6);
        performance2.setAmountTickets(364);
        performance2.setDescription("hobba bobba");
        performance2.setName("kill me please");
        performance2.setDuration("254");
        performance2.setGenreId(1);
        performance2.setPrice(98);
        performance2.setRating(23.5);
        performance2.setDate(Timestamp.valueOf("2015-07-24 09:45:44"));

        List<Performance> perrr = new ArrayList<>();
        perrr.add(performance);
        perrr.add(performance2);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                for (Performance performance: perrr) {
                    if(databaseShows.performanceDao().getByIdVersionTwo(performance.getId()) == null) {
                        databaseShows.performanceDao().insert(performance);
                    }
                }
              /*  databaseShows.performanceDao().insert(performance);
                databaseShows.performanceDao().insert(performance2);*/
            }
        });
    }

    public LiveData<List<Performance>> getPerformancesFromDb(){
   /*     getAllPerformancesFromApi();
        LiveData<List<Performance>> returning;
        Map<String,Context> map = new HashMap<String,Context>();
        map.put(ConstVariable.INSERT,context);


        PerformanceAsyncClass performanceAsyncClass = new PerformanceAsyncClass();
        performanceAsyncClass.execute(map);
      //  LiveData<List<Performance>> returningValue = performanceAsyncClass.get();
   *//*     new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        returning = databaseShows.performanceDao().getAll();
                    }
                }
        );*//*

       return  returningValue;*/
        return null;

    }
}
