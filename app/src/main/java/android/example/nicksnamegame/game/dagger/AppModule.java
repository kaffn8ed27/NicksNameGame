package android.example.nicksnamegame.game.dagger;

import android.content.Context;
import android.example.nicksnamegame.data.db.NameGameDatabase;
import android.example.nicksnamegame.data.db.PersonDao;

import androidx.room.Room;

import java.util.Random;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
abstract class AppModule {

    @Singleton
    @Provides
    static NameGameDatabase provideDb(Context context) {

        return Room.databaseBuilder(
                context,
                NameGameDatabase.class,
                "people_database"
        )
                .build();

    }

    @Singleton
    @Provides
    static PersonDao providePersonDao(NameGameDatabase db) {
        return db.personDao();
    }

    @Singleton
    @Provides
    static Random provideRandom() {
        return new Random();
    }

}

