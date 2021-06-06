package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.shows.model.database.asyncClasses.OperationAsyncClass;
import com.example.shows.model.database.asyncClasses.QueryAsyncClass;
import com.example.shows.model.database.dao.BookingDao;
import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.database.entity.ScenaristPerformance;
import com.example.shows.model.layerAboveNetwork.mapping.BookingMapping;
import com.example.shows.model.layerAboveNetwork.mapping.ScenaristMapping;
import com.example.shows.model.network.NetworkSmth;
import com.example.shows.model.network.api.BookingApi;
import com.example.shows.model.network.api.ScenaristApi;
import com.example.shows.model.network.api.UserApi;
import com.example.shows.model.network.dto.BookingDto;
import com.example.shows.model.network.dto.ScenaristDto;

import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.function.Consumer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingRepository extends CommonRepository<Booking>{

    BookingDao bookingDao;
    LiveData<List<Booking>> bookingLiveData;

    public BookingRepository(Context context) {
        super(context);
        bookingDao = database.bookingDao();
    }

    public void getAllBookingFromApi(Integer userId, Context context){
        NetworkSmth networkSmth = NetworkSmth.getInstance();
        UserApi userApi= networkSmth.userApi();

        userApi.getFromApiBookingCurrentUser(userId).enqueue(new Callback<List<BookingDto>>() {
            @Override
            public void onResponse(Call<List<BookingDto>> call, Response<List<BookingDto>> response) {
                List<BookingDto> bookingDtos = response.body();

                BookingRepository bookingRepository = new BookingRepository(context);

                for (BookingDto bookingDto: bookingDtos) {
                    Booking booking = new Booking();
                    booking.setId(bookingDto.getId());
                    booking.setAmount(bookingDto.getAmount());
                    booking.setUserId(bookingDto.getUser());
                    booking.setPerformanceId(bookingDto.getPerformance());

                    bookingRepository.insert(booking,null);
                    Log.d("booking:", "UserId:" + booking.getUserId() + " PerformanceId:" + booking.getPerformanceId());
                }

            }

            @Override
            public void onFailure(Call<List<BookingDto>> call, Throwable t) {
                Log.d("booking repository",t.getMessage() + t.getCause() + t.getStackTrace());
            }
        });

    }


    public void pushToServerBooking(Booking bookingToSend){
        NetworkSmth networkSmth = new NetworkSmth();
        BookingApi bookingApi = networkSmth.bookingApi();

        bookingApi.pushOnServer(bookingToSend.getAmount(),bookingToSend.getUserId(),
                bookingToSend.getPerformanceId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Log.d("success:", "push booking to server");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("suck:", String.valueOf(t));
            }
        });
    }

    //вытаскиваем бронь из бд по айди юзера
    public LiveData<List<Booking>> getBookingByUserIdFromDb(Integer userId){
        bookingLiveData = bookingDao.getBookingByUser(userId);
        return bookingLiveData;
    }

    public LiveData<List<Booking>> getBookingsByIdPerformAndUser(Integer idUser,Integer idPerform ){
        bookingLiveData = bookingDao.getBookingByUserAndPerform(idUser, idPerform);
        return bookingLiveData;
    }

    @Override
    public void insert(Booking item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<BookingDao,Booking>(bookingDao,onError,BookingDao::insert).execute(item);
        return;
    }

    @Override
    public void update(Booking item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<BookingDao,Booking>(bookingDao,onError,BookingDao::update).execute(item);
        return;
    }

    @Override
    public void delete(Booking item, Consumer<SQLiteException> onError) {
        new OperationAsyncClass<BookingDao,Booking>(bookingDao,onError,BookingDao::delete).execute(item);
        return;
    }
}
