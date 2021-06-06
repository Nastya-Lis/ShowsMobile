package com.example.shows.model.network;

import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.network.api.ActorApi;
import com.example.shows.model.network.api.BookingApi;
import com.example.shows.model.network.api.GenreApi;
import com.example.shows.model.network.api.PerformanceApi;
import com.example.shows.model.network.api.ScenaristApi;
import com.example.shows.model.network.api.UserApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkSmth {
    private static NetworkSmth mInstance;
    private final String BASE_URL = "http://10.0.2.2:8080/";
    private Retrofit retrofit;

    public static NetworkSmth getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkSmth();
        }
        return mInstance;
    }


    public BookingApi bookingApi(){
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(BookingApi.class);
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


    public UserApi userApi(){
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(UserApi.class);
    }

}
