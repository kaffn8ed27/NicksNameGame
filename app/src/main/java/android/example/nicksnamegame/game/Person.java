package android.example.nicksnamegame.game;

public class Person {
    private String name;
    private String headshotUrl;
    private String id;

    public Person (String name, String headshotUrl, String id) {
        this.name = name;
        this.headshotUrl = headshotUrl;
        this.id = id;
    }

    public String getName () {
        return this.name;
    }

    public String getHeadshotUrl () { return this.headshotUrl; }

    public String getId () { return this.id; }

}
