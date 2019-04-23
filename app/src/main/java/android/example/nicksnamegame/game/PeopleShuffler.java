package android.example.nicksnamegame.game;

import android.content.Context;
import android.example.nicksnamegame.R;
import android.provider.Contacts;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class PeopleShuffler {


    private static final String TAG = PeopleShuffler.class.getSimpleName();
    private static final int NUM_COWORKERS_TO_SHOW = 12;
    private List<Person> personList = new ArrayList<>();
    int correctAnswerIndex;

    public PeopleShuffler() {}

    public PeopleShuffler(List<Person> personList) {
        this.personList = personList;
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
        correctAnswerIndex = ThreadLocalRandom.current().nextInt(0, listToQuery.size());
        // create and return a ShuffledList from the randomly generated list of people
        Log.d(TAG, listToQuery.toString());
        return new ShuffledList(listToQuery, correctAnswerIndex);
    }

    public class ShuffledList {

        private List<Person> people;
        private int index;

        public ShuffledList (List<Person> people, int index) {
            this.people = people;
            this.index = index;
        }

        public List<Person> getPeople () {
            return this.people;
        }

        public int getIndex() {
            return this.index;
        }
    }
}
