package android.example.nicksnamegame.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PeopleShuffler implements Parcelable {

    /* TODO: use Rx Subject to filter API responses
     *  and pass filtered & shuffled list to GameActivity
     */

    private static final String TAG = PeopleShuffler.class.getSimpleName();
    private static int NUM_COWORKERS_TO_SHOW;
    private List<Person> personList;
    private int correctAnswerIndex;

    PeopleShuffler(List<Person> personList, int num_coworkers_to_show) {

        this.personList = personList;
        NUM_COWORKERS_TO_SHOW = num_coworkers_to_show;
    }

    private PeopleShuffler(Parcel in) {
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

    ShuffledList chooseCoworkers() {
        List<Person> listToQuery = new ArrayList<>();
        // shuffle the entire list of people
        Collections.shuffle(personList);
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
