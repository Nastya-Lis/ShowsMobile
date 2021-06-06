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
public class ActorService {

    private final DatabaseShows databaseShows;
    private final ActorApi actorApi = NetworkSmth.getInstance().actorApi();

    public ActorService(Context context) {
        databaseShows = DatabaseShows.getInstance(context);
    }


    public void getAllActorsFromApi(){
        actorApi.getAllActors().enqueue(new Callback<List<ActorDto>>() {

            @Override
            public void onResponse(Call<List<ActorDto>> call, Response<List<ActorDto>> response) {
                ActorMapping mapper = Mappers.getMapper(ActorMapping.class);
                List<Actor> actors = mapper.dtoesToEntities(response.body());
                if(response.body().isEmpty()){
                    Log.d("actorservice","success");
                }
            }

            @Override
            public void onFailure(Call<List<ActorDto>> call, Throwable t) {
                Log.d("actorservice","Something is going wrong "+t.getMessage() +t.getCause());
            }
        });
    }

}
