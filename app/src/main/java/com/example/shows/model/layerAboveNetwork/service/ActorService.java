package com.example.shows.model.layerAboveNetwork.service;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.layerAboveNetwork.mapping.ActorMapping;
import com.example.shows.model.layerAboveNetwork.mapping.PerformanceMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.ActorApi;
import com.example.shows.model.network.api.PerformanceApi;
import com.example.shows.model.network.dto.ActorDto;
import com.example.shows.model.network.dto.PerformanceDto;

import org.mapstruct.factory.Mappers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//не используется

public class ActorService {

    private final DatabaseShows databaseShows;
    private final ActorApi actorApi = NetworkSmth.getInstance().actorApi();

    public ActorService(Context context) {
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

    public void getAllActorsFromApi(){
        actorApi.getAllActors().enqueue(new Callback<List<ActorDto>>() {

            @Override
            public void onResponse(Call<List<ActorDto>> call, Response<List<ActorDto>> response) {
                ActorMapping mapper = Mappers.getMapper(ActorMapping.class);
                List<Actor> actors = mapper.dtoesToEntities(response.body());
                if(response.body().isEmpty()){
                    Log.d("Padla pustaiaa","yeeep");
                }
                Log.d("apiPerform fuccc","yeeep");
                //   addToDb(performances);

                if(actors.size()!=0){
                    for (Actor actor: actors) {

                        Log.d(String.valueOf("iii"),"actors:"
                                + actor.getName()+ " " +actor.getId());

                    }
                }

            }

            @Override
            public void onFailure(Call<List<ActorDto>> call, Throwable t) {
                Log.d("apiPerform suckk","Something is going wrong"+t.getMessage() +t.getCause());
            }
        });
    }

    void addToDb(List<Actor> actorList){
        for (Actor actor: actorList) {
            databaseShows.actorDao().insert(actor);
        }
    }

    public LiveData<List<Actor>> getPerformancesFromDb(){
        getAllActorsFromApi();
        return databaseShows.actorDao().getAll();
    }

}
