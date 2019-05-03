package android.example.nicksnamegame.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

public class ShuffledList implements Parcelable, Shuffle {

    private List<Person> people;
    private int correctAnswerIndex;

    private static final String TAG = ShuffledList.class.getSimpleName();

    public ShuffledList(List<Person> people) {
        this.people = people;
//        generate the index of the correct answer for this round
        this.correctAnswerIndex = (int) (Math.random() * people.size());
        for(Person person : people) {
            Log.d(TAG, person.toString());
        }
    }

    @Override
    public List<Person> getPeople() {
        return this.people;
    }

    @Override
    public int getCorrectAnswerIndex() {
        return this.correctAnswerIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.people);
        dest.writeInt(this.correctAnswerIndex);
    }

    public static final Creator<ShuffledList> CREATOR = new Creator<ShuffledList>() {

        @Override
        public ShuffledList createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public ShuffledList[] newArray(int size) {
            return new ShuffledList[0];
        }
    };
}
