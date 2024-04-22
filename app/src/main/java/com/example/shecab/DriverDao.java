package com.example.shecab;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DriverDao {

    @Insert
    void registerDriver(DriverEntity driverEntity);

    @Query("SELECT * from drivers where driverEmail=(:driverLoginEmail) and driverPassword=(:driverLoginPassword)")
    DriverEntity login(String driverLoginEmail, String driverLoginPassword);
}
