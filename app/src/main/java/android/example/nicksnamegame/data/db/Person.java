package android.example.nicksnamegame.data.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Person implements Parcelable {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String headShotUrl;

    public Person(String name, String headShotUrl, @NonNull String id) {
        this.name = name;
        this.headShotUrl = headShotUrl;
        this.id = id;
    }

    protected Person(Parcel in) {
        id = in.readString();
        name = in.readString();
        headShotUrl = in.readString();
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

    @NonNull
    @Override
    public String toString() {
        return ("{Name: " + this.getName() + ", Id: " + this.getId() + "}");
    }

    @NonNull
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getHeadShotUrl() {
        return this.headShotUrl;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(headShotUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

