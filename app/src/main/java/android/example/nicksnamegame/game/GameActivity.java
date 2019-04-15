package android.example.nicksnamegame.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.example.nicksnamegame.R;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GameActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final int NUM_COWORKERS = 500;

    private RecyclerView photosList;
    private PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        photosList = (RecyclerView) findViewById(R.id.rv_photos);

        LinearLayoutManager photoManager = new LinearLayoutManager(this);
        photosList.setLayoutManager(photoManager);
        photosList.setHasFixedSize(true);
    }

    @Override
    public void onClick(View v) {

    }
}

