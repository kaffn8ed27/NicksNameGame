package android.example.nicksnamegame.game.game_board.gameBoardManager;

import android.example.nicksnamegame.data.db.Person;
import android.example.nicksnamegame.game.dagger.GameBoardScope;
import android.example.nicksnamegame.game.game_board.game_controller.CorrectAnswerListener;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


@GameBoardScope
public class GameBoardManager {

    private static final String TAG = GameBoardManager.class.getSimpleName();

    private final PeopleShuffler peopleShuffler;
    private final GameState gameState;

    private List<ShuffledListListener> shuffledListListeners;
    private List<CorrectAnswerListener> correctAnswerListeners;

    @Inject
    public GameBoardManager(PeopleShuffler peopleShuffler, GameState gameState) {
        this.peopleShuffler = peopleShuffler;
        this.gameState = gameState;
        this.shuffledListListeners = new ArrayList<>();
        this.correctAnswerListeners = new ArrayList<>();
    }

    // the actions to be taken when the game is opened, and when the "next" button is clicked
    public void generateGameBoard(List<Person> personList) {
        Log.d(TAG, "Generating new game board");
        // reset tracking of photos that have been clicked
        gameState.clearClickedIds();
        // grab a new set of people for the game board
        List<Person> shuffledList = peopleShuffler.chooseCoworkers(personList);
        gameState.setShuffledList(shuffledList);
        // Choose a person to be the correct answer
        gameState.setCorrectAnswerIndex((int) (Math.random() * shuffledList.size()));
        onNewShuffledList(shuffledList);
    }

    private void onNewShuffledList(List<Person> shuffledList) {
        // set up the adapter with the new list
        for (ShuffledListListener listener : shuffledListListeners)
            listener.onNewShuffledList(shuffledList);
    }

    /* For other classes to register a listener for the creation of a new shuffled list
     * Those classes can then dictate their own behavior when the new list is generated
     */

    public void setShuffledListListener(ShuffledListListener listener) {
        this.shuffledListListeners.add(listener);
        List<Person> shuffledList = gameState.getShuffledList();
        if (shuffledList != null) listener.onNewShuffledList(shuffledList);
    }

    public void setCorrectAnswerListener(CorrectAnswerListener listener) {
        this.correctAnswerListeners.add(listener);
        if(this.getCorrectAnswerClicked()) listener.onCorrectAnswerClicked();
    }

    // for other classes to unregister their listener - e.g. in GameActivity's onDestroy
    public void unsetShuffledListListener(ShuffledListListener listener) {
        shuffledListListeners.remove(listener);
    }

    /* GameState management
     *
     * Mostly just pass-throughs to GameState methods, but gathering them all here eliminates the
     * need to inject GameState and GameBoardManager into every class that needs access to the
     * GameState. Instead, inject GameBoardManager and access GameState via these methods.
     */

    public boolean getCorrectAnswerClicked() {
        return gameState.getCorrectAnswerClicked();
    }

    public void setCorrectAnswerClicked(boolean correctAnswerClicked) {
        gameState.setCorrectAnswerClicked(correctAnswerClicked);
        if(correctAnswerClicked) {
            // fire callback to listeners
            for(CorrectAnswerListener listener: correctAnswerListeners) {
                listener.onCorrectAnswerClicked();
            }
        }
    }

    public void registerNewClickedPerson(String id) {
        gameState.registerNewClickedPerson(id);
    }

    public List<String> getClickedIds() {
        return gameState.getClickedIds();
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public int getCorrectAnswerIndex() {
        return gameState.getCorrectAnswerIndex();
    }

    public void saveState(Bundle bundle) {
        gameState.saveState(bundle);
    }

    public void restoreState(Bundle bundle) {
        gameState.restoreState(bundle);
        onNewShuffledList(gameState.getShuffledList());
    }
}

