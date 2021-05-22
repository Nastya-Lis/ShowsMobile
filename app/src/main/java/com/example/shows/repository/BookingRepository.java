package com.example.shows.repository;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;

import com.example.shows.model.database.asyncClasses.OperationAsyncClass;
import com.example.shows.model.database.asyncClasses.QueryAsyncClass;
import com.example.shows.model.database.dao.BookingDao;
import com.example.shows.model.database.entity.Booking;

import java.util.List;
import java.util.function.Consumer;

public class BookingRepository extends CommonRepository<Booking>{

    BookingDao bookingDao;
    LiveData<List<Booking>> bookingLiveData;

    public BookingRepository(Context context) {
        super(context);
        bookingDao = database.bookingDao();
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
