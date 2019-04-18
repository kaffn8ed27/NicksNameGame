package android.example.nicksnamegame.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonResponse implements Parcelable {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final HeadshotResponse headshot;

    public PersonResponse(String id,
                  String firstName,
                  String lastName,
                  HeadshotResponse headshot) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.headshot = headshot;
    }

    private PersonResponse(Parcel in) {
        this.id = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.headshot = in.readParcelable(HeadshotResponse.class.getClassLoader());
    }

    public String getId() { return this.id; }
    public String getFirstName() { return  this.firstName; }
    public String getLastName() { return this.lastName; }
    public HeadshotResponse getHeadshot() { return this.headshot; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeParcelable(headshot, flags);
    }

    public static final Creator<PersonResponse> CREATOR = new Creator<PersonResponse>() {
        @Override
        public PersonResponse createFromParcel(Parcel source) {
            return new PersonResponse(source);
        }

        @Override
        public PersonResponse[] newArray(int size) {
            return new PersonResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
