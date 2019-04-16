package android.example.nicksnamegame.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.example.nicksnamegame.R;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class GameActivity extends AppCompatActivity {

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

        LinearLayoutManager photoManager = new LinearLayoutManager(this);
        people.setLayoutManager(photoManager);
        people.setHasFixedSize(true);

        // create a shuffled list of people
        peopleShuffler = new PeopleShuffler();
        shuffledList = peopleShuffler.createShuffledList(this);

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
