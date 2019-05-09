package android.example.nicksnamegame.game.game_board;

import android.example.nicksnamegame.game.game_board.gameBoardManager.GameBoardManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NextButtonManager {

    final GameBoardManager gameBoardManager;

    private FloatingActionButton fab;
    @Inject
    public NextButtonManager(GameBoardManager gameBoardManager) {
        this.gameBoardManager = gameBoardManager;
    }


    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
        setEnabled(false);
        fab.setOnClickListener(v -> gameBoardManager.generateGameBoard());
    }

    public void setEnabled(boolean enabled) {
        gameBoardManager.setCorrectAnswerClicked(enabled);
        fab.setClickable(enabled);

        if (enabled) fab.show();
        else fab.hide();
    }

}
