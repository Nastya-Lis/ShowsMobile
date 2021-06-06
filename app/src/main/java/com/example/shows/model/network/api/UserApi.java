package com.example.shows.model.network.api;

import android.content.Intent;

import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.network.dto.BookingDto;
import com.example.shows.model.network.dto.GenersDto;
import com.example.shows.model.network.dto.UserDto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    @GET("/login")
    Call<UserDto> login(@Query("username") String email, @Query("password") String password);

    @POST("registration")
    Call<ResponseBody> registration(@Body UserDto dto);

    @GET("user/booking/{userId}")
    Call<List<BookingDto>> getFromApiBookingCurrentUser(@Path("userId") Integer userId);

    //TODO Delete this method after testing
    @GET("/user/all")
    Call<List<UserDto>> getAllUsers();


}
