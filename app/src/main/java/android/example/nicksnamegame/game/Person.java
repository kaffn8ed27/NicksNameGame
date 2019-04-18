package android.example.nicksnamegame.game;


import androidx.annotation.DrawableRes;

public class Person {
    private String name; // for now, just type it in; eventually this will come from the API

    private String headshotUrl;

    @DrawableRes
    private int photoId; // temporary - to pull from resources in project

    // constructor for demo version: remove once everything's coming in from teh interwebz
    public Person (String name, @DrawableRes int photoId) {
        this.name = name;
        this.photoId = photoId;
    }

    public Person (String name, String headshotUrl) {
        this.name = name;
        this.headshotUrl = headshotUrl;
    }

    public String getName () {
        return this.name;
    }

    public int getId () {
        return this.photoId;
    }

    public String getHeadshotUrl () {
        return this.headshotUrl;
    }
}
