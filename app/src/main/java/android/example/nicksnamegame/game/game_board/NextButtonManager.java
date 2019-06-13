package android.example.nicksnamegame.game.game_board;

import android.example.nicksnamegame.game.game_board.gameBoardManager.GameBoardManager;
import android.example.nicksnamegame.game.game_board.game_controller.GameActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NextButtonManager {

    private GameBoardManager gameBoardManager;

    private FloatingActionButton fab;

    @Inject
    NextButtonManager() {
    }

    /* TODO: ideally, remove GameBoardManager from here
     */

    public void setGameBoardManager(GameBoardManager gameBoardManager) {
        this.gameBoardManager = gameBoardManager;
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
        setEnabled(false);
//        fab.setOnClickListener(v -> gameBoardManager.generateGameBoard());
    }

    public void setOnClickListener(GameActivity listener) {
        fab.setOnClickListener(listener);
    }

    public void setEnabled(boolean isEnabled) {
//        gameBoardManager.setCorrectAnswerClicked(isEnabled);
        if (fab != null) {
            fab.setClickable(isEnabled);

            if (isEnabled) fab.show();
            else fab.hide();
        }
    }

}
