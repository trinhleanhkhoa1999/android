package com.example.myloginapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myloginapp.Chanel;

@Database(entities = {Chanel.class}, version = 1)
public abstract class ChanelDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "chanel.db";
    private static ChanelDatabase instance;

    public  static synchronized ChanelDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ChanelDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract ChanelDao chanelDao();
}
