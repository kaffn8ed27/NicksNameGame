package android.example.nicksnamegame.game;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.example.nicksnamegame.R;
import android.example.nicksnamegame.data.model.PersonConverter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = GameActivity.class.getSimpleName();
    private static final String PHOTO_ADAPTER_KEY = "saved_photo_adapter";
    private static final String SHUFFLED_LIST_KEY = "saved_shuffled_list";
    private static final String PEOPLE_SHUFFLER_KEY = "saved_people_shuffler";

    // TODO - add "next group" functionality

    /* TODO - tracking: if answered right on the first try, remove coworker from the pool.
     *  If there aren't enough people left in the pool to fill the grid, allow "wrong" answers
     *  to come from the list of known people, but make sure they're never the "right" answer again
     *  until the pool is empty and the game is restarted
     */

    private static FloatingActionButton nextButton;
    private ProgressBar progressBar;
    private RecyclerView people;
    private PhotoAdapter photoAdapter;
    private PeopleShuffler peopleShuffler;
    private ShuffledList shuffledList;
    private TextView game_prompt_text_view;

    public static CopyOnWriteArrayList<Person> personList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        people = (RecyclerView) findViewById(R.id.rv_photos);
        progressBar = findViewById(R.id.progress_bar);

        // prepare the nextButton FAB
        nextButton = findViewById(R.id.next_button);
        nextButton.hide();
        nextButton.setOnClickListener(v -> generateGameGrid());

        int numberOfColumns = this.getResources().getInteger(R.integer.number_game_columns);
        GridLayoutManager photoManager = new GridLayoutManager(this, numberOfColumns);
        people.setLayoutManager(photoManager);
        people.setHasFixedSize(true);
        if (savedInstanceState == null) {
            generateGameGrid();
        } else {
            Log.d(TAG, "Retrieving state: " + savedInstanceState);
            shuffledList = savedInstanceState.getParcelable(SHUFFLED_LIST_KEY);
            photoAdapter = savedInstanceState.getParcelable(PHOTO_ADAPTER_KEY);
            peopleShuffler = savedInstanceState.getParcelable(PEOPLE_SHUFFLER_KEY);
            people.setAdapter(photoAdapter);
            game_prompt_text_view = findViewById(R.id.game_prompt);
            game_prompt_text_view.setText(setName());
            game_prompt_text_view.setVisibility(View.VISIBLE);
            // loading finished: hide the progress bar
            progressBar.setVisibility(View.INVISIBLE);
            // show the photos
            people.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PHOTO_ADAPTER_KEY, photoAdapter);
        outState.putParcelable(SHUFFLED_LIST_KEY, shuffledList);
        outState.putParcelable(PEOPLE_SHUFFLER_KEY, peopleShuffler);
        Log.d(TAG, "SAVING...");
    }

    protected void generateGameGrid() {

        nextButton.hide();

        // hide the recycler view while the game board loads
        people.setVisibility(View.INVISIBLE);

        // show the loading indicator
        progressBar.setVisibility(View.VISIBLE);

        final int numberOfPhotos = this.getResources().getInteger(R.integer.number_game_photos);

        if (peopleShuffler == null) {
            new PersonConverter().retrievePersonList(new PersonConverter.PersonListHandler() {
                @Override
                public void onReceivePersonList(CopyOnWriteArrayList<Person> personList) {
                    Log.d(TAG, "Retrieving new list from API");

                    // generate a new list of co-workers to play the game on
                    if (personList == null) {
                        Log.d(TAG, "List of co-workers not found");
                    } else {
                        // create a peopleShuffler to clean & randomize the list
                        peopleShuffler = new PeopleShuffler(personList, numberOfPhotos);
                        shuffledList = peopleShuffler.chooseCoworkers();
                    }

                    if (shuffledList == null) {
                        Log.d(TAG, "Failed to load shuffled list");
                    } else {
                        // pass the list of people to the adapter
                        photoAdapter = new PhotoAdapter(shuffledList, GameActivity.this);
                        people.setAdapter(photoAdapter);
                    }

                    if (photoAdapter == null) {
                        Log.d(TAG, "Failed to load photo adapter");
                    } else {
                        // set the text of the game prompt
                        game_prompt_text_view = findViewById(R.id.game_prompt);
                        game_prompt_text_view.setText(setName());
                        // show the game prompt
                        game_prompt_text_view.setVisibility(View.VISIBLE);
                        // loading finished: hide the progress bar
                        progressBar.setVisibility(View.INVISIBLE);
                        // show the photos
                        people.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else {
            Log.d(TAG, "Using saved list");
            shuffledList = peopleShuffler.chooseCoworkers();

            if (shuffledList == null) {
                Log.d(TAG, "Failed to load shuffled list");
            } else {
                // pass the list of people to the adapter
                photoAdapter = new PhotoAdapter(shuffledList, GameActivity.this);
                people.setAdapter(photoAdapter);
            }

            if (photoAdapter == null) {
                Log.d(TAG, "Failed to load photo adapter");
            } else {
                // set the text of the game prompt
                game_prompt_text_view = findViewById(R.id.game_prompt);
                game_prompt_text_view.setText(setName());
                // show the game prompt
                game_prompt_text_view.setVisibility(View.VISIBLE);
                // loading finished: hide the progress bar
                progressBar.setVisibility(View.INVISIBLE);
                // show the photos
                people.setVisibility(View.VISIBLE);
            }
        }
    }

    private String setName() {
        // extract the correct name from the ShuffledList object and set the prompt text
        String namePrompt;
        if (shuffledList != null) {
            int index = shuffledList.getIndex();
            List<Person> peopleToChooseFrom = shuffledList.getPeople();
            String name = peopleToChooseFrom.get(index).getName();
            namePrompt = "Who is " + name + "?";
        } else {
            namePrompt = getString(R.string.generic_error);
        }
        return namePrompt;
    }

    public static void onCorrectAnswerClicked() {
        nextButton.show();
    }
}
