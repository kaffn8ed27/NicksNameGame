package android.example.nicksnamegame.game;


import androidx.annotation.DrawableRes;

class Person {
    private String name; // for now, just type it in; eventually this will also come from the API
    @DrawableRes
    private int photoId; // temporary - to pull from resources in project
    private String url; //  Eventually, will pull the image file from the API

    public Person (String name, @DrawableRes int photoId) {
        this.name = name;
        this.photoId = photoId;
    }

    public String getName () {
        return this.name;
    }

    public int getId () {
        return this.photoId;
    }
}
