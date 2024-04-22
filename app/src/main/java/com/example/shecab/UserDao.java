package com.example.shecab;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * from users where userEmail=(:userLoginEmail) and userPassword=(:userLoginPassword)")
    UserEntity login(String userLoginEmail, String userLoginPassword);
}
