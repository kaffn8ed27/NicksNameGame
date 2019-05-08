package UI.Tests;

import android.example.nicksnamegame.launcher.MainActivity;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import UI.Screens.HomeScreen;


@RunWith(AndroidJUnit4ClassRunner.class)
public class BaseTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Test
    public void testUserCanNavigateToGameScreen () throws Exception {
        new HomeScreen().launchGame();
        Thread.sleep(5000);
    }
}
