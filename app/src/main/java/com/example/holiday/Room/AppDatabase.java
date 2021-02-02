package com.example.holiday.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.holiday.Room.dao.TripsDao;

import com.example.holiday.Room.entity.Trips;

@Database(entities = {Trips.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final  String DB_NAME="db";
    public abstract TripsDao tripsDao();
    public static synchronized AppDatabase getAppDatabase(@NonNull Context context){
        if(INSTANCE==null){
              INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                     AppDatabase.class,
                      DB_NAME
              ).build();
        }
        return INSTANCE;
    }


}
