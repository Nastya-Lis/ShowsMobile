package com.example.shows.views.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shows.MainActivity;
import com.example.shows.R;
import com.example.shows.databinding.ActivityLoginBinding;
import com.example.shows.databinding.ActivitySighUpBinding;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.layerAboveNetwork.mapping.UserMapping;
import com.example.shows.model.network.dto.UserDto;
import com.example.shows.viewModel.LoginViewModel;
import com.example.shows.viewModel.SignUpViewModel;

import org.mapstruct.factory.Mappers;

public class SignUpActivity extends AppCompatActivity {

    SignUpViewModel signUpViewModel;
    ActivitySighUpBinding sighUpBinding;

    boolean isSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigh_up);

        sighUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sigh_up);
        sighUpBinding.setLifecycleOwner(this);
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);

        signUpViewModel.isSuccessToServer.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == true)
                    isSuccess = true;
            }
        });

    }

    public void registrFunc(View view){
        if(sighUpBinding.regName.getText()!=null && sighUpBinding.regEmail.getText()!=null&&
                sighUpBinding.regPassword.getText()!=null&&sighUpBinding.regRepassword.getText()!=null
                && sighUpBinding.regName.getText().toString()!= ""
                && sighUpBinding.regPassword.getText().toString() !=""){
            User user = new User();
            user.setEmail(sighUpBinding.regEmail.getText().toString());
            user.setLogin(sighUpBinding.regName.getText().toString());
            user.setPassword(sighUpBinding.regPassword.getText().toString());
            //user.setRole();
            UserMapping mapper = Mappers.getMapper(UserMapping.class);
            UserDto userDto = mapper.entityToDto(user);
            userDto.setRoleId(2);
            signUpViewModel.registration(userDto);
            if(isSuccess== true){
                Intent intent = new Intent(this, LoginActivity.class);
                // intent.putExtra(User.class.getSimpleName(), user);
                startActivity(intent);
               // finish();
            }
        }
    }

    public void goToLoginPage(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}