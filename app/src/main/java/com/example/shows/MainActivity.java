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
import com.example.shows.viewModel.PerformanceViewModel;
import com.example.shows.views.FullyPerformActivity;
import com.example.shows.views.login.recyclerViews.RecyclerAdapterPerformance;
import com.google.android.material.internal.ContextUtils;

import java.util.ArrayList;
import java.util.List;

//TODO observer and viewmodeladapter

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapterPerformance recyclerAdapterPerformance;
    List<Performance> performanceList = null;
    PerformanceService performanceService;
    ActorService actorService;
    ScenaristService scenaristService;
    GenreService genreService;
    UserService userService;

    List<Performance> performances = new ArrayList<>();
    LiveData<List<Performance>> listLiveData;


    //trying in livedata
    private ActivityMainBinding mainBinding;
    PerformanceViewModel performanceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setLifecycleOwner(this);

        Geners geners = new Geners();
        geners.setNameType("Naked and funny");

        Actor actor = new Actor();
        actor.setName("Dmitri Lze");
        actor.setBiography("Just freakin idiot");


        Scenarist scenarist = new Scenarist();
        scenarist.setName("Federiko Kastilo");
        scenarist.setBiography("Good boy");



        ActorPerformance actorPerformance = new ActorPerformance();
        actorPerformance.setActor_id(2);
        actorPerformance.setPerformance_id(3);


        ScenaristPerformance scenaristPerformance = new ScenaristPerformance();
        scenaristPerformance.setScenarist_id(4);
        scenaristPerformance.setPerformance_id(2);


     /*
        Role role = new Role();
        role.setName("User");*/

        User user = new User();
        user.setLogin("Bublik");
        user.setPassword("1234");
        user.setEmail("babka@mail.ru");
      //  List<Performance> performances = new ArrayList<>();

    /*    DatabaseShows databaseShows = DatabaseShows.getInstance(this);

        AsyncTask.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        databaseShows.userDao().insert(user);
                    }
                }
        );
*/
        recyclerAdapterPerformance = new RecyclerAdapterPerformance();


       performanceViewModel = ViewModelProviders.of(this).get(PerformanceViewModel.class);

       performanceViewModel.getPerformancesFromDb().observe(this, new Observer<List<Performance>>() {
            @Override
            public void onChanged(List<Performance> performanceList) {
                recyclerAdapterPerformance.setPerformanceList(performanceList);
                mainBinding.performancesRecycler.setAdapter(recyclerAdapterPerformance);
                mainBinding.performancesRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });



      // performances = performanceViewModel.getValuePerform().getValue();
//
//        recyclerAdapterPerformance = new RecyclerAdapterPerformance(performanceViewModel.getValuePerform().getValue());
//        mainBinding.performancesRecycler.setAdapter(recyclerAdapterPerformance);
//        mainBinding.performancesRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));





         /*  listLiveData.observe(this, new Observer<List<Performance>>() {
               @Override
               public void onChanged(List<Performance> performanceList) {
                   if (performanceList != null) {
                       recyclerAdapterPerformance = new RecyclerAdapterPerformance(performanceList);
                       mainBinding.performancesRecycler.setAdapter(recyclerAdapterPerformance);
                       mainBinding.performancesRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                   }
               }
           });*/


           /* recyclerAdapterPerformance = new RecyclerAdapterPerformance(performanceViewModel.getValuePerform().getValue());
            recyclerView.setAdapter(recyclerAdapterPerformance);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));*/



     //   performanceService = new PerformanceService(this);
   //     scenaristService = new ScenaristService(this);
    //   actorService = new ActorService(this);
     //   genreService = new GenreService(this);
    //    userService = new UserService(this);

      /*  recyclerView = (RecyclerView) findViewById(R.id.performancesRecycler);
        if(listLiveData!=null) {
            if(listLiveData.getValue()!=null) {
                recyclerAdapterPerformance = new RecyclerAdapterPerformance(listLiveData.getValue());
                recyclerView.setAdapter(recyclerAdapterPerformance);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
        }*/

    }


    @Override
    protected void onResume() {
        super.onResume();

      /*  if(listLiveData!=null) {
            if(listLiveData.getValue()!=null) {
                recyclerAdapterPerformance = new RecyclerAdapterPerformance(listLiveData.getValue());
                recyclerView.setAdapter(recyclerAdapterPerformance);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
        }*/

        creationOfPopupMenu();
    }


    private void creationOfPopupMenu() {
        recyclerAdapterPerformance.setOnPerformanceClickListener(performance -> {
            Intent intent = new Intent(this, FullyPerformActivity.class);
            intent.putExtra(Performance.class.getSimpleName(), performance);
            startActivity(intent);
        });
/*

        birthdayManAdapter.setOnBirthManLongClickListener((birthDayMan, view) -> {
            popupMenu = new PopupMenu(this, view);
            popupMenu.inflate(R.menu.context_menu);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.editId:
                        editRecipe(birthDayMan);
                        break;
                    case R.id.deleteId:
                        deleteRecipe(birthDayMan);
                        break;
                }
                return true;
            });
            popupMenu.show();
            return true;
        });
*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.filter:
//                Intent intent1 = new Intent(MainActivity.this, SortActivity.class);
//                startActivity(intent1);
                break;
            case R.id.bookingCheck:
//                Intent intent2 = new Intent(MainActivity.this, TemplateCreateActivity.class);
//                startActivity(intent2);
                break;
            case R.id.searching:
               /* Intent intent = new Intent(this, NotificationService.class);
                intent.putExtra("Date", dataPickFormat);
                startService(intent);*/
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    void doAgainShit(){
      /*  recyclerAdapterPerformance = new RecyclerAdapterPerformance(performanceList);
        recyclerView.setAdapter(recyclerAdapterPerformance);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
    }

    public void reloadFromPerformApi(View view) {
        listLiveData = performanceViewModel.getPerformancesFromDb();
       performances =  performanceViewModel.getPerformancesFromDb().getValue();



     //  performanceViewModel.getPerformanceByIdFromDb(5).observe();
       // performanceList = performanceService.getPerformancesFromDb();
         //  doAgainShit();
        //  performanceService.getAllPerformancesFromApi();
      //  scenaristService.getAllScenaristsFromApi();
       //  genreService.getAllGenresFromApi();
     //   actorService.getAllActorsFromApi();
     //     userService.getAllUsersFromApi();
    }
}