package android.example.nicksnamegame.game.dagger;

import android.example.nicksnamegame.game.game_board.PersonViewHolder;
import android.view.View;

import dagger.BindsInstance;
import dagger.Subcomponent;

@Subcomponent
@PersonViewScope
public interface PersonViewComponent {
    PersonViewHolder getPersonViewHolder();

    @Subcomponent.Builder
    interface Builder {
        PersonViewComponent build();

        @BindsInstance
        Builder withView(View view);
    }
}
