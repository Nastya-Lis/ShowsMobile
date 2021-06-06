package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.ColumnInfo;

import com.example.shows.model.database.asyncClasses.MarkAsyncClass;
import com.example.shows.model.database.asyncClasses.OperationAsyncClass;
import com.example.shows.model.database.dao.GenersDao;
import com.example.shows.model.database.dao.MarkDao;
import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.database.entity.Mark;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.PerformanceApi;

import java.util.function.Consumer;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkRepository extends CommonRepository<Mark>{
    MarkDao markDao;
    private LiveData<Mark> markLiveData;

    public MarkRepository(Context context) {
        super(context);
        markDao = database.markDao();
        markLiveData = new MutableLiveData<>();
    }

    public LiveData<Mark> getMarkLiveData(Integer performanceId, Integer userId){
        markLiveData = markDao.getMarkByPerformanceId(performanceId,userId);
        return  markLiveData;
    }

    public void sendToServer(Boolean likeOrNot, Integer performanceId){
        NetworkSmth networkSmth = new NetworkSmth();
        PerformanceApi performanceApi = networkSmth.performanceApi();

        performanceApi.setLike(likeOrNot,performanceId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Log.d("send to server rating:", "success");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("send to server rating:", "error" + t);
            }
        });
    }

    @SneakyThrows
    public void setMarkToDb(Integer performanceId, Integer userId, Boolean likeOrNot){
        MarkAsyncClass markAsyncClass = new MarkAsyncClass(markDao);
        Mark mark = markAsyncClass.execute(performanceId).get();
        if(mark != null)
        {
            mark.setLikeOrNot(likeOrNot);
            update(mark,null);
        }
        else
        {
            mark = new Mark();
            mark.setPerformance_id(performanceId);
            mark.setUser_id(userId);
            mark.setLikeOrNot(likeOrNot);
            insert(mark,null);
        }
    }

    @Override
    public void insert(Mark item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<MarkDao, Mark>
                (markDao,onError,MarkDao::insert).execute(item);
        return;
    }

    @Override
    public void update(Mark item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<MarkDao, Mark>
                (markDao,onError,MarkDao::update).execute(item);
        return;
    }

    @Override
    public void delete(Mark item, Consumer<SQLiteException> onError) {

    }
}
