package com.example.shows.model.layerAboveNetwork.service;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.layerAboveNetwork.mapping.ActorMapping;
import com.example.shows.model.layerAboveNetwork.mapping.GenerMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.ActorApi;
import com.example.shows.model.network.api.GenreApi;
import com.example.shows.model.network.dto.ActorDto;
import com.example.shows.model.network.dto.GenersDto;

import org.mapstruct.factory.Mappers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreService {
    private final DatabaseShows databaseShows;
    private final GenreApi genreApi = NetworkSmth.getInstance().genreApi();

    public GenreService(Context context) {
        databaseShows = DatabaseShows.getInstance(context);
    }


    public void getAllGenresFromApi(){
        genreApi.getAllGenres().enqueue(new Callback<List<GenersDto>>() {

            @Override
            public void onResponse(Call<List<GenersDto>> call, Response<List<GenersDto>> response) {
                GenerMapping mapper = Mappers.getMapper(GenerMapping.class);
                List<Geners> geners = mapper.dtoesToEntities(response.body());
                if(response.body().isEmpty()){
                    Log.d("genreservice","success");
                }
            }

            @Override
            public void onFailure(Call<List<GenersDto>> call, Throwable t) {
                Log.d("genereservice","Something is going wrong"+t.getMessage() +t.getCause());
            }
        });
    }

}
