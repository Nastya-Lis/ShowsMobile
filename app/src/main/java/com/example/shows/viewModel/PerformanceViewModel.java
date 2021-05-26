package com.example.shows.viewModel;

import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.shows.model.database.asyncClasses.ActorForSearch;
import com.example.shows.model.database.asyncClasses.ActorPerformForSearch;
import com.example.shows.model.database.asyncClasses.ActorPerformanceAsyncClass;
import com.example.shows.model.database.asyncClasses.ScenaristForSearch;
import com.example.shows.model.database.asyncClasses.ScenaristPerformForSearch;
import com.example.shows.model.database.dao.ActorDao_Impl;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.layerAboveNetwork.service.PerformanceService;
import com.example.shows.repository.ActorRepository;
import com.example.shows.repository.GenreRepository;
import com.example.shows.repository.PerformanceRepository;
import com.example.shows.repository.ScenaristRepository;
import com.example.shows.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

import lombok.SneakyThrows;

//обращение к репозиторию за данными, которые нужно отобразить в активити

//создать ссылку на репозиторий
public class PerformanceViewModel extends AbstractCrudViewModel<Performance,PerformanceRepository> {

    //PerformanceRepository performanceRepository;

    GenreRepository genreRepository;
    ActorRepository actorRepository;
    ScenaristRepository scenaristRepository;
    UserRepository userRepository;


    LiveData<Actor> actorLiveData;


    public LiveData<Geners> genersLiveData;
    public LiveData<List<Actor>> actorsListLiveData;
    public LiveData<List<Performance>> listLiveDataPerformancesSearch;
    public LiveData<List<Scenarist>> scenaristListLiveData;
    public LiveData<User> userLiveData;


    public PerformanceViewModel(@NonNull Application application) {
        super(application);
        repository = new PerformanceRepository(application);
        genreRepository = new GenreRepository(application);
        actorRepository = new ActorRepository(application);
        scenaristRepository = new ScenaristRepository(application);
        userRepository = new UserRepository(application);
      //  genersLiveData = genreRepository.getFirstGenre();
        listLiveData = repository.getAllPerformances();


        actorLiveData = new MutableLiveData<>();

        listLiveDataPerformancesSearch = repository.getAllPerformances();
        justOneLiveDataElement = repository.getPerformanceFirst();
    }


    public List<Performance> getPerformancesByCriteriaSearch(String criteria, String name){
        List<Performance> performancesListSearch = new ArrayList<>();
        switch (criteria){
            case "Actor":
                try {
                    ActorForSearch actorForSearch = new ActorForSearch(actorRepository.actorDao);
                    Actor actor = actorForSearch.execute(name).get();
                    ActorPerformForSearch actorPerformForSearch = new ActorPerformForSearch(actorRepository.actorPerformanceDao);
                    if(actor!=null)
                    performancesListSearch = actorPerformForSearch.execute(actor.getId()).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            case "Scenarist":
                try {
                    ScenaristForSearch scenaristForSearch= new ScenaristForSearch(scenaristRepository.scenaristDao);
                    Scenarist scenarist = scenaristForSearch.execute(name).get();
                    ScenaristPerformForSearch scenaristPerformForSearch = new ScenaristPerformForSearch(scenaristRepository.scenaristPerformance);
                    if(scenarist!=null)
                    performancesListSearch = scenaristPerformForSearch.execute(scenarist.getId()).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            case "Gener":
                break;
        }
        return performancesListSearch;
    }

    public LiveData<List<Performance>> returnCurrentLiveDataPerfSearch(){
        return listLiveDataPerformancesSearch;
    }

    public LiveData<List<Performance>> chaaaaange(){
       /* if(actor != null)
        listLiveDataPerformancesSearch = repository.getPerformancesByActorId(actor.getId());*/
        return listLiveDataPerformancesSearch;
    }

    public LiveData<List<Performance>> getPerformancesByIdActorSearch(Integer id){
        listLiveDataPerformancesSearch = repository.getPerformancesByActorId(id);
        return listLiveDataPerformancesSearch;
    }

    //взять текущего юзера, записанного в бд, в случае, если нет доступа к инету
    public LiveData<User> getCurrentUser(){
        userLiveData =  userRepository.getFirstUser();
        return userLiveData;
    }

    //получение всех спектаклей из бд
    public LiveData<List<Performance>> getPerformancesFromDb() {
        listLiveData = repository.getAllPerformances();
        return listLiveData;
    }

    //получение спектакля по айдишнику
    public LiveData<Performance> getPerformanceByIdFromDb(Integer integer){
        justOneLiveDataElement = repository.getPerformanceById(integer);
        return justOneLiveDataElement;
    }

    //получение жанра по айдишнику спектакля
    public LiveData<Geners> getGenreById(Integer id){
        genersLiveData = genreRepository.getGenersLiveData(id);
        return genersLiveData;
    }

    //получение списка актеров по айдишнику спектакля
    public LiveData<List<Actor>> getActorsByIdPerform(Integer id){
        actorsListLiveData = actorRepository.getActorsByPerformanceId(id);
        return actorsListLiveData;
    }


    //получение списка сценаристов по айдишнику спектакля
    public LiveData<List<Scenarist>> getScenaristsByIdPerform(Integer id){
        scenaristListLiveData = scenaristRepository.getScenaristsByPerformanceId(id);
        return scenaristListLiveData;
    }

    public void ifNetworkConnectionOn(Context context){
        genreRepository.getAllGenersFromApi(context);
        actorRepository.getAllActorsFromApi(context);
        scenaristRepository.getAllScenaristFromApi(context);
        repository.getAllPerformancesFromApi(getApplication());
//       Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//       thread.start();
//     //  thread.stop();
//       new Thread(new Runnable() {
//           @Override
//           public void run() {
//               repository.getAllPerformancesFromApi(context);
//           }
//       }).start();

    }

  /*  public LiveData<List<Performance>> getPerformancesFromRest(PerformanceService performanceService){
        List<Performance> performances = performanceService.getAllPerformancesFromApi();
        listLiveData = new MutableLiveData(performances);
        return listLiveData;
    }*/

    //адаптер для представления списка актеров в виде стринга
    @BindingAdapter("android:text")
    public static void convertFromActorsListToString(TextView textActor, LiveData<List<Actor>> actorsListLiveData){
        List<Actor> actorList = actorsListLiveData.getValue();
        if( actorList != null && actorList.size()!=0) {
            char symbol = ',';
            String str = "";
            for (int i = 0; i < actorList.size(); i++) {
                if(i!=actorList.size()-1){
                  str += actorList.get(i).getName() + symbol;
                }
                else{
                    symbol = '.';
                    str += actorList.get(i).getName() + symbol;
                }
            }
            textActor.setText(str);
        }
    }


    //адаптер для представления списка спектаклей в виде стринга
    @BindingAdapter("android:text")
    public static void convertFromScenaristListToString(TextView textScenarist, LiveData<List<Scenarist>> scenaristListLiveData){
        List<Scenarist> scenaristList = scenaristListLiveData.getValue();
        if( scenaristList != null && scenaristList.size()!=0) {
            char symbol = ',';
            String str = "";
            for (int i = 0; i < scenaristList.size(); i++) {
                if(i!=scenaristList.size()-1){
                    str += scenaristList.get(i).getName() + symbol;
                }
                else{
                    symbol = '.';
                    str += scenaristList.get(i).getName() + symbol;
                }
            }
            textScenarist.setText(str);
        }
    }

}
