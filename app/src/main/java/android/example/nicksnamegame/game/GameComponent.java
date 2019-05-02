package android.example.nicksnamegame.game;

import android.example.nicksnamegame.launcher.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = PeopleShufflerModule.class)
interface GameComponent {

    void inject(GameActivity gameActivity);

}
