package com.example.shows.model.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shows.model.database.dao.ActorDao;
import com.example.shows.model.database.dao.ActorPerformanceDao;
import com.example.shows.model.database.dao.BookingDao;
import com.example.shows.model.database.dao.GenersDao;
import com.example.shows.model.database.dao.PerformanceDao;
import com.example.shows.model.database.dao.ScenaristDao;
import com.example.shows.model.database.dao.ScenaristPerformanceDao;
import com.example.shows.model.database.dao.UserDao;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.ActorPerformance;
import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.database.entity.ScenaristPerformance;
import com.example.shows.model.database.entity.User;

@Database(entities = {Actor.class, ActorPerformance.class, Booking.class, Geners.class,
        Performance.class, Scenarist.class, ScenaristPerformance.class, User.class}, version = 1)
public abstract class DatabaseShows extends RoomDatabase {
    public abstract ActorDao actorDao();
    public abstract ActorPerformanceDao actorPerformanceDao();
    public abstract BookingDao bookingDao();
    public abstract GenersDao genersDao();
    public abstract PerformanceDao performanceDao();
    public abstract ScenaristDao scenaristDao();
    public abstract ScenaristPerformanceDao scenaristPerformanceDao();
    public abstract UserDao userDao();


    private static final String nameDB = "ShowsDb";

    public static volatile DatabaseShows INSTANCE;

    public static DatabaseShows getInstance(final Context context){

        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseShows.class,
                nameDB).build();
        return INSTANCE;
    }
}
