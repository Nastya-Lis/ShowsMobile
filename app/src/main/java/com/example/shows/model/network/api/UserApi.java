package com.example.shows.model.network.api;

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

public interface UserApi {

    @FormUrlEncoded
    @POST("login")
    Call<UserDto> login(@Field("login") String login,
                        @Field("email") String email,
                        @Field("password") String password);


    @POST("registration")
    Call<ResponseBody> registration(@Body UserDto dto);

    //TODO Delete this method after testing
    @GET("/user/all")
    Call<List<UserDto>> getAllUsers();
}
