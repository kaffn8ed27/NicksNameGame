package android.example.nicksnamegame.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class PeopleShuffler {


    private static final String TAG = PeopleShuffler.class.getSimpleName();
    private static int NUM_COWORKERS_TO_SHOW;
    private List<Person> personList;
    int correctAnswerIndex;

    public PeopleShuffler(CopyOnWriteArrayList<Person> personList, int num_coworkers_to_show) {
        this.personList = personList;

        /* TODO eliminate people with same name
         *  i.e. same person is listed twice (there IS at least one example of this)
         */

        int totalResponses = personList.size();
        for (Person person : personList) {
            String headshotUrl = person.getHeadshotUrl();
            String name = person.getName();
            if (headshotUrl == null) {
                Log.d(TAG, "Removing " + name + ": missing headshot URL");
                personList.remove(person);
            } else if (headshotUrl.contains("featured-image-TEST1.png")) {
                Log.d(TAG, "Removing " + name + " from list: invalid headshot URL: " + headshotUrl);
                personList.remove(person);
            }
        }
        Log.d(TAG, "Removed " + (totalResponses - personList.size()) + " people from list");

        // TODO: eliminate people whose picture does not have a face (Google Vision API?)

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
