package android.example.nicksnamegame.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class PeopleShuffler {


    private static final String TAG = PeopleShuffler.class.getSimpleName();
    private static int NUM_COWORKERS_TO_SHOW;
    private List<Person> personList = new ArrayList<>();
    int correctAnswerIndex;

    public PeopleShuffler() {}

    public PeopleShuffler(List<Person> personList, int num_coworkers_to_show) {
        this.personList = personList;
        this.NUM_COWORKERS_TO_SHOW = num_coworkers_to_show;
    }

    synchronized public ShuffledList chooseCoworkers() {
        List<Person> listToQuery = new ArrayList<>();
        // shuffle the entire list of people
        Collections.shuffle(personList);
        // select first n people and put them in a new list
        for (int i = 0; i < NUM_COWORKERS_TO_SHOW; i++) {
            listToQuery.add(personList.get(i));
        }
        // pseudo-randomly generate the index of the correct answer for this round
        correctAnswerIndex = (int) (Math.random() * listToQuery.size());
        // create and return a ShuffledList from the randomly generated list of people
        Log.d(TAG, listToQuery.toString());
        return new ShuffledList(listToQuery, correctAnswerIndex);
    }

}
