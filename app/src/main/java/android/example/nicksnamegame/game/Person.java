package android.example.nicksnamegame.game;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private String name;
    private String headshotUrl;
    private String id;

    public Person(String name, String headshotUrl, String id) {
        this.name = name;
        this.headshotUrl = headshotUrl;
        this.id = id;
    }

    protected Person(Parcel in) {
        name = in.readString();
        headshotUrl = in.readString();
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

    public String getHeadshotUrl() {
        return this.headshotUrl;
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
        dest.writeString(headshotUrl);
        dest.writeString(id);
    }
}
