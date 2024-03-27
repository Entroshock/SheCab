package com.example.shecab;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "users")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo (name = "userId")
    String userId;

    @ColumnInfo (name = "password")
    String password;

    @ColumnInfo (name = "email")
    String email;

    @ColumnInfo (name = "phone")
    String phone;






    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
