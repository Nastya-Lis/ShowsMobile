package com.example.shows.repository;



//managing resources
//from what source(retrofit or db take data)
//when wifi off or on

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.shows.model.database.asyncClasses.OperationAsyncClass;
import com.example.shows.model.database.asyncClasses.PerformanceAsyncClass;
import com.example.shows.model.database.asyncClasses.QueryAsyncClass;
import com.example.shows.model.database.asyncClasses.QueryForCollectionAsyncClass;
import com.example.shows.model.database.dao.PerformanceDao;
import com.example.shows.model.database.dao.PerformanceDao_Impl;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.layerAboveNetwork.mapping.PerformanceMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.PerformanceApi;
import com.example.shows.model.network.dto.PerformanceDto;

import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO change in dao returning value from usual list to livedata
public class PerformanceRepository extends CommonRepository<Performance>{

    private PerformanceDao performanceDao;

    private Optional<Performance> optionalPerformance;

    private LiveData<List<Performance>> performancesList;
    private LiveData<Performance> performance;
    private MutableLiveData<Performance> performanceMutable;
    private int currentId = 0;
    private boolean isLastDefault = true;


    private LiveData<List<Performance>> listPerformanceForApiLiveData;


    public PerformanceRepository(Context context) {
        super(context);
        performanceDao = new PerformanceDao_Impl(database);
        performancesList = new MutableLiveData<>();
        performance = new MutableLiveData<>();
    }

    public Optional<Performance> checkIsExistFirstPerform(){
        optionalPerformance= performanceDao.checkIsExistPerformFirst();
        return optionalPerformance;
    }

    public LiveData<Performance> getPerformanceFirst(){
        performance = performanceDao.getFirstPerformance();
        return performance;
    }

    public void deleteAllPerformance(){
        performanceDao.deleteAllPerformances();
    }

    public LiveData<List<Performance>> getAllPerformances(){
     /*   new QueryForCollectionAsyncClass<Performance,PerformanceDao>(performancesList,
                () -> performanceDao.getAll()).execute();*/
        performancesList = performanceDao.getAll();
      //  isLastDefault = false;
        return performancesList;
    }

    public LiveData<Performance> getPerformanceById(Integer id){
     /*   new QueryForCollectionAsyncClass<Performance,PerformanceDao>(performancesList,
                () -> performanceDao.getAll()).execute();*/
        performance = performanceDao.getById(id);
        //  isLastDefault = false;
        return performance;
    }


    public void getAllPerformancesFromApi(Context context){

        NetworkSmth networkSmth = new NetworkSmth();
        PerformanceApi performanceApi = networkSmth.performanceApi();
        // List<PerformanceDto> performanceList = performanceApi.getAllPerformances().execute().body();

        //PerformanceMapping mapper = Mappers.getMapper(PerformanceMapping.class);
        //List<Performance> performances = new ArrayList<>();

        performanceApi.getAllPerformances().enqueue(new Callback<List<PerformanceDto>>() {

            @Override
            public void onResponse(Call<List<PerformanceDto>> call, Response<List<PerformanceDto>> response) {

                PerformanceMapping mapper = Mappers.getMapper(PerformanceMapping.class);
                List<Performance> performances = mapper.dtoesToEntities(response.body());
                if(response.body().isEmpty()){
                    Log.d("Padla pustaiaa","yeeep");
                }
                Log.d("apiPerform fuccc","yeeep");


          /*      listPerformanceForApiLiveData.observeForever(new Observer<List<Performance>>() {
                    @Override
                    public void onChanged(List<Performance> performanceList) {

                    }
                });
                */
                PerformanceRepository repository = new PerformanceRepository(context);

                for (Performance performance: performances) {
                    performance.setGenreId(performance.getGenre().getId());
                    repository.insert(performance,null);
                }

                //  insertPerformAsyncTask .execute(performances);
                //   PerformanceRepository repository = new PerformanceRepository(context);

                   /* AsyncTask.execute(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Optional<Performance> opt = repository.checkIsExistFirstPerform();
                                    if(opt != null){
                                        repository.deleteAllPerformance();
                                    }
                                    for (Performance performance:performances) {
                                        repository.insert(performance,null);
                                    }
                                }
                            }
                    );*/


                // Performance performance = mapper.dtoToEntity(response.body().get(0));
//                Log.d("performance","wooot:"
//                        + performance.getDescription() + performance.getId());

//                 if(performances.size()!=0){
//                    for (Performance performance: performances) {
//                       Log.d(String.valueOf("pipi"),"performances:"
//                                + performance.getName()+ " " + performance.getAmountTickets() + " "
//                                +performance.getId());
//                    }
//                }
                //    addToDb(performances);

            }

            @Override
            public void onFailure(Call<List<PerformanceDto>> call, Throwable t) {
                Log.d("apiPerform suckk","Something is going wrong"+t.getMessage() +t.getCause());
            }
        });



    }




  /*  public MutableLiveData<Performance> getPerformanceMutableById(Integer id){
        performanceMutable = performanceDao.getByIdMutable(id);
        return performanceMutable;
    }*/

    @Override
    public void insert(Performance item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<PerformanceDao,Performance>
                (performanceDao,onError,PerformanceDao::insert).execute(item);
        return;
    }

    @Override
    public void update(Performance item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<PerformanceDao,Performance>
                (performanceDao,onError, PerformanceDao::update).execute(item);
        return;
    }

    @Override
    public void delete(Performance item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<PerformanceDao,Performance>(
                performanceDao,onError,PerformanceDao::delete).execute(item);
        return;
    }

}
