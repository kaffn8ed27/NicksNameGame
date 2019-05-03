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

    /* TODO - tracking: if answered right on the first try, remove coworker from the pool.
     *  If there aren't enough people left in the pool to fill the grid, allow "wrong" answers
     *  to come from the list of known people, but make sure they're never the "right" answer again
     *  until the pool is empty and the game is restarted
     */

    private static FloatingActionButton nextButton;
    private ProgressBar progressBar;
    private RecyclerView people;
    private TextView game_prompt_text_view;

    // TODO: turn PhotoAdapter, PeopleShuffler, ShuffledList into Dagger injections?

    private PhotoAdapter photoAdapter;
    @Inject PeopleShuffler peopleShuffler;
    private ShuffledList shuffledList;
    private List<Person> personList;

    private CompositeDisposable disposables = new CompositeDisposable();

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

        // initialize views
        people = findViewById(R.id.rv_photos);
        progressBar = findViewById(R.id.progress_bar);
        game_prompt_text_view = findViewById(R.id.game_prompt);

        // prepare the nextButton FAB
        nextButton = findViewById(R.id.next_button);
        nextButton.hide();
        nextButton.setOnClickListener(v -> generateGameGrid());

        int numberOfColumns = this.getResources().getInteger(R.integer.number_game_columns);
        GridLayoutManager photoManager = new GridLayoutManager(this, numberOfColumns);
        people.setLayoutManager(photoManager);
        people.setHasFixedSize(true);
        ((GameApplication) getApplication())
                .getGameComponent()
                .inject(GameActivity.this);
        if (savedInstanceState == null) {
            generateGameGrid();
        } else {
            Log.d(TAG, "Retrieving state: " + savedInstanceState);
            shuffledList = savedInstanceState.getParcelable(SHUFFLED_LIST_KEY);
            photoAdapter = savedInstanceState.getParcelable(PHOTO_ADAPTER_KEY);
            personList = savedInstanceState.getParcelableArrayList(PERSON_LIST_KEY);
            people.setAdapter(photoAdapter);
            // loading finished: hide the progress bar
            progressBar.setVisibility(View.INVISIBLE);
            // show the photos
            people.setVisibility(View.VISIBLE);
        }
        game_prompt_text_view.setText(setName());
        game_prompt_text_view.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PHOTO_ADAPTER_KEY, photoAdapter);
        outState.putParcelable(SHUFFLED_LIST_KEY, shuffledList);
        outState.putParcelableArrayList(PERSON_LIST_KEY, new ArrayList<>(personList));
        Log.d(TAG, "SAVING...");
    }

    protected void generateGameGrid() {

        nextButton.setClickable(false);
        nextButton.hide();

        // hide the recycler view while the game board loads
        people.setVisibility(View.INVISIBLE);

        // show the loading indicator
        progressBar.setVisibility(View.VISIBLE);

        final int numberOfPhotos = this.getResources().getInteger(R.integer.number_game_photos);

        if (personList == null) {
            Disposable personListSubscription = new PersonConverter().retrievePersonList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(personList -> {
                        Log.d(TAG, "Retrieved new list from API");

                        // generate a new list of co-workers to play the game on
                        if (personList == null) {
                            Log.d(TAG, "List of co-workers not found");
                        } else {
                            this.personList = personList;
                            // create a peopleShuffler to randomize the list
                            shuffledList = peopleShuffler.chooseCoworkers(personList);
                        }

                        if (shuffledList == null) {
                            Log.d(TAG, "Failed to load shuffled list");
                        } else {
                            // pass the list of people to the adapter
                            photoAdapter = new PhotoAdapter(shuffledList, GameActivity.this);
                            people.setAdapter(photoAdapter);

                            if (photoAdapter == null) {
                                Log.d(TAG, "Failed to load photo adapter");
                            } else {
                                // set the text of the game prompt
                                game_prompt_text_view.setText(setName());
                                // show the game prompt
                                game_prompt_text_view.setVisibility(View.VISIBLE);
                                // show the photos
                                people.setVisibility(View.VISIBLE);
                            }
                        }
                    }, throwable -> game_prompt_text_view.setText(R.string.generic_error));

            disposables.add(personListSubscription);
        } else {
            Log.d(TAG, "Using saved list");
            shuffledList = peopleShuffler.chooseCoworkers(personList);

            if (shuffledList == null) {
                Log.d(TAG, "Failed to load shuffled list");
            } else {
                // pass the list of people to the adapter
                photoAdapter = new PhotoAdapter(shuffledList, GameActivity.this);
                people.setAdapter(photoAdapter);

                if (photoAdapter == null) {
                    Log.d(TAG, "Failed to load photo adapter");
                } else {
                    game_prompt_text_view.setText(setName());
                    // show the game prompt
                    game_prompt_text_view.setVisibility(View.VISIBLE);
                    // show the photos
                    people.setVisibility(View.VISIBLE);
                }
            }
        }
        // loading finished: hide the progress bar
        progressBar.setVisibility(View.INVISIBLE);
    }

    private String setName() {
        // extract the correct name from the ShuffledList object and set the prompt text
        String namePrompt;
        if (shuffledList != null) {
            int index = shuffledList.getCorrectAnswerIndex();
            List<Person> peopleToChooseFrom = shuffledList.getPeople();
            String name = peopleToChooseFrom.get(index).getName();
            namePrompt = "Who is " + name + "?";
        } else {
            namePrompt = "";
        }
        return namePrompt;
    }

    public static void onCorrectAnswerClicked() {
        nextButton.show();
        nextButton.setClickable(true);
    }
}
