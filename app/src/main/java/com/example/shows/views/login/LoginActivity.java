package com.example.shows.views.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.shows.MainActivity;
import com.example.shows.R;
import com.example.shows.databinding.ActivityLoginBinding;
import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.User;
import com.example.shows.viewModel.LoginViewModel;
import com.example.shows.viewModel.PerformanceViewModel;
import com.example.shows.views.FullyPerformActivity;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;
    ActivityLoginBinding loginBinding;
    boolean isSuccessAdding = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginBinding.setLifecycleOwner(this);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);


        //из-за этого лагает экран активности
    /*    loginViewModel.getCurrentUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
              //  if(user!=null && user.getId() != 0){
                  //  goToMainActivity();
                    isSuccessAdding = true;

            }
        });*/

        loginViewModel.getCurrentUser().observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user!=null && user.getId()!=0){
                    isSuccessAdding = true;
                }
            }
        });

        loginViewModel.getCurrentUser();
/*        DatabaseShows databaseShows = DatabaseShows.getInstance(this);

       AsyncTask.execute(
                new Runnable() {
                    @Override
                    public void run() {
                       //databaseShows.genersDao().insert(geners);
                        //databaseShows.genersDao().deleteAllGeners();
                        //databaseShows.scenaristDao().deleteAllScenarist();
                        //databaseShows.actorDao().deleteAllActors();
                        databaseShows.userDao().deleteAllUsers();
                       // databaseShows.genersDao().deleteAllGeners();

                    }
                }
        );*/

    }

    public void loginFunc(View view) {
        if(loginBinding.etEmail.getText()!=null && loginBinding.etPassword.getText()!=null
                && loginBinding.etEmail.getText().toString() != ""
                && loginBinding.etPassword.getText().toString() != ""
        ){
            loginViewModel.loginFunc(loginBinding.etEmail.getText().toString(), loginBinding.etPassword.getText().toString());
            if(isSuccessAdding == true){
          //  User user = loginViewModel.getCurrentUser().getValue();
            Intent intent = new Intent(this, MainActivity.class);
            //intent.putExtra(User.class.getSimpleName(), user);
            startActivity(intent);
            finish();
            }
        }
    }

    public void goToRegistrationPage(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}