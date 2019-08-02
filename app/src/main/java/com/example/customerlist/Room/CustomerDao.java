package com.example.customerlist.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CustomerDao {
    @Query("SELECT * FROM customerinformation")
    List<CustomerInformation> getAll();

    @Insert
    void insert(CustomerInformation customerInformation);

    @Delete
    void delete(CustomerInformation customerInformation);
}
