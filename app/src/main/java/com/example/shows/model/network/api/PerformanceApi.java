package com.example.shows.model.network.api;

import com.example.shows.model.network.dto.PerformanceDto;

import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PerformanceApi {

    @GET("/performances/all")
    Call<List<PerformanceDto>> getAllPerformances();

}
