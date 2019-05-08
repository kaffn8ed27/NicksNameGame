package android.example.nicksnamegame.game;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class GameBoardManager {

    private static final String TAG = GameBoardManager.class.getSimpleName();

    final PeopleShuffler peopleShuffler;
    final PhotoAdapter photoAdapter;
    final GameState gameState;

    private List<Person> personList;
    private List<NameListener> listeners;

    @Inject
    GameBoardManager(PeopleShuffler peopleShuffler, PhotoAdapter photoAdapter, GameState gameState) {
        this.peopleShuffler = peopleShuffler;
        this.photoAdapter = photoAdapter;
        this.gameState = gameState;
        this.listeners = new ArrayList<>();
    }

    void generateGameBoard() {
        // reset tracking of photos that have been clicked
        gameState.clearClickedIds();
        // grab a new set of people to play on
        gameState.setShuffledList(peopleShuffler.chooseCoworkers(personList));
        // set up the adapter with the new list
        photoAdapter.setData(gameState.getShuffledList());
        for (NameListener listener : listeners)
        listener.onListLoaded(createNamePrompt());
    }

    void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    void setNameListener(NameListener listener) {
        this.listeners.add(listener);
    }

    void unsetNameListener (NameListener listener) {
        listeners.remove(listener);
    }

    String createNamePrompt() {
        // extract the correct name from the ShuffledList object and set the prompt text
        if (gameState.getShuffledList() != null) {
            int index = gameState.getShuffledList().getCorrectAnswerIndex();
            List<Person> peopleOnGameBoard = gameState.getShuffledList().getPeople();
            return ("Who is " + peopleOnGameBoard.get(index).getName() + "?");
        } else return null;
    }

    public interface NameListener {
        void onListLoaded(String correctAnswerPrompt);
    }
}
