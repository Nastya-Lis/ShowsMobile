package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shows.model.database.asyncClasses.OperationAsyncClass;
import com.example.shows.model.database.dao.GenersDao;
import com.example.shows.model.database.dao.ScenaristDao;
import com.example.shows.model.database.dao.ScenaristPerformanceDao;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.ActorPerformance;
import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.database.entity.ScenaristPerformance;
import com.example.shows.model.layerAboveNetwork.mapping.GenerMapping;
import com.example.shows.model.layerAboveNetwork.mapping.ScenaristMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.GenreApi;
import com.example.shows.model.network.api.ScenaristApi;
import com.example.shows.model.network.dto.GenersDto;
import com.example.shows.model.network.dto.ScenaristDto;

import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScenaristRepository extends CommonRepository<Scenarist>{

    public ScenaristDao scenaristDao;
    public ScenaristPerformanceDao scenaristPerformance;

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

    public void getAllScenaristFromApi(Context context){

        NetworkSmth networkSmth = new NetworkSmth();
        ScenaristApi scenaristApi = networkSmth.scenaristApi();
        scenaristApi.getAllScenarists().enqueue(new Callback<List<ScenaristDto>>() {

            @Override
            public void onResponse(Call<List<ScenaristDto>> call, Response<List<ScenaristDto>> response) {

                ScenaristMapping mapper = Mappers.getMapper(ScenaristMapping.class);
                List<Scenarist> scenarists = mapper.dtoesToEntities(response.body());
                ScenaristRepository repository = new ScenaristRepository(context);
                ScenaristPerformanceRepository scenaristPerformanceRepository = new ScenaristPerformanceRepository(context);

                for (Scenarist scenarist: scenarists) {
                    for (Performance performance: scenarist.getPerformances()) {
                    ScenaristPerformance scenaristPerformance = new ScenaristPerformance();
                    scenaristPerformance.setScenarist_id(scenarist.getId());
                    scenaristPerformance.setPerformance_id(performance.getId());
                    scenaristPerformanceRepository.insert(scenaristPerformance, null);
                };
                    repository.insert(scenarist,null);
                }
            }

            @Override
            public void onFailure(Call<List<ScenaristDto>> call, Throwable t) {
                Log.d("scenarist repository","Something is going wrong "+t.getMessage() +t.getCause());
            }
        });
    }

    @Override
    public void insert(Scenarist item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<ScenaristDao,Scenarist>
                (scenaristDao,onError,ScenaristDao::insert).execute(item);
        return;
    }

    @Override
    public void update(Scenarist item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<ScenaristDao,Scenarist>
                (scenaristDao,onError,ScenaristDao::update).execute(item);
        return;
    }

    @Override
    public void delete(Scenarist item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<ScenaristDao,Scenarist>
                (scenaristDao,onError,ScenaristDao::delete).execute(item);
        return;
    }
}
