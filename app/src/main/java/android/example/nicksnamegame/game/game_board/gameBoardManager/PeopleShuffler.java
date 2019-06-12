package android.example.nicksnamegame.game.game_board.gameBoardManager;

import android.content.Context;
import android.example.nicksnamegame.R;
import android.example.nicksnamegame.data.db.Person;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;


public class PeopleShuffler {

    private static final String TAG = PeopleShuffler.class.getSimpleName();
    private final int NUM_COWORKERS_TO_SHOW;
    private final Random random;

    @Inject
    PeopleShuffler(Context context, Random random) {
        this(context.getResources().getInteger(R.integer.number_game_photos), random);
    }

    public PeopleShuffler(int numberOfCoworkers, Random random) {

        NUM_COWORKERS_TO_SHOW = numberOfCoworkers;
        Log.d(TAG, "# people to display: " + NUM_COWORKERS_TO_SHOW);
        this.random = random;

    }

    List<Person> chooseCoworkers(List<Person> personList) {

        List<Person> listToQuery = new ArrayList<>();

        // select n people at random and put them in a new list
        while (listToQuery.size() < NUM_COWORKERS_TO_SHOW) {
            // assume next person is unique
            boolean duplicate = false;
            // choose pseudo-random person from the list
            int index = (int) (random.nextDouble() * personList.size());
            Person candidate = personList.get(index);
            // check against existing list of people
            for (Person person : listToQuery) {
                if (candidate.getName().equals(person.getName()) || candidate.getHeadShotUrl().equals(person.getHeadShotUrl())) {
                    /* same name isn't totally reliable - dubbed the "Mike v. Michael" bug
                     * combining with check on head shot appears to eliminate all duplicates that
                     * exist as it stands now */

                    Log.d(TAG, "Person named " + candidate.getName() + " already in list or has a duplicate");
                    duplicate = true;
                    break; // don't process the rest of the list once a duplicate is found
                }
            }

            /* This will not eliminate 100% of duplicates (e.g. "John Doe" and "Johnathan Doe"
             * with 2 different head shots), but so far it seems to have eliminated all the ones
             * returned by the current version of the API */
            if (!duplicate) {
                listToQuery.add(candidate);
            }
        }
        // create and return a new list of Person objects from the randomly generated list of people
        Log.d(TAG, "# people chosen: " + listToQuery.size());
        return listToQuery;

    }
}
