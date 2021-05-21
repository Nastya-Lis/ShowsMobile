package com.example.shows.model.layerAboveNetwork.service;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.layerAboveNetwork.mapping.ActorMapping;
import com.example.shows.model.layerAboveNetwork.mapping.ScenaristMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.ActorApi;
import com.example.shows.model.network.api.ScenaristApi;
import com.example.shows.model.network.dto.ActorDto;
import com.example.shows.model.network.dto.ScenaristDto;

import org.mapstruct.factory.Mappers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScenaristService {
    private final DatabaseShows databaseShows;
    private final ScenaristApi scenaristApi = NetworkSmth.getInstance().scenaristApi();

    public ScenaristService(Context context) {
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

    public void getAllScenaristsFromApi(){
        scenaristApi.getAllScenarists().enqueue(new Callback<List<ScenaristDto>>() {

            @Override
            public void onResponse(Call<List<ScenaristDto>> call, Response<List<ScenaristDto>> response) {
                ScenaristMapping mapper = Mappers.getMapper(ScenaristMapping.class);
                List<Scenarist> scenarists = mapper.dtoesToEntities(response.body());
                if(response.body().isEmpty()){
                    Log.d("Padla pustaiaa","yeeep");
                }
                Log.d("apiPerform fuccc","yeeep");
                //   addToDb(performances);

                if(scenarists.size()!=0){
                    for (Scenarist scenarist: scenarists) {

                        Log.d(String.valueOf("sss"),"scenarist:"
                                + scenarist.getName()+ " " +scenarist.getId());

                    }
                }

            }

            @Override
            public void onFailure(Call<List<ScenaristDto>> call, Throwable t) {
                Log.d("apiPerform suckk","Something is going wrong"+t.getMessage() +t.getCause());
            }
        });
    }

    void addToDb(List<Scenarist> scenaristList){
        for (Scenarist scenarist: scenaristList) {
            databaseShows.scenaristDao().insert(scenarist);
        }
    }

    public LiveData<List<Scenarist>> getScenaristsFromDb(){
        getAllScenaristsFromApi();
        return databaseShows.scenaristDao().getAll();
    }

}
