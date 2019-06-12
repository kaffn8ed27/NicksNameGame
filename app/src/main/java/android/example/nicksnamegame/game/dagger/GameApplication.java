package android.example.nicksnamegame.game.dagger;

import android.app.Application;

public class GameApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = createAppComponent();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent
                .builder()
                .withContext(this)
                .build();
    }
}
