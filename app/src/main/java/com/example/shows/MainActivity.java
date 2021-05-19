package com.example.shows;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.layerAboveNetwork.service.ActorService;
import com.example.shows.model.layerAboveNetwork.service.GenreService;
import com.example.shows.model.layerAboveNetwork.service.PerformanceService;
import com.example.shows.model.layerAboveNetwork.service.ScenaristService;
import com.example.shows.model.layerAboveNetwork.service.UserService;
import com.example.shows.views.login.recyclerViews.RecyclerAdapterPerformance;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapterPerformance recyclerAdapterPerformance;
    List<Performance> performanceList = null;
    PerformanceService performanceService;
    ActorService actorService;
    ScenaristService scenaristService;
    GenreService genreService;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   performanceService = new PerformanceService(this);
   //     scenaristService = new ScenaristService(this);
    //   actorService = new ActorService(this);
     //   genreService = new GenreService(this);
        userService = new UserService(this);
        recyclerView = (RecyclerView) findViewById(R.id.performancesRecycler);

        if(performanceList!=null) {
            recyclerAdapterPerformance = new RecyclerAdapterPerformance(performanceList);
            recyclerView.setAdapter(recyclerAdapterPerformance);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    void doAgainShit(){
        recyclerAdapterPerformance = new RecyclerAdapterPerformance(performanceList);
        recyclerView.setAdapter(recyclerAdapterPerformance);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void reloadFromPerformApi(View view) {
        // performanceList = performanceService.getPerformancesFromDb();
        //  doAgainShit();
      //  performanceService.getAllPerformancesFromApi();
      //  scenaristService.getAllScenaristsFromApi();
      //  genreService.getAllGenresFromApi();
     //   actorService.getAllActorsFromApi();
        userService.getAllUsersFromApi();
    }
}