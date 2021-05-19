package com.example.shows.model.network.api;

import com.example.shows.model.network.dto.ActorDto;
import com.example.shows.model.network.dto.GenersDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GenreApi {
    @GET("/genres/all")
    Call<List<GenersDto>> getAllGenres();
}
