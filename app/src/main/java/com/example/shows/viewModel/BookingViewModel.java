package com.example.shows.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shows.model.database.dao.UserDao;
import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.network.dto.BookingDto;
import com.example.shows.repository.BookingRepository;
import com.example.shows.repository.PerformanceRepository;
import com.example.shows.repository.UserRepository;

import org.mapstruct.control.MappingControl;

import java.util.List;

public class BookingViewModel extends AbstractCrudViewModel<Booking, BookingRepository>{

    BookingRepository bookingRepository;
    PerformanceRepository performanceRepository;
    UserRepository userRepository;

    MutableLiveData<Boolean> isSuccess;

    public MutableLiveData<Booking> bookingLiveData;

    public MutableLiveData<Performance> performanceMutableLiveData;
    public LiveData<Performance> performanceLiveData;

    public LiveData<User> userLiveData;


    //обновление информации спектакля
    public void updatePerformance(Performance performance){
        performanceRepository.update(performance,null);
    }

    public LiveData<Performance> getPerformanceData(Integer id){
        performanceLiveData =  performanceRepository.getPerformanceById(id);
        return performanceLiveData;
    }

    public LiveData<User> getUserById(Integer id){
        userLiveData = userRepository.getUserById(id);
        return  userLiveData;
    }

    public void ifNetworkOn(Context context){
        bookingRepository.getAllBookingFromApi(3,getApplication());
    }

    public LiveData<User> getCurrentUserFromDb(){
        userLiveData = userRepository.getFirstUser();
        return userLiveData;
    }

    public BookingViewModel(@NonNull Application application) {
        super(application);
        bookingRepository = new BookingRepository(application);
        performanceRepository = new PerformanceRepository(application);
        userRepository = new UserRepository(application);
    }

    public void firstInitializeBookingLiveData(Integer idUser,Integer idPerform){
        Booking booking = new Booking();
        booking.setUserId(idUser);
        booking.setPerformanceId(idPerform);
        bookingLiveData.postValue(booking);

    }

    public void pushToServer(Booking booking){
        bookingRepository.pushToServerBooking(booking);
    }

    public void insertBookingLiveData(){
        bookingRepository.insert(bookingLiveData.getValue(),null);
    }

}
