package android.example.nicksnamegame.game.dagger;

import android.content.Context;
import android.example.nicksnamegame.game.GameActivity;
import android.example.nicksnamegame.game.PersonViewHolder;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = GameModule.class)

public interface GameComponent {

    @Component.Builder
    interface Builder {
        GameComponent build();

        @BindsInstance
        Builder withContext(Context context);
    }

    void injectInto(GameActivity gameActivity);

    void injectInto(PersonViewHolder personViewHolder);


}
