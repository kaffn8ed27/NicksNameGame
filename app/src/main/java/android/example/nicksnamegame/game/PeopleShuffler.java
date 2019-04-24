package android.example.nicksnamegame.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PeopleShuffler {


    private static final String TAG = PeopleShuffler.class.getSimpleName();
    private static int NUM_COWORKERS_TO_SHOW;
    private List<Person> personList;
    int correctAnswerIndex;

    public PeopleShuffler(List<Person> personList, int num_coworkers_to_show) {
        this.personList = personList;
        // TODO eliminate people with same name
        // TODO: eliminate people with no picture
        // TODO: eliminate people whose picture does not have a face (Google Vision API)
        this.NUM_COWORKERS_TO_SHOW = num_coworkers_to_show;
    }

    public ShuffledList chooseCoworkers() {
        List<Person> listToQuery = new ArrayList<>();
        // shuffle the entire list of people
        Collections.shuffle(personList);
        // select first n people and put them in a new list
        for (int i = 0; i < NUM_COWORKERS_TO_SHOW; i++) {
            listToQuery.add(personList.get(i));
        }
        // generate the index of the correct answer for this round
        correctAnswerIndex = (int) (Math.random() * listToQuery.size());
        // create and return a ShuffledList from the randomly generated list of people
        Log.d(TAG, listToQuery.toString());
        return new ShuffledList(listToQuery, correctAnswerIndex);
    }
}
