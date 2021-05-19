package com.example.shows.model.layerAboveNetwork.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.layerAboveNetwork.mapping.PerformanceMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.PerformanceApi;
import com.example.shows.model.network.dto.PerformanceDto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerformanceService {
    private final DatabaseShows databaseShows;
    private final PerformanceApi performanceApi = NetworkSmth.getInstance().performanceApi();

    public PerformanceService(Context context) {
        databaseShows = DatabaseShows.getInstance(context);
    }


  /*  public void takeDataFromApi(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // should be here
            }
        });
    }*/

    public void getAllPerformancesFromApi(){
        performanceApi.getAllPerformances().enqueue(new Callback<List<PerformanceDto>>() {

            @Override
            public void onResponse(Call<List<PerformanceDto>> call, Response<List<PerformanceDto>> response) {

                    //List<PerformanceDto> performanceDtos = response.body().stream().findFirst();
                    PerformanceMapping mapper = Mappers.getMapper(PerformanceMapping.class);
                    List<Performance> performances = mapper.dtoesToEntities(response.body());
                    if(response.body().isEmpty()){
                        Log.d("Padla pustaiaa","yeeep");
                    }
                    Log.d("apiPerform fuccc","yeeep");
                 //   addToDb(performances);

               // Performance performance = mapper.dtoToEntity(response.body().get(0));
               /* Log.d("performance","wooot:"
                        + performance.getDescription() + performance.getId());*/

                 if(performances.size()!=0){
                    for (Performance performance: performances) {

                        Log.d(String.valueOf("pipi"),"performances:"
                                + performance.getName()+ " " + performance.getAmountTickets() + " "
                                +performance.getId());

                    }
                }

            }

            @Override
            public void onFailure(Call<List<PerformanceDto>> call, Throwable t) {
                Log.d("apiPerform suckk","Something is going wrong"+t.getMessage() +t.getCause());
            }
        });
    }

    void addToDb(List<Performance> performanceList){
        for (Performance performance: performanceList) {
            databaseShows.performanceDao().insert(performance);
        }
    }

    public List<Performance> getPerformancesFromDb(){
        getAllPerformancesFromApi();
        return databaseShows.performanceDao().getAll();
    }

}
