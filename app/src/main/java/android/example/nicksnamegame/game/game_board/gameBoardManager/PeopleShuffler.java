package android.example.nicksnamegame.game.game_board.gameBoardManager;

import android.content.Context;
import android.example.nicksnamegame.R;
import android.example.nicksnamegame.data.db.Person;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


public class PeopleShuffler {

    private static final String TAG = PeopleShuffler.class.getSimpleName();
    private int NUM_COWORKERS_TO_SHOW;

    @Inject
    PeopleShuffler(Context context) {
        this(context.getResources().getInteger(R.integer.number_game_photos));
    }

    PeopleShuffler(int numberOfCoworkers) {
        NUM_COWORKERS_TO_SHOW = numberOfCoworkers;
        Log.d(TAG, "# people to display: " + NUM_COWORKERS_TO_SHOW);
    }

    List<Person>  chooseCoworkers(List<Person> personList) {
        List<Person> listToQuery = new ArrayList<>();
        // select n people at random and put them in a new list
        while (listToQuery.size() < NUM_COWORKERS_TO_SHOW) {
            boolean duplicate = false;
            int index = (int) (Math.random() * personList.size());
            Person candidate = personList.get(index);
            for (Person person : listToQuery) {
                if (candidate.getName().equals(person.getName()) || candidate.getHeadShotUrl().equals(person.getHeadShotUrl())) {
                    Log.d(TAG, "Person named " + candidate.getName() + " already in list or has a duplicate");
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                listToQuery.add(candidate);
            }
        }
        // create and return a ShuffledList from the randomly generated list of people
        Log.d(TAG, "# people chosen: " + listToQuery.size());
        return listToQuery;
    }
}
