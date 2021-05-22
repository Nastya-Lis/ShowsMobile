package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;

import com.example.shows.model.database.dao.ScenaristDao;
import com.example.shows.model.database.dao.ScenaristPerformanceDao;
import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.database.entity.ScenaristPerformance;

import java.util.List;
import java.util.function.Consumer;

public class ScenaristRepository extends CommonRepository<Scenarist>{

    ScenaristDao scenaristDao;
    ScenaristPerformanceDao scenaristPerformance;

    LiveData<List<Scenarist>> scenaristListLiveData;

    public ScenaristRepository(Context context) {
        super(context);
        scenaristDao = database.scenaristDao();
        scenaristPerformance = database.scenaristPerformanceDao();
    }

    public LiveData<List<Scenarist>> getScenaristsByPerformanceId(Integer id){
        scenaristListLiveData = scenaristPerformance.getScenaristsByPerformance(id);
        return scenaristListLiveData;
    }

    @Override
    public void insert(Scenarist item, Consumer<SQLiteException> onError) {

    }

    @Override
    public void update(Scenarist item, Consumer<SQLiteException> onError) {

    }

    @Override
    public void delete(Scenarist item, Consumer<SQLiteException> onError) {

    }
}
