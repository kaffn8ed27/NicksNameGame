package android.example.nicksnamegame.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.nicksnamegame.R;
import android.example.nicksnamegame.game.GameActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button defaultGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defaultGameButton = findViewById(R.id.default_game_button);
        defaultGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(startGameIntent);
            }
        });
    }
}
