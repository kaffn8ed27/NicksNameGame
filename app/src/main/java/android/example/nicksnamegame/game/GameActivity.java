package android.example.nicksnamegame.game;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.example.nicksnamegame.R;
import android.example.nicksnamegame.data.model.PersonConverter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = GameActivity.class.getSimpleName();

    // TODO - add "next group" functionality
    /* TODO - tracking: if answered right on the first try, remove coworker from the pool.
       If there are fewer than 6 names remaining, return "known" coworkers to pool
       Once all coworkers are "known", prompt player to start over
     */

    private RecyclerView people;
    private PhotoAdapter photoAdapter;
    private PeopleShuffler peopleShuffler;
    private PeopleShuffler.ShuffledList shuffledList;
    private TextView game_prompt_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        people = (RecyclerView) findViewById(R.id.rv_photos);

        int numberOfColumns = this.getResources().getInteger(R.integer.number_game_columns);
        GridLayoutManager photoManager = new GridLayoutManager(this, numberOfColumns);
        people.setLayoutManager(photoManager);
        people.setHasFixedSize(true);

        final int numberOfPhotos = this.getResources().getInteger(R.integer.number_game_photos);

            new PersonConverter().retrievePersonList(new PersonConverter.PersonListHandler() {
                @Override
                public void onReceivePersonList(List<Person> personList) {
                    if (personList != null) {
                        peopleShuffler = new PeopleShuffler(personList, numberOfPhotos);
                        shuffledList = peopleShuffler.chooseCoworkers();
                    } else {
                        Log.d(TAG, "List of co-workers not found");
                    }

                    if(shuffledList != null) {
                        photoAdapter = new PhotoAdapter(shuffledList, GameActivity.this);
                        people.setAdapter(photoAdapter);
                    }

                    // set the text of the game prompt
                    game_prompt_text_view = findViewById(R.id.game_prompt);
                    game_prompt_text_view.setText(setName());
                }
            });
    }

    private String setName() {
        // extract the correct name from the ShuffledList object and set the prompt text
        String namePrompt;
        if(shuffledList != null) {
            int index = shuffledList.getIndex();
            List<Person> peopleToChooseFrom = shuffledList.getPeople();
            String name = peopleToChooseFrom.get(index).getName();
            namePrompt = "Who is " + name + "?";
        } else {
            namePrompt = getString(R.string.generic_error);
        }
            return namePrompt;
    }
}
