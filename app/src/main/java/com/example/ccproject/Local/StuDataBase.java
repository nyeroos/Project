package com.example.ccproject.Local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ccproject.Model.Student;

@Database(entities = Student.class, version = 1)
public abstract class StuDataBase extends RoomDatabase {

    public abstract StudentDao studentDao();

    private static StuDataBase Instance;
    public static StuDataBase getInstance(Context context){
        if(Instance==null){
            Instance = Room.databaseBuilder(context,StuDataBase.class,"NYEdb")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }

}
