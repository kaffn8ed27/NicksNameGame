package android.example.nicksnamegame.game;

import android.content.Context;

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
