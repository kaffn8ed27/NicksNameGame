package android.example.nicksnamegame.game;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.example.nicksnamegame.R;
import android.example.nicksnamegame.data.model.PersonConverter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class GameActivity extends AppCompatActivity {

    private static final String TAG = GameActivity.class.getSimpleName();
    private static final String PHOTO_ADAPTER_KEY = "saved_photo_adapter";
    private static final String SHUFFLED_LIST_KEY = "saved_shuffled_list";
    private static final String PERSON_LIST_KEY = "saved_person_list";
    private static final String NEXT_BUTTON_MANAGER_KEY = "saved_next_button_manager";

    /* TODO - tracking: if answered right on the first try, remove coworker from the pool.
     *  If there aren't enough people left in the pool to fill the grid, allow "wrong" answers
     *  to come from the list of known people, but make sure they're never the "right" answer again
     *  until the pool is empty and the game is restarted
     */

    private static FloatingActionButton nextButton;
    private ProgressBar progressBar;
    private RecyclerView people;
    private TextView game_prompt_text_view;
    private ShuffledList shuffledList;
    private List<Person> personList;
    private PhotoAdapter photoAdapter;

    private CompositeDisposable disposables;

    @Inject
    NextButtonManager nextButtonManager;
    @Inject
    GameBoardManager gameBoardManager;

    public GameActivity() {
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useDarkTheme = sharedPreferences.getBoolean(getString(R.string.pref_dark_theme_key), getResources().getBoolean(R.bool.pref_dark_theme_default));
        int theme = useDarkTheme ? R.style.AppTheme_Dark_GameTheme : R.style.AppTheme_GameTheme;
        setTheme(theme);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setupSharedPreferences();
        setContentView(R.layout.activity_game);

        // inject dependencies
        ((GameApplication) getApplication())
                .getGameComponent()
                .inject(GameActivity.this);

        // initialize views
        people = findViewById(R.id.rv_photos);
        progressBar = findViewById(R.id.progress_bar);
        game_prompt_text_view = findViewById(R.id.game_prompt);

        // prepare the nextButton FAB
        nextButton = findViewById(R.id.next_button);
        nextButtonManager.setFab(nextButton);

        // TODO: move nextButton's onClick listener to NextButtonManager?
        nextButton.setOnClickListener(v -> generateGameGrid());

        int numberOfColumns = this.getResources().getInteger(R.integer.number_game_columns);
        GridLayoutManager photoManager = new GridLayoutManager(this, numberOfColumns);
        people.setLayoutManager(photoManager);
        people.setHasFixedSize(true);
        if (savedInstanceState == null) {
            // hide the recycler view while the game board loads
            people.setVisibility(View.INVISIBLE);
            // show the loading indicator
            progressBar.setVisibility(View.VISIBLE);
            generateGameGrid();
            // show the photos
            people.setVisibility(View.VISIBLE);
            // loading finished: hide the progress bar
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            Log.d(TAG, "Retrieving state: " + savedInstanceState);
            shuffledList = savedInstanceState.getParcelable(SHUFFLED_LIST_KEY);
            photoAdapter = savedInstanceState.getParcelable(PHOTO_ADAPTER_KEY);
            personList = savedInstanceState.getParcelableArrayList(PERSON_LIST_KEY);
            nextButtonManager = savedInstanceState.getParcelable(NEXT_BUTTON_MANAGER_KEY);
            people.setAdapter(photoAdapter);
            // loading finished: hide the progress bar
            progressBar.setVisibility(View.INVISIBLE);
            // show the photos
            people.setVisibility(View.VISIBLE);
            game_prompt_text_view.setText(gameBoardManager.setName());
            game_prompt_text_view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        // get rid of any Rx subscriptions
//        gameBoardManager.dispose();
        disposables.dispose();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PHOTO_ADAPTER_KEY, photoAdapter);
        outState.putParcelable(SHUFFLED_LIST_KEY, shuffledList);
        outState.putParcelableArrayList(PERSON_LIST_KEY, new ArrayList<>(personList));
        outState.putParcelable(NEXT_BUTTON_MANAGER_KEY, nextButtonManager);
        Log.d(TAG, "SAVING...");
    }

    protected void generateGameGrid() {

        disposables = new CompositeDisposable();

        if (personList == null) {
            Disposable personListSubscription = new PersonConverter().retrievePersonList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(personList -> {
                        Log.d(TAG, "Retrieved new list from API");
                        this.personList = personList;

                        photoAdapter = gameBoardManager.generateGameBoard(personList);
                        // set the text of the game prompt
                        game_prompt_text_view.setText(gameBoardManager.setName());

                        people.setAdapter(photoAdapter);
                        // show the game prompt
                        game_prompt_text_view.setVisibility(View.VISIBLE);
                    }, throwable -> game_prompt_text_view.setText(R.string.generic_error));

            // set up the personList subscription to be disposed of when the activity is destroyed
            disposables.add(personListSubscription);

        } else {
            Log.d(TAG, "Using saved list");
            photoAdapter = gameBoardManager.generateGameBoard(personList);
            people.setAdapter(photoAdapter);
            // set the text of the game prompt
            game_prompt_text_view.setText(gameBoardManager.setName());
            // show the game prompt
            game_prompt_text_view.setVisibility(View.VISIBLE);
            // show the photos
            people.setVisibility(View.VISIBLE);
            // loading finished: hide the progress bar
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
