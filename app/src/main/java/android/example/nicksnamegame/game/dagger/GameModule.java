package android.example.nicksnamegame.game.dagger;

import android.content.Context;
import android.example.nicksnamegame.data.db.NameGameDatabase;
import android.example.nicksnamegame.data.db.PersonDao;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
abstract class GameModule {

    @Singleton
    @Provides
    static NameGameDatabase provideDb(Context context) {
        NameGameDatabase db = Room.databaseBuilder(
                context,
                NameGameDatabase.class,
                "people_database"
        )
                .build();

        return db;
    }

    @Singleton
    @Provides
    static PersonDao providePersonDao(NameGameDatabase db) {
        return db.personDao();
    }
}

