package com.example.shows.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shows.model.database.asyncClasses.UserGetAsyncTask;
import com.example.shows.model.database.entity.User;
import com.example.shows.repository.UserRepository;

import java.util.concurrent.ExecutionException;

public class LoginViewModel extends AbstractCrudViewModel<User,UserRepository> {

    LiveData<User> userLiveData;
    UserRepository userRepository;

    boolean isSuccessAdding = false;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }


    public User getCurrentUserAsync(){
        User user = null;
        try {
            UserGetAsyncTask userGetAsyncTask = new UserGetAsyncTask(userRepository.userDao);
            user = userGetAsyncTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void loginFunc(String email, String password){
        userRepository.login(email,password,getApplication());
    }

    public LiveData<User> getCurrentUser(){
        userLiveData = userRepository.getFirstUser();
        return userLiveData;
    }

    public LiveData<User> getCurrentUserV2(String email, String password){
        userLiveData = userRepository.getByPasswordAndEmail(email,password);
        return userLiveData;
    }
}
