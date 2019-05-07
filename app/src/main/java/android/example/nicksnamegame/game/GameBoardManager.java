package android.example.nicksnamegame.game;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class GameBoardManager {

    private static final String TAG = GameBoardManager.class.getSimpleName();

    private ShuffledList shuffledList;

    final NextButtonManager nextButtonManager;
    final PeopleShuffler peopleShuffler;
    final PhotoAdapter photoAdapter;
    final GameState gameState;

    private List<Person> personList;

    @Inject
    GameBoardManager(NextButtonManager nextButtonManager, PeopleShuffler peopleShuffler, PhotoAdapter photoAdapter, GameState gameState) {
        this.nextButtonManager = nextButtonManager;
        this.peopleShuffler = peopleShuffler;
        this.photoAdapter = photoAdapter;
        this.gameState = gameState;
    }

    PhotoAdapter generateGameBoard() {
        // disable nextButton FAB
        nextButtonManager.setEnabled(false);
        // reset tracking of photos that have been clicked
        gameState.clear();
        // grab a new set of people to play on
        shuffledList = peopleShuffler.chooseCoworkers(personList);
        gameState.setShuffledList(shuffledList);
        // set up the adapter with the new list
        photoAdapter.setData(shuffledList);
        // return the adapter to the GameActivity
        return photoAdapter;
    }

    void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    String createNamePrompt() {
        // extract the correct name from the ShuffledList object and set the prompt text
        String namePrompt;
        if (shuffledList != null) {
            int index = shuffledList.getCorrectAnswerIndex();
            List<Person> peopleOnGameBoard = shuffledList.getPeople();
            String name = peopleOnGameBoard.get(index).getName();
            namePrompt = "Who is " + name + "?";
        } else {
            namePrompt = "";
        }
        return namePrompt;
    }
}
