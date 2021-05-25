package com.example.shows.model.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shows.model.database.contract.DbTriggers;
import com.example.shows.model.database.contract.RoleType;
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
import com.example.shows.model.database.entity.converter.ConverterDateType;
import com.example.shows.model.layerAboveNetwork.service.PerformanceService;

@Database(entities = {Actor.class, ActorPerformance.class, Booking.class, Geners.class,
        Performance.class, Scenarist.class, ScenaristPerformance.class, User.class}, version = 8)
@TypeConverters({ConverterDateType.class})
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
                nameDB).fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback)
                .build();
        return INSTANCE;
    }

    private void createRoles(SupportSQLiteDatabase db){
        for(RoleType roleType : RoleType.values()){
            db.execSQL(DbTriggers.getInsertRole(roleType));
        }
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
           // PerformanceService performanceService = new PerformanceService();
        }
    };

}
