package com.example.shows.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;


import com.example.shows.MainActivity;
import com.example.shows.R;
import com.example.shows.databinding.ActivityFullyPerformBinding;
import com.example.shows.model.database.asyncClasses.PerformanceGetByIdAsync;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.database.entity.Mark;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.database.entity.User;
import com.example.shows.utils.Utils;
import com.example.shows.viewModel.PerformanceViewModel;
import com.example.shows.views.login.recyclerViews.RecyclerAdapterPerformance;

import org.mapstruct.ap.internal.model.source.MapperOptions;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Supplier;

public class FullyPerformActivity extends AppCompatActivity {

    PerformanceViewModel performanceViewModel;
    private ActivityFullyPerformBinding fullyPerformBinding;
    Performance sending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fully_perform);

        sending = getDataCurrentPerformance();
        fullyPerformBinding = DataBindingUtil.setContentView(this, R.layout.activity_fully_perform);
        fullyPerformBinding.setLifecycleOwner(this);

        performanceViewModel = ViewModelProviders.of(this).get(PerformanceViewModel.class);
        fullyPerformBinding.setVm(performanceViewModel);


       performanceViewModel.getGenreById(sending.getGenreId()).observe(this, new Observer<Geners>() {
            @Override
            public void onChanged(Geners geners) {

            }
        });

       performanceViewModel.getActorsByIdPerform(sending.getId()).observe(this, new Observer<List<Actor>>() {
           @Override
           public void onChanged(List<Actor> actorList) {

           }
       });

       performanceViewModel.getScenaristsByIdPerform(sending.getId()).observe(this, new Observer<List<Scenarist>>() {
           @Override
           public void onChanged(List<Scenarist> scenarists) {

           }
       });

        performanceViewModel.getMarkLiveData(sending.getId(),getCurrentUserFromBundle().getId()).observe(this, new Observer<Mark>() {
            @Override
            public void onChanged(Mark mark) {
                if (mark != null) {
                    if (mark.isLikeOrNot()) {
                        fullyPerformBinding.checkBoxLike.setChecked(true);
                    } else
                        fullyPerformBinding.checkBoxLike.setChecked(false);
                }
            }
        });



        performanceViewModel.getPerformanceByIdFromDb(sending.getId());
        performanceViewModel.getGenreById(sending.getGenreId());
        performanceViewModel.getActorsByIdPerform(sending.getId());
        performanceViewModel.getScenaristsByIdPerform(sending.getId());
        performanceViewModel.getMarkLiveData(sending.getId(), getCurrentUserFromBundle().getId());
    }


    private Performance getDataCurrentPerformance(){
        Bundle bundle = getIntent().getExtras();
        Performance getPerformance = (Performance) bundle.getSerializable(Performance.class.getSimpleName());
        return  getPerformance;
    }

    public void getBookingPage(View view){
        Intent intent = new Intent(this, BookingPageActivity.class);
        intent.putExtra(Performance.class.getSimpleName(), sending);
        intent.putExtra(User.class.getSimpleName(),getCurrentUserFromBundle());
        startActivity(intent);
    }

    private User getCurrentUserFromBundle(){
        Bundle bundle = getIntent().getExtras();
        User user = (User) bundle.getSerializable(User.class.getSimpleName());
        return user;
    }

    public void saveLikeState(View view){
        if(fullyPerformBinding.checkBoxLike.isChecked()){
            if(Utils.isOnline(this)){
                performanceViewModel.setToServerRating( fullyPerformBinding.checkBoxLike.isChecked(),getDataCurrentPerformance().getId());
                //Integer[]  massiv = new Integer[]{getDataCurrentPerformance(),};
            }
            performanceViewModel.setMarkLiveData(getDataCurrentPerformance().getId(),getCurrentUserFromBundle().getId(),
                    fullyPerformBinding.checkBoxLike.isChecked());
        }
        else{
            if(Utils.isOnline(this)){
                performanceViewModel.setToServerRating( false,getDataCurrentPerformance().getId());
            }
            performanceViewModel.setMarkLiveData(getDataCurrentPerformance().getId(),getCurrentUserFromBundle().getId(),false);
        }
    }

}