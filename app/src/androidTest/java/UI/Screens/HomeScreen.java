package UI.Screens;

import android.example.nicksnamegame.R;

import androidx.test.espresso.ViewInteraction;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


public class HomeScreen {

    private ViewInteraction launchGameButton = onView(withId(R.id.default_game_button));

    public GameScreen launchGame () {

        launchGameButton.perform(click());
        return new GameScreen();
    }

}
