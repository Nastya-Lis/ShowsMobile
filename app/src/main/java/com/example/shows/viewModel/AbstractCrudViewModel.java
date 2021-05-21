package com.example.shows.viewModel;

import android.app.Application;
import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shows.repository.CommonRepository;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractCrudViewModel<E, R extends CommonRepository<E>> extends AndroidViewModel {

    protected LiveData<List<E>> listLiveData;
    protected LiveData<E> justOneLiveDataElement;
    protected R repository;

    public AbstractCrudViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<E>> getLiveDataList() {
        return listLiveData;
    }

    public LiveData<E> getLiveDataById(){
        return justOneLiveDataElement;
    }

    public void add(E item, Consumer<SQLiteException> onError)
    {
        repository.insert(item, onError);
    }

    public void update(E item, Consumer<SQLiteException> onError) {
        repository.update(item, onError);
    }

    public void delete(E item, Consumer<SQLiteException> onError) {
        repository.delete(item, onError);
    }
}
