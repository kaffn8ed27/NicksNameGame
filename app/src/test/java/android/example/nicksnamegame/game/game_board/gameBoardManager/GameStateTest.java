package android.example.nicksnamegame.game.game_board.gameBoardManager;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameStateTest {

    private GameState gameState;

    @Before
    public void setUp() {
        gameState = new GameState();
    }

    @Test
    public void registerNewClickedPerson() {

        String testId1 = "ID1 for test";
        String testId2 = "ID2 for test";

        gameState.registerNewClickedPerson(testId1);
        gameState.registerNewClickedPerson(testId2);

        List<String> testList = gameState.getClickedIds();

        assertTrue("Clicked IDs list should contain " + testId1, testList.contains(testId1));
        assertTrue("Clicked IDs list should contain " + testId2, testList.contains(testId2));

    }
}