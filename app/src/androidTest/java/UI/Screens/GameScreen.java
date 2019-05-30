package UI.Screens;

import android.example.nicksnamegame.R;

import androidx.test.espresso.ViewInteraction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class GameScreen {

    private ViewInteraction photoRecyclerView = onView(withId(R.id.rv_photos));
    private ViewInteraction gamePromptTextView = onView(withId(R.id.game_prompt));
    private ViewInteraction nextButton = onView(withId(R.id.next_button));


    public ViewInteraction getPhotoRecyclerView() {
        return photoRecyclerView;
    }

    public ViewInteraction getGamePromptTextView() {
        return gamePromptTextView;
    }

    public ViewInteraction getNextButton() {
        return nextButton;
    }


    public GameScreen clickNextButton () {

        nextButton.perform(click());
        return new GameScreen();

    }

}
