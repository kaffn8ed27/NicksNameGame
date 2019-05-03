//package android.example.nicksnamegame.game;
//
//import android.app.Application;
//
//public class GameApplication extends Application {
//    private GameComponent gameComponent;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        gameComponent = createGameComponent();
//    }
//
//    GameComponent getGameComponent() {
//        return gameComponent;
//    }
//
//    private GameComponent createGameComponent() {
//        return DaggerGameComponent
//                .builder()
//                .peopleShufflerModule(new GameModule())
//                .build();
//    }
//}
