package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.provider.MediaStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.dao.GenersDao;
import com.example.shows.model.database.dao.PerformanceDao;
import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.database.entity.Performance;

import java.util.List;
import java.util.function.Consumer;

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

    @Override
    public void insert(Geners item, Consumer<SQLiteException> onError) {

    }

    @Override
    public void update(Geners item, Consumer<SQLiteException> onError) {

    }

    @Override
    public void delete(Geners item, Consumer<SQLiteException> onError) {

    }
}
