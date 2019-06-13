package android.example.nicksnamegame.game.game_board;

import android.example.nicksnamegame.game.game_board.game_controller.GameActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NextButtonManager {

    private FloatingActionButton fab;

    @Inject
    NextButtonManager() {
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
        setEnabled(false);
    }

    public void setOnClickListener(GameActivity listener) {
        fab.setOnClickListener(listener);
    }

    public void setEnabled(boolean isEnabled) {
        if (fab != null) {
            fab.setClickable(isEnabled);

            if (isEnabled) fab.show();
            else fab.hide();
        }
    }

}
