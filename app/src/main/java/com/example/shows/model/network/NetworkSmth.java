package com.example.shows.model.network;

import com.example.shows.model.network.api.UserApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkSmth {
    private static NetworkSmth mInstance;
    private final String BASE_URL = "http://localhost:8080/";
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


    UserApi userApi(){
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(UserApi.class);
    }



}
