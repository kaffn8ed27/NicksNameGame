package android.example.nicksnamegame.data.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Person {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String headShotUrl;

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

    public String getId() { return this.id; }

    public String getName() {
        return this.name;
    }

    public String getHeadShotUrl() {
        return this.headShotUrl;
    }

}

