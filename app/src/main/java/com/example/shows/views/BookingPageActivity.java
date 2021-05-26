package com.example.shows.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shows.MainActivity;
import com.example.shows.R;
import com.example.shows.databinding.ActivityBookingPageBinding;
import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.layerAboveNetwork.mapping.BookingMapping;
import com.example.shows.model.network.dto.BookingDto;
import com.example.shows.utils.Utils;
import com.example.shows.utils.mailService.JavaMailApi;
import com.example.shows.viewModel.BookingViewModel;
import com.example.shows.viewModel.PerformanceViewModel;

import org.mapstruct.factory.Mappers;


public class BookingPageActivity extends AppCompatActivity {
    Performance performance;
    ActivityBookingPageBinding bookingPageBinding;
    BookingViewModel bookingViewModel;

    User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);

        performance = getPerformanceFromBundle();
        user = getCurrentUserFromBundle();

        bookingPageBinding = DataBindingUtil.setContentView(this, R.layout.activity_booking_page);
        //ВНИМАНИЕ!!! ОЧЕНЬ ВАЖНАЯ СТРОКА!!!!!!!!!!
        bookingPageBinding.setLifecycleOwner(this);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingPageBinding.setVmBook(bookingViewModel);

        if(Utils.isOnline(this)){
            bookingViewModel.ifNetworkOn(this);
        }

        bookingViewModel.getPerformanceData(performance.getId()).observe(this, new Observer<Performance>() {
            @Override
            public void onChanged(Performance performance) {
               // bookingViewModel.performanceMutableLiveData.postValue(performance);
            }
        });



        bookingViewModel.getCurrentUserFromDb();
        bookingViewModel.getPerformanceData(performance.getId());


        //bookingViewModel.getUserById(1);
       // user = bookingViewModel.getUserById(1).getValue();
    }


    public void  increaseAmount(View view){
        if(Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString()) <= performance.getAmountTickets()) {
            bookingPageBinding.numberOfTicket.setText(String.valueOf(Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString()) + 1));
            bookingPageBinding.totalSum.setText(String.valueOf(Double.parseDouble(bookingPageBinding.totalSum.getText().toString())
                    + performance.getPrice()));
        }
    }

    public void decreaseAmount(View view){
        if(Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString()) > 0) {
            bookingPageBinding.numberOfTicket.setText(String.valueOf(Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString()) - 1));
            bookingPageBinding.totalSum.setText(String.valueOf(Double.parseDouble(bookingPageBinding.totalSum.getText().toString())
                    - performance.getPrice()));
        }
    }

    public void doBooking(View view){
        Booking booking = new Booking();
      //  booking.setPerformanceId(performance.getId());
       // user = bookingViewModel.userLiveData.getValue();

        Integer amount = Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString());
       /* booking.setPerformance(performance);
        booking.setPerformanceId(performance.getId());
        booking.setAmount(amount);
        booking.setUser(getCurrentUserFromBundle());
        booking.setUserId(getCurrentUserFromBundle().getId());

        BookingMapping mapper = Mappers.getMapper(BookingMapping.class);
        BookingDto bookingDto = mapper.entityToDto(booking);*/

        Booking bookingToSend = new Booking();
        bookingToSend.setAmount(amount);
        bookingToSend.setPerformanceId(getPerformanceFromBundle().getId());
        bookingToSend.setUserId(getCurrentUserFromBundle().getId());
        bookingToSend.setUser(getCurrentUserFromBundle());
        bookingToSend.setPerformance(getPerformanceFromBundle());


        bookingViewModel.bookingLiveData = new MutableLiveData<>(bookingToSend);
        bookingViewModel.insertBookingLiveData();
        bookingViewModel.pushToServer(bookingToSend);
        if(Utils.isOnline(this) == true){

            JavaMailApi javaMailApi = new JavaMailApi(getCurrentUserFromBundle().getEmail(),
                    "Бронирование билетов", getPerformanceFromBundle());
            javaMailApi.execute();
            Toast.makeText(this,"Success booking",Toast.LENGTH_SHORT).show();
            /*Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();*/
        }
    }

    private Performance getPerformanceFromBundle(){
        Bundle bundle = getIntent().getExtras();
        Performance performance = (Performance) bundle.getSerializable(Performance.class.getSimpleName());
        return performance;
    }

    private User getCurrentUserFromBundle(){
        Bundle bundle = getIntent().getExtras();
        User user = (User) bundle.getSerializable(User.class.getSimpleName());
        return user;
    }
}