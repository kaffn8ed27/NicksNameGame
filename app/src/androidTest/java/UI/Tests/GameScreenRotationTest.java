package UI.Tests;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.example.nicksnamegame.launcher.MainActivity;

import androidx.test.espresso.ViewInteraction;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import UI.Screens.GameScreen;
import UI.Screens.HomeScreen;


@RunWith(AndroidJUnit4ClassRunner.class)
public class GameScreenRotationTest {

    private static final String TAG = GameScreenRotationTest.class.getSimpleName();
    private GameScreen testGameScreen;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp () {

        testGameScreen = new HomeScreen().launchGame();

    }

    @Test
    public void testGamePromptDoesNotChangeOnScreenRotation () {

        // check for some element on the screen and store it?
        ViewInteraction gamePromptTextView = testGameScreen.getGamePromptTextView();
        rotateScreen();
        // ...then make sure that element exists again now that the activity has restarted?
        assertEquals("Game prompt should not change upon rotation",
                gamePromptTextView,
                testGameScreen.getGamePromptTextView());

    }

    @Test
    public void testRecyclerViewDoesNotChangeOnScreenRotation () {

        ViewInteraction photoRecyclerView = testGameScreen.getPhotoRecyclerView();
        rotateScreen();
        assertEquals("Recycler view should not change upon rotation",
                photoRecyclerView,
                testGameScreen.getPhotoRecyclerView());

    }


    // helper function to perform a screen rotation
    private void rotateScreen () {

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        int orientation = context.getResources().getConfiguration().orientation;
        Activity activity = activityTestRule.getActivity();
        activity.setRequestedOrientation(
                orientation == Configuration.ORIENTATION_PORTRAIT ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        );
    }

}
