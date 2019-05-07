package android.example.nicksnamegame.game;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class GameBoardManager {

    private static final String TAG = GameBoardManager.class.getSimpleName();

    private ShuffledList shuffledList;

    @Inject
    NextButtonManager nextButtonManager;
    @Inject
    PeopleShuffler peopleShuffler;
    @Inject
    PhotoAdapter photoAdapter;

    private List<Person> personList;

    @Inject
    public GameBoardManager() {
    }

    PhotoAdapter generateGameBoard() {

        nextButtonManager.setEnabled(false);
        if (photoAdapter != null) photoAdapter.clearClickedState();

        try {
            shuffledList = peopleShuffler.chooseCoworkers(personList);
            photoAdapter.setData(shuffledList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photoAdapter;
    }

    void setPersonList (List<Person> personList) {
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
