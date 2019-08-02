package com.example.customerlist.Room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.example.customerlist.Model.CustomerDetails;

@Database(entities = {CustomerInformation.class} ,  version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract CustomerDao customerDao();

}
