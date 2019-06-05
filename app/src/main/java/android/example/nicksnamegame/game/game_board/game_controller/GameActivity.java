package android.example.nicksnamegame.game.game_board.game_controller;

import android.content.SharedPreferences;
import android.example.nicksnamegame.R;
import android.example.nicksnamegame.game.dagger.GameApplication;
import android.example.nicksnamegame.game.game_board.NextButtonManager;
import android.example.nicksnamegame.game.game_board.gameBoardManager.GameBoardManager;
import android.example.nicksnamegame.game.game_board.gameBoardManager.ShuffledListListener;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;


public class GameActivity extends AppCompatActivity {

    private static final String TAG = GameActivity.class.getSimpleName();

    /* TODO - tracking: if answered right on the first try, remove coworker from the pool.
     *  If there aren't enough people left in the pool to fill the grid, allow "wrong" answers
     *  to come from the list of known people, but make sure they're never the "right" answer again
     *  until the pool is empty and the game is restarted
     */

    private ShuffledListListener nextButtonListener;

    @Inject
    NextButtonManager nextButtonManager;
    @Inject
    GameBoardManager gameBoardManager;

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useDarkTheme = sharedPreferences.getBoolean(getString(R.string.pref_dark_theme_key), getResources().getBoolean(R.bool.pref_dark_theme_default));
        int theme = useDarkTheme ? R.style.AppTheme_Dark_GameTheme : R.style.AppTheme_GameTheme;
        setTheme(theme);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        setupSharedPreferences();

        FragmentManager fragmentManager = getSupportFragmentManager();

        // check FragmentManager and only create a new one if there isn't a GameBoardFragment
        // i.e., upon rotation
        if (fragmentManager.findFragmentByTag(getString(R.string.game_board_tag)) == null) {
            // Replace game board fragment placeholder with actual fragment
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.game_board_placeholder, new GameBoardFragment(), getString(R.string.game_board_tag));
            ft.commit();
        }

        // inject dependencies
        ((GameApplication) getApplication())
                .getGameComponent()
                .injectInto(GameActivity.this);

        // prepare the nextButton FAB
        FloatingActionButton nextButton = findViewById(R.id.next_button);
        nextButtonManager.setFab(nextButton);

        nextButtonListener = (shuffledList -> {
            // disable nextButton FAB
            nextButtonManager.setEnabled(false);
        });

        gameBoardManager.setShuffledListListener(nextButtonListener);
    }

    @Override
    public void onDestroy() {
        gameBoardManager.unsetShuffledListListener(nextButtonListener);
        super.onDestroy();
    }
}
