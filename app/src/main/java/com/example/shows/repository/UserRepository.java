package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.shows.model.database.asyncClasses.OperationAsyncClass;
import com.example.shows.model.database.dao.ScenaristDao;
import com.example.shows.model.database.dao.UserDao;
import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.database.entity.ScenaristPerformance;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.layerAboveNetwork.mapping.ScenaristMapping;
import com.example.shows.model.layerAboveNetwork.mapping.UserMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.ScenaristApi;
import com.example.shows.model.network.api.UserApi;
import com.example.shows.model.network.dto.ScenaristDto;
import com.example.shows.model.network.dto.UserDto;

import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.function.Consumer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository extends CommonRepository<User>{
    UserDao userDao;
    LiveData<User> userLiveData;

    public UserRepository(Context context) {
        super(context);
        userDao = database.userDao();
    }

    public LiveData<User> getFirstUser(){
        userLiveData = userDao.getFirstUser();
        return userLiveData;
    }



    public LiveData<User> getUserById(Integer id){
        userLiveData = userDao.getById(id);
        return userLiveData;
    }

    public void login(String email, String password, Context context){
        NetworkSmth networkSmth = new NetworkSmth();
        UserApi userApi = networkSmth.userApi();

        userApi.login(email,password).enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                UserMapping mapper = Mappers.getMapper(UserMapping.class);
                User user = mapper.dtoToEntity(response.body());

                UserRepository userRepository = new UserRepository(context);
                BookingRepository bookingRepository = new BookingRepository(context);

                userRepository.insert(user,null);
            }
            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Log.d("User_login",t.getMessage());
            }
        });

    }

    public void registration(UserDto userDto,MutableLiveData<Boolean> isSuccess){
        NetworkSmth networkSmth = new NetworkSmth();
        UserApi userApi = networkSmth.userApi();

        userApi.registration(userDto).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    isSuccess.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void insert(User item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<UserDao, User>
                (userDao,onError,UserDao::insert).execute(item);
        return;
    }

    @Override
    public void update(User item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<UserDao, User>
                (userDao,onError,UserDao::update).execute(item);
        return;
    }

    @Override
    public void delete(User item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<UserDao, User>
                (userDao,onError,UserDao::delete).execute(item);
        return;
    }
}
