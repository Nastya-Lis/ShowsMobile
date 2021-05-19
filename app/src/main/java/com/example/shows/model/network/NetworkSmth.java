package com.example.shows.model.network;

import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.network.api.ActorApi;
import com.example.shows.model.network.api.GenreApi;
import com.example.shows.model.network.api.PerformanceApi;
import com.example.shows.model.network.api.ScenaristApi;
import com.example.shows.model.network.api.UserApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkSmth {
    private static NetworkSmth mInstance;
    private final String BASE_URL = "http://10.208.46.43:8080/";
    private Retrofit retrofit;

    public static NetworkSmth getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkSmth();
        }
        return mInstance;
    }


   /* private NetworkSmth(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }*/


    public UserApi userApi(){
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(UserApi.class);
    }

    public PerformanceApi performanceApi(){
        return new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create())
                .build().create(PerformanceApi.class);
    }


    public ActorApi actorApi(){
        return new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create())
                .build().create(ActorApi.class);
    }

    public ScenaristApi scenaristApi(){
        return new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create())
                .build().create(ScenaristApi.class);
    }

    public GenreApi genreApi(){
        return new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create())
                .build().create(GenreApi.class);
    }

}
