package com.example.shows.model.database.entity;


import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.List;

@Entity(tableName = "user")
public class User extends CommonEntity{
    private String login;
    private String email;
    private String password;

    @Ignore
    private List<Booking> bookings;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }


}
