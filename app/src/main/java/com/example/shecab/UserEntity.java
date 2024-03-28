package com.example.shecab;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "users")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    Integer id;



    @ColumnInfo (name = "userPassword")
    String userPassword;

    @ColumnInfo (name = "userEmail")
    String userEmail;

    @ColumnInfo (name = "userPhone")
    String userPhone;

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }









}
