package android.example.nicksnamegame.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.example.nicksnamegame.R;
import android.example.nicksnamegame.data.model.PersonConverter;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    private static final boolean SHOULD_USE_LIVE_DATA = false; // cannot be set to true until Picasso is implemented

    private List<Person> personList;

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

        GridLayoutManager photoManager = new GridLayoutManager(this, 2);
        people.setLayoutManager(photoManager);
        people.setHasFixedSize(true);


        if (SHOULD_USE_LIVE_DATA) {
            personList = new PersonConverter().retrievePersonList();
            peopleShuffler = new PeopleShuffler(personList);
            peopleShuffler.chooseCoworkers();
            // TODO: use Picasso to set the image views
        } else {
            peopleShuffler = new PeopleShuffler();
            // create a shuffled list of people: Beatles demo version
            shuffledList = peopleShuffler.createShuffledList(this);
        }

        // pass the list of people to the adapter
        photoAdapter = new PhotoAdapter(shuffledList);
        people.setAdapter(photoAdapter);

        // set the text of the game prompt
        game_prompt_text_view = findViewById(R.id.game_prompt);
        game_prompt_text_view.setText(setName());
    }

    private String setName() {
        // extract the correct name from the ShuffledList object and set the prompt text
        int index = shuffledList.getIndex();
        List<Person> peopleToChooseFrom = shuffledList.getPeople();
        String name = peopleToChooseFrom.get(index).getName();
        String namePrompt = "Who is " + name + "?";
        return namePrompt;
    }
}
