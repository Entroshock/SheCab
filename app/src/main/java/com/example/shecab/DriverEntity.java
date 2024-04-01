package com.example.shecab;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "users")
public class DriverEntity {
    @PrimaryKey(autoGenerate = true)
    Integer driverId;

    @ColumnInfo(name = "driverPassword")
    String driverPassword;

    @ColumnInfo(name = "driverEmail")
    String driverEmail;

    @ColumnInfo(name = "driverPhone")
    String driverPhone;

    public String getDriverPassword() {
        return driverPassword;
    }

    public void setDriverPassword(String driverPassword) {
        this.driverPassword = driverPassword;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }
}