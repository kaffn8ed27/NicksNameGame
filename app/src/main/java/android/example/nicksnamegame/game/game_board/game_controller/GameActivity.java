package android.example.nicksnamegame.game.game_board.game_controller;

import android.content.SharedPreferences;
import android.example.nicksnamegame.R;
import android.example.nicksnamegame.game.dagger.GameApplication;
import android.example.nicksnamegame.game.game_board.NextButtonManager;
import android.example.nicksnamegame.game.game_board.gameBoardManager.GameBoardManager;
import android.example.nicksnamegame.game.game_board.gameBoardManager.ShuffledListListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;


public class GameActivity extends AppCompatActivity  implements View.OnClickListener {

    private static final String TAG = GameActivity.class.getSimpleName();

    /* TODO - tracking: if answered right on the first try, remove coworker from the pool.
     *  If there aren't enough people left in the pool to fill the grid, allow "wrong" answers
     *  to come from the list of known people, but make sure they're never the "right" answer again
     *  until the pool is empty and the game is restarted
     */
    @Inject
    NextButtonManager nextButtonManager;
    private ShuffledListListener shuffledListListener;
    private CorrectAnswerListener correctAnswerListener;

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

        // inject dependencies
        ((GameApplication) getApplication())
                .getAppComponent()
                .injectInto(GameActivity.this);

        // check FragmentManager and only create a new one if there isn't a GameBoardFragment
        // i.e., upon rotation
        if (fragmentManager.findFragmentByTag(getString(R.string.game_board_tag)) == null) {
            Log.d(TAG, "No fragment found. Creating new one.");
            // Replace game board fragment placeholder with actual fragment
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.game_board_placeholder, new GameBoardFragment(), getString(R.string.game_board_tag));
            ft.commit();
        } else {
            Log.d(TAG, "Using existing fragment");
        }

        // prepare the nextButton FAB
        FloatingActionButton nextButton = findViewById(R.id.next_button);
        nextButtonManager.setFab(nextButton);
        nextButtonManager.setOnClickListener(this);
        shuffledListListener = (shuffledList -> {
            // disable nextButton FAB
            nextButtonManager.setEnabled(false);
        });

        // listener to enable Next button
        correctAnswerListener = (() -> nextButtonManager.setEnabled(true));

        // when a GameBoardFragment is created, get its GameBoardManager and pass it to the NextButtonManager
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                if (f instanceof GameBoardFragment) {
                    // cast the fragment to GameBoardFragment type to access its methods
                    GameBoardFragment gameBoardFragment = (GameBoardFragment) (f);
                    // retrieve the GBM from the fragment
                    GameBoardManager gameBoardManager = gameBoardFragment.getGameBoardManager();
                    // register the GBM with the Next button manager
                    nextButtonManager.setGameBoardManager(gameBoardManager);
                    // pass listeners into GBM
                    gameBoardManager.setCorrectAnswerListener(correctAnswerListener);
                    gameBoardManager.setShuffledListListener(shuffledListListener);
                }
            }
        }, false);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction newFragment = fragmentManager.beginTransaction();
        Fragment completedGameBoardFragment = fragmentManager
                .findFragmentByTag(getString(R.string.game_board_tag));
        if (completedGameBoardFragment != null) {
            newFragment.remove(completedGameBoardFragment);
        }
        GameBoardFragment newGameBoardFragment = new GameBoardFragment();
        newFragment.replace(R.id.game_board_placeholder, newGameBoardFragment, getString(R.string.game_board_tag));
        newFragment.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
