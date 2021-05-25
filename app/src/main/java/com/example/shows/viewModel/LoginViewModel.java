package com.example.shows.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shows.model.database.entity.User;
import com.example.shows.repository.UserRepository;

public class LoginViewModel extends AbstractCrudViewModel<User,UserRepository> {

    LiveData<User> userLiveData;
    UserRepository userRepository;

    boolean isSuccessAdding = false;

//    public MutableLiveData<String> username = new MutableLiveData<String>("");
//    public MutableLiveData<String> password = new MutableLiveData<String>("");

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void loginFunc(String email, String password){
        userRepository.login(email,password,getApplication());
    }

    public LiveData<User> getCurrentUser(){
        userLiveData = userRepository.getFirstUser();
        return userLiveData;
    }
}
