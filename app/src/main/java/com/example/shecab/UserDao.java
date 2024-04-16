package com.example.shecab;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * from users where userEmail=(:userEmail) and userPassword=(:userPassword)")
    UserEntity login(String userEmail, String userPassword);
}
