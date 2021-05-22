package com.example.shows.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.example.shows.R;
import com.example.shows.databinding.ActivityBookingPageBinding;
import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.User;
import com.example.shows.viewModel.BookingViewModel;
import com.example.shows.viewModel.PerformanceViewModel;


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

        bookingPageBinding = DataBindingUtil.setContentView(this, R.layout.activity_booking_page);
        //ВНИМАНИЕ!!! ОЧЕНЬ ВАЖНАЯ СТРОКА!!!!!!!!!!
        bookingPageBinding.setLifecycleOwner(this);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingPageBinding.setVmBook(bookingViewModel);


        bookingViewModel.getPerformanceData(performance.getId()).observe(this, new Observer<Performance>() {
            @Override
            public void onChanged(Performance performance) {
               // bookingViewModel.performanceMutableLiveData.postValue(performance);
            }
        });


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
        booking.setPerformanceId(performance.getId());
       // user = bookingViewModel.userLiveData.getValue();

        Integer amount = Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString());
        booking.setAmount(amount);
        booking.setUserId(1);

        bookingViewModel.bookingLiveData = new MutableLiveData<>(booking);
        bookingViewModel.insertBookingLiveData();
    }

    private Performance getPerformanceFromBundle(){
        Bundle bundle = getIntent().getExtras();
        Performance performance = (Performance) bundle.getSerializable(Performance.class.getSimpleName());
        return performance;
    }
}