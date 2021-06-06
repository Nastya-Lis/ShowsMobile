package com.example.shows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.shows.databinding.ActivityMainBinding;
import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.ActorPerformance;
import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.Role;
import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.database.entity.ScenaristPerformance;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.layerAboveNetwork.service.ActorService;
import com.example.shows.model.layerAboveNetwork.service.GenreService;
import com.example.shows.model.layerAboveNetwork.service.PerformanceService;
import com.example.shows.model.layerAboveNetwork.service.ScenaristService;
import com.example.shows.model.layerAboveNetwork.service.UserService;
import com.example.shows.utils.Utils;
import com.example.shows.viewModel.PerformanceViewModel;
import com.example.shows.views.BookingPageActivity;
import com.example.shows.views.FullyPerformActivity;
import com.example.shows.views.SearchingActivity;
import com.example.shows.views.login.LoginActivity;
import com.example.shows.views.login.recyclerViews.RecyclerAdapterPerformance;
import com.google.android.material.internal.ContextUtils;

import java.util.ArrayList;
import java.util.List;

//TODO observer and viewmodeladapter

public class MainActivity extends AppCompatActivity {


    RecyclerAdapterPerformance recyclerAdapterPerformance;

    private ActivityMainBinding mainBinding;
    PerformanceViewModel performanceViewModel;

    User currentUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setLifecycleOwner(this);

/*        DatabaseShows databaseShows = DatabaseShows.getInstance(this);

        AsyncTask.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        //databaseShows.genersDao().insert(geners);

                        databaseShows.genersDao().deleteAllGeners();
                        databaseShows.performanceDao().deleteAllPerformances();
                        databaseShows.scenaristDao().deleteAllScenarist();
                        databaseShows.actorDao().deleteAllActors();

                        databaseShows.bookingDao().deleteAllBookings();
                       // databaseShows.genersDao().deleteAllGeners();

                    }
                }
        );*/
        recyclerAdapterPerformance = new RecyclerAdapterPerformance();
       performanceViewModel = ViewModelProviders.of(this).get(PerformanceViewModel.class);

        if(Utils.isOnline(this)){
            performanceViewModel.ifNetworkConnectionOn(this);
        }
       performanceViewModel.getPerformancesFromDb().observe(this,new Observer<List<Performance>>() {
            @Override
            public void onChanged(List<Performance> performanceList) {
                recyclerAdapterPerformance.setPerformanceList(performanceList);
                mainBinding.performancesRecycler.setAdapter(recyclerAdapterPerformance);
                mainBinding.performancesRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });

/*
        performanceViewModel.getCurrentUser().observeForever( new Observer<User>() {
            @Override
            public void onChanged(User user) {
            //    mainBinding.buttonReload.setText(user.getLogin());
                currentUser = user;
            }
        });*/

        performanceViewModel.getPerformancesFromDb();
     //   performanceViewModel.getCurrentUser();


    }


    @Override
    protected void onResume() {
        super.onResume();
        creationOfPopupMenu();
    }


    private void creationOfPopupMenu() {
        recyclerAdapterPerformance.setOnPerformanceClickListener(performance -> {
            Intent intent = new Intent(this, FullyPerformActivity.class);
         //   intent.putExtra(User.class.getSimpleName(),currentUser);
            intent.putExtra(User.class.getSimpleName(),performanceViewModel.getCurrentUserAsync());
            intent.putExtra(Performance.class.getSimpleName(), performance);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
                case R.id.searching:
                    Intent intent = new Intent(this, SearchingActivity.class);
                    startActivity(intent);
                    break;

        }
        return super.onOptionsItemSelected(item);
    }


}