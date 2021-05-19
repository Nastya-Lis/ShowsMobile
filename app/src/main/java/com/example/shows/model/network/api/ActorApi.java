package com.example.shows.model.network.api;

import com.example.shows.model.network.dto.ActorDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ActorApi {
    @GET("/actors/all")
    Call<List<ActorDto>> getAllActors();
}
