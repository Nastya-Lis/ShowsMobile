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

        bookingPageBinding.setLifecycleOwner(this);

        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingPageBinding.setVmBook(bookingViewModel);

        if(Utils.isOnline(this)){
            bookingViewModel.ifNetworkOn(this);
        }

        bookingViewModel.getPerformanceData(performance.getId()).observe(this, new Observer<Performance>() {
            @Override
            public void onChanged(Performance performance) {
            }
        });

        bookingViewModel.getCurrentUserFromDb();
        bookingViewModel.getPerformanceData(performance.getId());
    }


    public void  increaseAmount(View view){
        if(Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString()) < performance.getAmountTickets()) {
            bookingPageBinding.numberOfTicket.setText(String.valueOf(Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString()) + 1));
            bookingPageBinding.totalSum.setText(String.format("%.2f",Double.parseDouble(bookingPageBinding.totalSum.getText().toString())
                    + performance.getPrice()));
        }
    }

    public void decreaseAmount(View view){
        if(Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString()) > 0) {

            if(Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString()) == 0)
                bookingPageBinding.totalSum.setText(0);

            if(Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString()) != 0) {
                bookingPageBinding.numberOfTicket.setText(String.valueOf(Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString()) - 1));
                bookingPageBinding.totalSum.setText(String.format("%.2f",Double.parseDouble(bookingPageBinding.totalSum.getText().toString())
                        - performance.getPrice()));
            }

        }
    }

    public void doBooking(View view){
        Integer amount = Integer.valueOf(bookingPageBinding.numberOfTicket.getText().toString());

        if(amount!=0) {
            Booking bookingToSend = new Booking();
            bookingToSend.setAmount(amount);
            bookingToSend.setPerformanceId(getPerformanceFromBundle().getId());
            bookingToSend.setUserId(getCurrentUserFromBundle().getId());
            bookingToSend.setUser(getCurrentUserFromBundle());
            bookingToSend.setPerformance(getPerformanceFromBundle());


            bookingViewModel.bookingLiveData = new MutableLiveData<>(bookingToSend);
            bookingViewModel.insertBookingLiveData();
            bookingViewModel.pushToServer(bookingToSend);

            performance.setAmountTickets(performance.getAmountTickets() - amount);
            bookingViewModel.updatePerformance(performance);

            if (Utils.isOnline(this) == true) {

                JavaMailApi javaMailApi = new JavaMailApi(getCurrentUserFromBundle().getEmail(),
                        "Бронирование билетов", getPerformanceFromBundle(), bookingToSend);
                javaMailApi.execute();
                Toast.makeText(this, "Success booking", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
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