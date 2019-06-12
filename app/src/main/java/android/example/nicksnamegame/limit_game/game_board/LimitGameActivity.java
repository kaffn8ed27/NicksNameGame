package android.example.nicksnamegame.limit_game.game_board;

import android.content.SharedPreferences;
import android.example.nicksnamegame.R;
import android.example.nicksnamegame.game.dagger.GameApplication;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class LimitGameActivity extends AppCompatActivity {

    private static final String TAG = LimitGameActivity.class.getSimpleName();

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useDarkTheme = sharedPreferences.getBoolean(getString(R.string.pref_dark_theme_key), getResources().getBoolean(R.bool.pref_dark_theme_default));
        int theme = useDarkTheme ? R.style.AppTheme_Dark_GameTheme : R.style.AppTheme_GameTheme;
        setTheme(theme);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit_game);
        setupSharedPreferences();

        // Instantiate a ViewPager and a PagerAdapter
        ViewPager gameBoardPager = findViewById(R.id.limit_game_pager);
        PagerAdapter pagerAdapter = new LimitGameBoardAdapter(getSupportFragmentManager());
        gameBoardPager.setAdapter(pagerAdapter);

        // inject dependencies
        ((GameApplication) getApplication())
                .getAppComponent()
                .injectInto(LimitGameActivity.this);
    }
}
