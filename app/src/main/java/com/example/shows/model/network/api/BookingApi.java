package com.example.shows.model.network.api;

import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.network.dto.BookingDto;

import kotlin.ParameterName;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface BookingApi {
    @POST("/bookings/add")
    Call<String> pushOnServer(@Query("amount") Integer amount, @Query("userId") Integer userId, @Query("performanceId") Integer performanceId);
}
