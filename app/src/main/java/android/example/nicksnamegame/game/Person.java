package android.example.nicksnamegame.game;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Person implements Parcelable {
    private String name;
    private String headShotUrl;
    private String id;

    public Person(String name, String headShotUrl, String id) {
        this.name = name;
        this.headShotUrl = headShotUrl;
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return ("Name: " + this.getName() + ", Id: " + this.getId());
    }

    private Person(Parcel in) {
        name = in.readString();
        headShotUrl = in.readString();
        id = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return this.name;
    }

    public String getHeadShotUrl() {
        return this.headShotUrl;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(headShotUrl);
        dest.writeString(id);
    }
}
