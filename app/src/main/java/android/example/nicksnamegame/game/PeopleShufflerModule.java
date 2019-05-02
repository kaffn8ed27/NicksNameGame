package android.example.nicksnamegame.game;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class PeopleShufflerModule {

    @Provides
    @Singleton
    static PeopleShufflerModule providePeopleShuffler() {
        return new PeopleShufflerModule();
    }
}
