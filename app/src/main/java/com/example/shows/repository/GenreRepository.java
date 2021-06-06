package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.asyncClasses.OperationAsyncClass;
import com.example.shows.model.database.dao.GenersDao;
import com.example.shows.model.database.dao.PerformanceDao;
import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.layerAboveNetwork.mapping.GenerMapping;
import com.example.shows.model.layerAboveNetwork.mapping.PerformanceMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.GenreApi;
import com.example.shows.model.network.api.PerformanceApi;
import com.example.shows.model.network.dto.GenersDto;
import com.example.shows.model.network.dto.PerformanceDto;

import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreRepository extends CommonRepository<Geners> {

    private GenersDao genersDao;

    private LiveData<List<Geners>> genersList;
    private LiveData<Geners> genersLiveData;

    public GenreRepository(Context context) {
        super(context);
        genersDao = database.genersDao();
        genersList = new MutableLiveData<>();
        genersLiveData = new MutableLiveData<>();
    }

    public LiveData<Geners> getGenersLiveData(Integer id){
        genersLiveData = genersDao.getById(id);
        return genersLiveData;
    }

    public LiveData<Geners> getFirstGenre(){
        genersLiveData = genersDao.getFirst();
        return genersLiveData;
    }

    public void getAllGenersFromApi(Context context){

        NetworkSmth networkSmth = new NetworkSmth();
        GenreApi genreApi = networkSmth.genreApi();
        genreApi.getAllGenres().enqueue(new Callback<List<GenersDto>>() {

            @Override
            public void onResponse(Call<List<GenersDto>> call, Response<List<GenersDto>> response) {

                GenerMapping mapper = Mappers.getMapper(GenerMapping.class);
                List<Geners> geners = mapper.dtoesToEntities(response.body());
                GenreRepository repository = new GenreRepository(context);

                for (Geners gener: geners) {
                    repository.insert(gener,null);
                }
            }

            @Override
            public void onFailure(Call<List<GenersDto>> call, Throwable t) {
                Log.d("genre repository","Something is going wrong"+t.getMessage() +t.getCause());
            }
        });
    }

    @Override
    public void insert(Geners item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<GenersDao,Geners>
                (genersDao,onError,GenersDao::insert).execute(item);
        return;
    }

    @Override
    public void update(Geners item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<GenersDao,Geners>
                (genersDao,onError,GenersDao::update).execute(item);
        return;
    }

    @Override
    public void delete(Geners item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<GenersDao,Geners>
                (genersDao,onError,GenersDao::delete).execute(item);
        return;
    }
}
