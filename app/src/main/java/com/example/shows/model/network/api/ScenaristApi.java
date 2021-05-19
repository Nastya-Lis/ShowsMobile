package com.example.shows.model.network.api;

import com.example.shows.model.network.dto.ScenaristDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ScenaristApi {
    @GET("/scenarists/all")
    Call<List<ScenaristDto>> getAllScenarists();
}
