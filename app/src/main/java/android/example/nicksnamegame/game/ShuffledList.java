package android.example.nicksnamegame.game;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ShuffledList implements Parcelable {

    private List<Person> people;
    private int index;

    public ShuffledList(List<Person> people, int index) {
        this.people = people;
        this.index = index;
    }

    public List<Person> getPeople() {
        return this.people;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.people);
        dest.writeInt(this.index);
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
