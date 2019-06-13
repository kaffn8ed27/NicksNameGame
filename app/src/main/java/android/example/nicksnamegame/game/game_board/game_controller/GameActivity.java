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


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = GameActivity.class.getSimpleName();
    /* TODO - tracking: if answered right on the first try, remove coworker from the pool.
     *  If there aren't enough people left in the pool to fill the grid, allow "wrong" answers
     *  to come from the list of known people, but make sure they're never the "right" answer again
     *  until the pool is empty and the game is restarted
     */
    @Inject
    NextButtonManager nextButtonManager;
    private String FRAGMENT_KEY_TAG;
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
        FRAGMENT_KEY_TAG = getString(R.string.game_board_tag);

        FragmentManager fragmentManager = getSupportFragmentManager();
        // inject dependencies
        ((GameApplication) getApplication())
                .getAppComponent()
                .injectInto(GameActivity.this);
        // listener to enable Next button
        correctAnswerListener = (() -> nextButtonManager.setEnabled(true));
        shuffledListListener = (shuffledList -> {
            // disable nextButton FAB
            nextButtonManager.setEnabled(false);
        });

        // when a GameBoardFragment is created, get its GameBoardManager and pass it to the NextButtonManager
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                if (f instanceof GameBoardFragment) {
                    // cast the fragment to GameBoardFragment type to access its methods
                    GameBoardFragment gameBoardFragment = (GameBoardFragment) (f);
                    // retrieve the GBM from the fragment
                    GameBoardManager gameBoardManager = gameBoardFragment.getGameBoardManager();
                    // pass listeners into GBM
                    gameBoardManager.setCorrectAnswerListener(correctAnswerListener);
                    gameBoardManager.setShuffledListListener(shuffledListListener);
                }
            }
        }, false);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        setupSharedPreferences();


        /* check FragmentManager and only create a new one if there isn't a GameBoardFragment
         *  i.e., upon rotation
         */
        GameBoardFragment gameBoardFragment;
        if (fragmentManager.findFragmentByTag(FRAGMENT_KEY_TAG) == null) {
            Log.d(TAG, "No fragment found. Creating new one.");
            gameBoardFragment = new GameBoardFragment();
            // Replace game board fragment placeholder with actual fragment (new or existing)
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.game_board_placeholder, gameBoardFragment, FRAGMENT_KEY_TAG);
            ft.commit();
        }
        Fragment f = fragmentManager.findFragmentById(R.id.game_board_placeholder);

        // prepare the nextButton FAB
        FloatingActionButton nextButton = findViewById(R.id.next_button);
        nextButtonManager.setFab(nextButton);
        nextButtonManager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction newFragment = fragmentManager.beginTransaction();
        Fragment completedGameBoardFragment = fragmentManager.findFragmentByTag(FRAGMENT_KEY_TAG);
        if (completedGameBoardFragment != null) {
            newFragment.remove(completedGameBoardFragment);
        }
        GameBoardFragment newGameBoardFragment = new GameBoardFragment();
        newFragment.replace(R.id.game_board_placeholder, newGameBoardFragment, FRAGMENT_KEY_TAG);
        newFragment.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
