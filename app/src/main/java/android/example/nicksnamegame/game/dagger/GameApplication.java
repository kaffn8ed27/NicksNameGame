package android.example.nicksnamegame.game.dagger;

import android.app.Application;

public class GameApplication extends Application {
    private GameComponent gameComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        gameComponent = createGameComponent();
    }

    public GameComponent getGameComponent() {
        return gameComponent;
    }

    private GameComponent createGameComponent() {
        return DaggerGameComponent
                .builder()
                .withContext(this)
                .build();
    }
}
