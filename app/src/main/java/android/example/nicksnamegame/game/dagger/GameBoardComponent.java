package android.example.nicksnamegame.game.dagger;

import android.example.nicksnamegame.game.game_board.PersonViewHolder;
import android.example.nicksnamegame.game.game_board.game_controller.GameBoardFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
@GameBoardScope
public interface GameBoardComponent {
    void injectInto(GameBoardFragment gameBoardFragment);

    void injectInto(PersonViewHolder personViewHolder);
}
