package android.example.nicksnamegame.game.game_board.game_controller;

import android.content.SharedPreferences;
import android.example.nicksnamegame.R;
import android.example.nicksnamegame.data.PersonRepo;
import android.example.nicksnamegame.data.db.Person;
import android.example.nicksnamegame.game.dagger.GameApplication;
import android.example.nicksnamegame.game.game_board.NextButtonManager;
import android.example.nicksnamegame.game.game_board.PhotoAdapter;
import android.example.nicksnamegame.game.game_board.gameBoardManager.GameBoardManager;
import android.example.nicksnamegame.game.game_board.gameBoardManager.ShuffledListListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class GameActivity extends AppCompatActivity {

    private static final String TAG = GameActivity.class.getSimpleName();

    /* TODO - tracking: if answered right on the first try, remove coworker from the pool.
     *  If there aren't enough people left in the pool to fill the grid, allow "wrong" answers
     *  to come from the list of known people, but make sure they're never the "right" answer again
     *  until the pool is empty and the game is restarted
     */

    private ProgressBar progressBar; // move to Fragment
    private RecyclerView people; // move to Fragment
    private TextView gamePromptTextView; // move to Fragment
    private ShuffledListListener namePromptListener; // maybe move to Fragment

    private CompositeDisposable disposables;

    @Inject
    PhotoAdapter photoAdapter; // move to Fragment
    @Inject
    NextButtonManager nextButtonManager;
    @Inject
    GameBoardManager gameBoardManager;
    @Inject
    PersonRepo personRepo;

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

        // Replace game board fragment placeholder with actual fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.game_board_placeholder, new GameBoardFragment());
        ft.commit();

        // inject dependencies
        ((GameApplication) getApplication())
                .getGameComponent()
                .injectInto(GameActivity.this);

        // initialize views
        people = findViewById(R.id.rv_photos);
        progressBar = findViewById(R.id.progress_bar);
        gamePromptTextView = findViewById(R.id.game_prompt);

        // hide the game board while everything else loads
        setGameVisibility(false);

        // prepare the nextButton FAB
        FloatingActionButton nextButton = findViewById(R.id.next_button);
        nextButtonManager.setFab(nextButton);

        namePromptListener = (shuffledList -> {
            // disable nextButton FAB
            nextButtonManager.setEnabled(false);
            // hide the game board while everything else loads
            setGameVisibility(false);
            if (shuffledList != null) {
                gamePromptTextView.setText(createNamePrompt(shuffledList, gameBoardManager.getCorrectAnswerIndex()));
            } else {
                gamePromptTextView.setText(R.string.generic_error);
            }
            // show the game board
            setGameVisibility(true);
        });
        gameBoardManager.setShuffledListListener(namePromptListener);

        int numberOfColumns = this.getResources().getInteger(R.integer.number_game_columns);
        GridLayoutManager photoManager = new GridLayoutManager(this, numberOfColumns);
        people.setLayoutManager(photoManager);
        people.setHasFixedSize(true);
        people.setAdapter(photoAdapter);


        if (savedInstanceState == null) {

            // make the network call to retrieve the list of people
            Disposable personListSubscription = personRepo.retrievePersonList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(personList -> {
                        Log.d(TAG, "Retrieved new list from repository: " + personList);
                        gameBoardManager.setPersonList(personList);
                        gameBoardManager.generateGameBoard();
                        // show the game board now that everything has loaded
                        setGameVisibility(true);
                    }, throwable -> {
                        gamePromptTextView.setText(R.string.generic_error);
                        setGameVisibility(true);
                    });

            // set up the personList subscription to be disposed of when the activity is destroyed
            disposables = new CompositeDisposable();
            disposables.add(personListSubscription);
        } else setGameVisibility(true); // if re-creating (i.e. rotating), just show the game board
    }

    void setGameVisibility(boolean makeVisible) {
        if (makeVisible) {
            // hide progress bar
            progressBar.setVisibility(View.GONE);
            // show photos and prompt text
            people.setVisibility(View.VISIBLE);
            gamePromptTextView.setVisibility(View.VISIBLE);
        } else {
            // hide photos and prompt text
            people.setVisibility(View.GONE);
            gamePromptTextView.setVisibility(View.GONE);
            // show progress bar while board loads
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        if (disposables != null) disposables.dispose();
        gameBoardManager.unsetShuffledListListener(namePromptListener);
        super.onDestroy();
    }

    String createNamePrompt(List<Person> shuffledList, int index) {
        String namePrompt;
        String name = shuffledList.get(index).getName();
        namePrompt = "Who is " + name + "?";

        return namePrompt;
    }

}
