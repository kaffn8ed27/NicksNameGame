package android.example.nicksnamegame.game.dagger;

import android.content.Context;
import android.example.nicksnamegame.data.PersonRepo;
import android.example.nicksnamegame.game.game_board.NextButtonManager;
import android.example.nicksnamegame.game.game_board.game_controller.GameActivity;
import android.example.nicksnamegame.limit_game.game_board.LimitGameActivity;

import java.util.Random;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = AppModule.class)

public interface AppComponent {
    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder withContext(Context context);
    }

    // getters for child component(s)
    Context getContext();
    PersonRepo getPersonRepo();
    Random getRandom();
    NextButtonManager getNextButtonManager();

    void injectInto(GameActivity gameActivity);

    void injectInto(LimitGameActivity limitGameActivity);
}
