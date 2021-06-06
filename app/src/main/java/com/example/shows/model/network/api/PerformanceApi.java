package com.example.shows.model.network.api;

import com.example.shows.model.network.dto.PerformanceDto;

import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PerformanceApi {

    @GET("/performances/all")
    Call<List<PerformanceDto>> getAllPerformances();

    @POST("/performances/rating")
    Call<String> setLike(@Query("mark") Boolean like, @Query("idPerform") Integer idPerformance);
}
