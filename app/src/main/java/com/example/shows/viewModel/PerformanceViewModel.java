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

import com.example.shows.model.database.asyncClasses.UserGetAsyncTask;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.database.entity.Mark;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.layerAboveNetwork.service.PerformanceService;
import com.example.shows.repository.ActorRepository;
import com.example.shows.repository.GenreRepository;
import com.example.shows.repository.MarkRepository;
import com.example.shows.repository.PerformanceRepository;
import com.example.shows.repository.ScenaristRepository;
import com.example.shows.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

import lombok.SneakyThrows;

public class PerformanceViewModel extends AbstractCrudViewModel<Performance,PerformanceRepository> {

    GenreRepository genreRepository;
    ActorRepository actorRepository;
    ScenaristRepository scenaristRepository;
    UserRepository userRepository;
    MarkRepository markRepository;

    LiveData<Actor> actorLiveData;
    public LiveData<Geners> genersLiveData;
    public LiveData<List<Actor>> actorsListLiveData;
    public LiveData<List<Performance>> listLiveDataPerformancesSearch;
    public LiveData<List<Scenarist>> scenaristListLiveData;
    public LiveData<User> userLiveData;
    public LiveData<Mark> markLiveData;


    public PerformanceViewModel(@NonNull Application application) {
        super(application);
        repository = new PerformanceRepository(application);
        genreRepository = new GenreRepository(application);
        actorRepository = new ActorRepository(application);
        scenaristRepository = new ScenaristRepository(application);
        userRepository = new UserRepository(application);
        markRepository = new MarkRepository(application);
        listLiveData = repository.getAllPerformances();
        actorLiveData = new MutableLiveData<>();
        listLiveDataPerformancesSearch = repository.getAllPerformances();
        justOneLiveDataElement = repository.getPerformanceFirst();
    }


    //асинк таск получение юзера
    public User getCurrentUserAsync(){
        User user = null;
        try {
            UserGetAsyncTask userGetAsyncTask = new UserGetAsyncTask(userRepository.userDao);
            user = userGetAsyncTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    //для выставления оценки
    public LiveData<Mark> getMarkLiveData(Integer performanceId, Integer userId){
        markLiveData = markRepository.getMarkLiveData(performanceId, userId);
        return markLiveData;
    }

    //когда появляется интернет соединение, отправляются данные на сервер
    public void setToServerRating(Boolean likeOrNot, Integer performanceId){
        markRepository.sendToServer(likeOrNot,performanceId);
    }

    public void setMarkLiveData(Integer performanceId, Integer userId,Boolean likeOrNot){
        markRepository.setMarkToDb(performanceId,userId, likeOrNot);
        //repository.update();
    }

    public List<Performance> getPerformancesByCriteriaSearch(String criteria, String name){
        List<Performance> performancesListSearch = new ArrayList<>();
        switch (criteria){
            case "Актер":
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
            case "Сценарист":
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
        }
        return performancesListSearch;
    }

    public LiveData<List<Performance>> returnCurrentLiveDataPerfSearch(){
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
    }


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
