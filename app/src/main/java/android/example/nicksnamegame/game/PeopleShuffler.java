package android.example.nicksnamegame.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class PeopleShuffler implements Parcelable {


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

    protected PeopleShuffler(Parcel in) {
        personList = in.createTypedArrayList(Person.CREATOR);
        correctAnswerIndex = in.readInt();
    }

    public static final Creator<PeopleShuffler> CREATOR = new Creator<PeopleShuffler>() {
        @Override
        public PeopleShuffler createFromParcel(Parcel in) {
            return new PeopleShuffler(in);
        }

        @Override
        public PeopleShuffler[] newArray(int size) {
            return new PeopleShuffler[size];
        }
    };

    public ShuffledList chooseCoworkers() {
        List<Person> listToQuery = new ArrayList<>();
        // shuffle the entire list of people
        Collections.shuffle(personList);
        // select n people at random and put them in a new list
        while (listToQuery.size() < NUM_COWORKERS_TO_SHOW) {
            boolean duplicate = false;
            int index = (int) (Math.random() * personList.size());
            Person candidate = personList.get(index);
            for (Person person : listToQuery) {
                if (candidate.getName().equals(person.getName())) {
                    Log.d(TAG, "Person named " + candidate.getName() + " already in list");
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                listToQuery.add(candidate);
            }
        }
        // generate the index of the correct answer for this round
        correctAnswerIndex = (int) (Math.random() * listToQuery.size());
        // create and return a ShuffledList from the randomly generated list of people
        Log.d(TAG, listToQuery.toString());
        return new ShuffledList(listToQuery, correctAnswerIndex);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(personList);
        dest.writeInt(correctAnswerIndex);
    }
}
