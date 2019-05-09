package android.example.nicksnamegame.data.model;

public class PersonResponse {

    private final String id;
    private final String firstName;
    private final String lastName;

    /* this "headshot" has to be all lowercase or else things break
     *  Possibly something to do with Rx or Gson conversion
     */
    private final HeadShotResponse headshot;

    public PersonResponse(String id,
                          String firstName,
                          String lastName,
                          HeadShotResponse headshot) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.headshot = headshot;
    }

    public String getId() {
        return this.id;
    }

    String getFirstName() {
        return this.firstName;
    }

    String getLastName() {
        return this.lastName;
    }

    HeadShotResponse getHeadShot() {
        return this.headshot;
    }

}
