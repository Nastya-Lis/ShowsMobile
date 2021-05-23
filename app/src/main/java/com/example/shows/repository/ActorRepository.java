package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shows.model.database.asyncClasses.OperationAsyncClass;
import com.example.shows.model.database.dao.ActorDao;
import com.example.shows.model.database.dao.ActorPerformanceDao;
import com.example.shows.model.database.dao.GenersDao;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.ActorPerformance;
import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.layerAboveNetwork.mapping.ActorMapping;
import com.example.shows.model.layerAboveNetwork.mapping.GenerMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.ActorApi;
import com.example.shows.model.network.api.GenreApi;
import com.example.shows.model.network.dto.ActorDto;
import com.example.shows.model.network.dto.GenersDto;


import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActorRepository extends CommonRepository<Actor>{

    ActorDao actorDao;
    ActorPerformanceDao actorPerformanceDao;
    private  LiveData<List<Actor>> actorsByPerformanceId;

    private LiveData<List<Actor>> actorList;
    private LiveData<Actor> actorLiveData;

    public ActorRepository(Context context) {
        super(context);
        actorDao = database.actorDao();
        actorPerformanceDao = database.actorPerformanceDao();
    }


    public LiveData<List<Actor>> getActorsByPerformanceId(Integer idPerform){
        actorsByPerformanceId = actorPerformanceDao.getActorsByPerformance(idPerform);
        return actorsByPerformanceId;
    }


    public void getAllActorsFromApi(Context context){

        NetworkSmth networkSmth = new NetworkSmth();
        ActorApi actorApi = networkSmth.actorApi();
        actorApi.getAllActors().enqueue(new Callback<List<ActorDto>>() {

            @Override
            public void onResponse(Call<List<ActorDto>> call, Response<List<ActorDto>> response) {

                ActorMapping mapper = Mappers.getMapper(ActorMapping.class);
                List<Actor> actors = mapper.dtoesToEntities(response.body());
                if(response.body().isEmpty()){
                    Log.d("Padla pustaiaa","yeeep");
                }
                Log.d("apiPerform fuccc","yeeep");

                ActorRepository repository = new ActorRepository(context);
                ActorPerformanceRepository actorPerformanceRepository = new ActorPerformanceRepository(context);

                for (Actor actor: actors) {
                    //Set<ActorPerformance> actorPerformanceSet = new HashSet<>();
                    for (Performance performance: actor.getPerformances()) {
                        ActorPerformance actorPerformance = new ActorPerformance();
                        actorPerformance.setActor_id(actor.getId());
                        actorPerformance.setPerformance_id(performance.getId());
                        //actorPerformanceSet.add(actorPerformance);
                        actorPerformanceRepository.insert(actorPerformance, null);
                    };
                    repository.insert(actor,null);
                }
            }

            @Override
            public void onFailure(Call<List<ActorDto>> call, Throwable t) {
                Log.d("apiPerform suckk","Something is going wrong"+t.getMessage() +t.getCause());
            }
        });
    }


    @Override
    public void insert(Actor item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<ActorDao, Actor>
                (actorDao,onError,ActorDao::insert).execute(item);
        return;
    }

    @Override
    public void update(Actor item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<ActorDao, Actor>
                (actorDao,onError,ActorDao::update).execute(item);
        return;
    }

    @Override
    public void delete(Actor item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<ActorDao, Actor>
                (actorDao,onError,ActorDao::delete).execute(item);
        return;
    }
}
