package android.example.nicksnamegame.game.game_board.gameBoardManager;

import android.example.nicksnamegame.data.db.Person;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameBoardManagerTest {

    private static final String TAG = GameBoardManagerTest.class.getSimpleName();
    private GameBoardManager testGameBoardManager;
    private GameState mockGameState;
    private List<Person> mockPersonList;
    private ShuffledListListener mockListener;

    @Before
    public void setUp() {

        // mock dependencies
        mockGameState = mock(GameState.class);
        Person mockPerson = mock(Person.class);
        mockPersonList = new ArrayList<>();
        mockPersonList.add(mockPerson);
        PeopleShuffler mockPeopleShuffler = mock(PeopleShuffler.class);
        when(mockPeopleShuffler.chooseCoworkers(mockPersonList)).thenReturn(mockPersonList);
        mockListener = mock(ShuffledListListener.class);

        // set up the GBM
        testGameBoardManager = new GameBoardManager(mockPeopleShuffler, mockGameState);
        testGameBoardManager.setPersonList(mockPersonList);
        testGameBoardManager.setShuffledListListener(mockListener);

    }

    @Test
    public void testShuffledListListenerCall() {

        testGameBoardManager.generateGameBoard();
        verify(mockListener).onNewShuffledList(mockPersonList);

    }

    @Test
    public void testGameStateClearClickedIds() {

        testGameBoardManager.generateGameBoard();
        verify(mockGameState).clearClickedIds();

    }

    @Test
    public void testGameStateReceivedValidIndex() {

        Person mockPerson1 = mock(Person.class);
        Person mockPerson2 = mock(Person.class);
        mockPersonList.add(mockPerson1);
        mockPersonList.add(mockPerson2);
        testGameBoardManager.generateGameBoard();
        verify(mockGameState).setCorrectAnswerIndex(intThat(index ->
                index >= 0 && index < mockPersonList.size()));

    }
}