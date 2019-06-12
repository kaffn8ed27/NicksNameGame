package android.example.nicksnamegame.launcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.nicksnamegame.R;
import android.example.nicksnamegame.game.game_board.game_controller.GameActivity;
import android.example.nicksnamegame.limit_game.game_board.LimitGameActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean useDarkTheme;

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        useDarkTheme = sharedPreferences.getBoolean(getString(R.string.pref_dark_theme_key), getResources().getBoolean(R.bool.pref_dark_theme_default));
        int theme = useDarkTheme ? R.style.AppTheme_Dark : R.style.AppTheme;
        setTheme(theme);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_dark_theme_key))) {
            useDarkTheme = sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.pref_dark_theme_default));
            Log.d(TAG, "Theme preference changed from " + (useDarkTheme ? "light to dark" : "dark to light"));
            recreate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupSharedPreferences();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button defaultGameButton = findViewById(R.id.default_game_button);
        defaultGameButton.setOnClickListener(v -> {
            Intent startGameIntent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(startGameIntent);
        });
        Button limitGameButton = findViewById(R.id.limit_game_button);
        limitGameButton.setOnClickListener(v -> {
            Intent startLimitGameIntent = new Intent(MainActivity.this, LimitGameActivity.class);
            startActivity(startLimitGameIntent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
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
