package com.example.shows.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.layerAboveNetwork.service.PerformanceService;
import com.example.shows.repository.PerformanceRepository;

import java.util.List;

//TODO LiveData<List<Performance>>
//обращение к репозиторию за данными, которые нужно отобразить в активити

//создать ссылку на репозиторий
public class PerformanceViewModel extends AbstractCrudViewModel<Performance,PerformanceRepository> {

    //PerformanceRepository performanceRepository;

    public PerformanceViewModel(@NonNull Application application) {
        super(application);
        repository = new PerformanceRepository(application);
        listLiveData = repository.getAllPerformances();
        justOneLiveDataElement = repository.getPerformanceFirst();
    }

    public LiveData<List<Performance>> getPerformancesFromDb() {
        listLiveData = repository.getAllPerformances();
        return listLiveData;
    }


    public LiveData<Performance> getPerformanceByIdFromDb(Integer integer){
        justOneLiveDataElement = repository.getPerformanceById(integer);
        return justOneLiveDataElement;
    }


    //СТАРАЯ ВЕРСИЯ ХЕРОТЫ

/*    PerformanceRepository performanceRepository;
    PerformanceService performanceService;
    Context context;

    public MutableLiveData<List<Performance>> mutableLiveData;


    public LiveData<List<Performance>> getValuePerform(){
            mutableLiveData = new MutableLiveData<>();
                    mutableLiveData.postValue(performanceService.getPerformancesFromDb().getValue());


        return mutableLiveData;
    }

    public void setValue(){
            mutableLiveData = new MutableLiveData<>();
                    mutableLiveData.postValue(performanceService.getPerformancesFromDb().getValue());

    }

    public PerformanceViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        performanceService = new PerformanceService(context);
    }*/
}
