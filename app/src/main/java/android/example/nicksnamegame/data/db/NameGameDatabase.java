package android.example.nicksnamegame.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class NameGameDatabase extends RoomDatabase {
    public abstract PersonDao personDao();
}
