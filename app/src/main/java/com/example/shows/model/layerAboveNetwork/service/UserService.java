package com.example.shows.model.layerAboveNetwork.service;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.layerAboveNetwork.mapping.ActorMapping;
import com.example.shows.model.layerAboveNetwork.mapping.UserMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.ActorApi;
import com.example.shows.model.network.api.UserApi;
import com.example.shows.model.network.dto.ActorDto;
import com.example.shows.model.network.dto.UserDto;

import org.mapstruct.factory.Mappers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    private final DatabaseShows databaseShows;
    private final UserApi userApi = NetworkSmth.getInstance().userApi();

    public UserService(Context context) {
        databaseShows = DatabaseShows.getInstance(context);
    }


  /*  public void takeDataFromApi(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // should be here
            }
        });
    }*/

    public void getAllUsersFromApi(){
        userApi.getAllUsers().enqueue(new Callback<List<UserDto>>() {

            @Override
            public void onResponse(Call<List<UserDto>> call, Response<List<UserDto>> response) {
                UserMapping mapper = Mappers.getMapper(UserMapping.class);
                List<User> users = mapper.dtoesToEntities(response.body());
                if(response.body().isEmpty()){
                    Log.d("Padla pustaiaa","yeeep");
                }
                Log.d("apiPerform fuccc","yeeep");
                //   addToDb(performances);

                if(users.size()!=0){
                    for (User user: users) {

                        Log.d(String.valueOf("iii"),"actors:"
                                + user.getRole()+ " " +user.getId() + " " + user.getEmail());

                    }
                }

            }

            @Override
            public void onFailure(Call<List<UserDto>> call, Throwable t) {
                Log.d("apiPerform suckk","Something is going wrong"+t.getMessage() +t.getCause());
            }
        });
    }

    void addToDb(List<User> usersList){
        for (User user: usersList) {
            databaseShows.userDao().insert(user);
        }
    }

    public LiveData<List<User>> getUsersFromDb(){
        getAllUsersFromApi();
        return databaseShows.userDao().getAll();
    }

}
