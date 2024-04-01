package com.example.shecab;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {DriverEntity.class}, version = 1)
public abstract class DriverDatabase extends RoomDatabase {
    private static final String dbName = "driver";
    private static DriverDatabase driverDatabase;

    public static synchronized DriverDatabase getDriverDatabase(Context context) {
        if (driverDatabase == null) {
            driverDatabase = Room.databaseBuilder(context, DriverDatabase.class, dbName)
                    .fallbackToDestructiveMigrationFrom()
                    .build();
        }

        return driverDatabase;
    }
    public abstract DriverDao driverDao();

}
