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

    // receives the list returned from the API
    void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    // the actions to be taken when the game is opened, and when the "next" button is clicked
    void generateGameBoard() {
        // reset tracking of photos that have been clicked
        clearClickedIds();
        // grab a new set of people for the game board
        shuffledList = peopleShuffler.chooseCoworkers(personList);
        // set up the adapter with the new list
        for (ShuffledListListener listener : listeners)
            listener.onNewShuffledList(shuffledList);
    }

    /* Returns the current shuffled list of people so that it can be used by other classes
     * e.g. to set the photos in the adapter, get the current correct answer so the ViewHolder knows
     * what foreground color to set, etc.
     */

    ShuffledList getShuffledList() {
        return this.shuffledList;
    }

    // for other classes to register a listener for the creation of a new ShuffledList
    void setShuffledListListener(ShuffledListListener listener) {
        this.listeners.add(listener);
        if (shuffledList != null) listener.onNewShuffledList(shuffledList);
    }

    // for other classes to unregister their listener - e.g. in GameActivity's onDestroy
    void unsetShuffledListListener(ShuffledListListener listener) {
        listeners.remove(listener);
    }

    /* GameState management functions
     *
     * Mostly just a pass-through to GameState functions, but gathering them all here eliminates the
     * need to inject GameState and GameBoardManager into every class that needs access to the
     * GameState. Instead, inject GameBoardManager and manipulate GameState via these functions.
     */

    public boolean getCorrectAnswerClicked() {
        return gameState.getCorrectAnswerClicked();
    }

    public void setCorrectAnswerClicked(boolean correctAnswerClicked) {
        gameState.setCorrectAnswerClicked(correctAnswerClicked);
    }

    void registerNewClickedPerson(String id) {
        gameState.registerNewClickedPerson(id);
    }

    void clearClickedIds() {
        gameState.clearClickedIds();
    }

    List<String> getClickedIds() {
        return gameState.getClickedIds();
    }

    GameState getGameState() {
        return this.gameState;
    }
}

