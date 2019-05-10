package android.example.nicksnamegame.game.game_board;

import androidx.annotation.NonNull;

public class Person {
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

    public String getName() {
        return this.name;
    }

    public String getHeadShotUrl() {
        return this.headShotUrl;
    }

    public String getId() {
        return this.id;
    }

}
