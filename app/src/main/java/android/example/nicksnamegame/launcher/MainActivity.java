package android.example.nicksnamegame.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.nicksnamegame.R;
import android.example.nicksnamegame.game.GameActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button defaultGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defaultGameButton = findViewById(R.id.default_game_button);
        defaultGameButton.setOnClickListener(v -> {
            Intent startGameIntent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(startGameIntent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
