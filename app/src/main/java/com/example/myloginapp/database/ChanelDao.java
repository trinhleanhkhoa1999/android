package com.example.myloginapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myloginapp.Chanel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Dao
public interface ChanelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void InsertChanel(Chanel chanel);

    @Query("SELECT * FROM chanel")
    List<Chanel> getListChanel();

    @Delete
    void Delete(Chanel chanel);
}
