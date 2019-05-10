package android.example.nicksnamegame.game.game_board.gameBoardManager;

import android.example.nicksnamegame.data.db.Person;
import android.util.Log;

import java.util.List;

public class ShuffledList {

    private List<Person> people;
    private int correctAnswerIndex;

    private static final String TAG = ShuffledList.class.getSimpleName();

    ShuffledList(List<Person> people) {
        this.people = people;
        // generate the index of the correct answer for this round
        this.correctAnswerIndex = (int) (Math.random() * people.size());
        for (Person person : people) {
            Log.d(TAG, person.toString());
        }
    }

    public List<Person> getPeople() {
        return this.people;
    }

    public int getCorrectAnswerIndex() {
        return this.correctAnswerIndex;
    }

}
