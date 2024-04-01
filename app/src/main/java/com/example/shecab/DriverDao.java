package com.example.shecab;

import androidx.room.Dao;
import androidx.room.Insert;
@Dao
public interface DriverDao {

    @Insert
    void registerDriver(DriverEntity driverEntity);
}
