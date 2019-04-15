package android.example.nicksnamegame.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.example.nicksnamegame.R;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity{

    private RecyclerView people;
    private PhotoAdapter photoAdapter;
    private List<Person> peopleToChooseFrom = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        people = (RecyclerView) findViewById(R.id.rv_photos);

        LinearLayoutManager photoManager = new LinearLayoutManager(this);
        people.setLayoutManager(photoManager);
        people.setHasFixedSize(true);

        Person john = new Person(this.getString(R.string.John), R.drawable.john);
        Person paul = new Person(this.getString(R.string.Paul), R.drawable.paul);
        Person george = new Person(this.getString(R.string.George), R.drawable.george);
        Person ringo = new Person(this.getString(R.string.Ringo), R.drawable.ringo);
        Person yoko = new Person(this.getString(R.string.Yoko), R.drawable.yoko);

        peopleToChooseFrom.add(john);
        peopleToChooseFrom.add(paul);
        peopleToChooseFrom.add(george);
        peopleToChooseFrom.add(ringo);
        peopleToChooseFrom.add(yoko);

        photoAdapter = new PhotoAdapter(peopleToChooseFrom);
        people.setAdapter(photoAdapter);
    }
}

