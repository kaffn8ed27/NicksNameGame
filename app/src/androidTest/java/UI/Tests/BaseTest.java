package UI.Tests;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.example.nicksnamegame.launcher.MainActivity;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import UI.Screens.GameScreen;
import UI.Screens.HomeScreen;


@RunWith(AndroidJUnit4ClassRunner.class)
public class BaseTest {

    private GameScreen testGameScreen;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp () {

        testGameScreen = new HomeScreen().launchGame();

    }

    @Test
    public void testGameScreenRotation () {

        // check for some element on the screen and store it?
        rotateScreen();
        // ...then make sure that element exists again now that the activity has restarted?

    }

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
