package com.example.shows.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.shows.model.database.dao.UserDao;
import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.network.dto.UserDto;
import com.example.shows.repository.UserRepository;

public class SignUpViewModel extends AbstractCrudViewModel<User, UserRepository>{

    //UserRepository userRepository;
    public MutableLiveData<Boolean> isSuccessToServer = new MutableLiveData<>();

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        isSuccessToServer.setValue(false);
    }

    public void registration(UserDto userDto){
        repository.registration(userDto,isSuccessToServer);
    }

}
