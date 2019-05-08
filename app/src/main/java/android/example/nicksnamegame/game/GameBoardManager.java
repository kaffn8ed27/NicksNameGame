package android.example.nicksnamegame.game;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class GameBoardManager {

    private static final String TAG = GameBoardManager.class.getSimpleName();

    final PeopleShuffler peopleShuffler;
    final GameState gameState;

    private ShuffledList shuffledList;
    private List<Person> personList;
    private List<ShuffledListListener> listeners;

    @Inject
    GameBoardManager(PeopleShuffler peopleShuffler, GameState gameState) {
        this.peopleShuffler = peopleShuffler;
        this.gameState = gameState;
        this.listeners = new ArrayList<>();
    }

    void generateGameBoard() {
        // reset tracking of photos that have been clicked
        gameState.clearClickedIds();
        // grab a new set of people to play on
        gameState.setShuffledList(peopleShuffler.chooseCoworkers(personList));
        shuffledList = gameState.getShuffledList();
        // set up the adapter with the new list
        for (ShuffledListListener listener : listeners)
            listener.onNewShuffledList(shuffledList);
    }

    void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    void setShuffledListListener(ShuffledListListener listener) {
        this.listeners.add(listener);
        if (shuffledList != null) listener.onNewShuffledList(shuffledList);
    }

    void unsetShuffledListListener(ShuffledListListener listener) {
        listeners.remove(listener);
    }
}

