package com.example.shows.model.layerAboveNetwork.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.shows.R;
import com.example.shows.model.database.DatabaseShows;
import com.example.shows.model.database.asyncClasses.PerformanceAsyncClass;
import com.example.shows.model.database.asyncClasses.constForAsync.ConstVariable;
import com.example.shows.model.database.dao.PerformanceDao;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.layerAboveNetwork.mapping.PerformanceMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.PerformanceApi;
import com.example.shows.model.network.dto.PerformanceDto;
import com.example.shows.repository.PerformanceRepository;
import com.google.common.collect.Multiset;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.security.KeyStore;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerformanceService {
    private final DatabaseShows databaseShows;
    private final PerformanceApi performanceApi = NetworkSmth.getInstance().performanceApi();
    Context context;

    public PerformanceService(Context context) {
        databaseShows = DatabaseShows.getInstance(context);
        this.context = context;
    }

    public void getAllPerformancesFromApi(){
       performanceApi.getAllPerformances().enqueue(new Callback<List<PerformanceDto>>() {

            @Override
            public void onResponse(Call<List<PerformanceDto>> call, Response<List<PerformanceDto>> response) {

                    PerformanceMapping mapper = Mappers.getMapper(PerformanceMapping.class);
                    List<Performance> performances = mapper.dtoesToEntities(response.body());
                    if(response.body().isEmpty()){
                        Log.d("performanceservice","success");
                    }
            }

            @Override
            public void onFailure(Call<List<PerformanceDto>> call, Throwable t) {
                Log.d("performanceservice","Something is going wrong "+t.getMessage() +t.getCause());
            }
        });



    }

}
