package android.example.nicksnamegame.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class HeadshotResponse implements Parcelable {

    private String url;
    private String type;
    private int height;
    private int width;

    public HeadshotResponse(@Nullable String headshotUrl,
                            @Nullable String type,
                            @Nullable int height,
                            @Nullable int width) {
        this.url = headshotUrl;
        this.type = type;
        this.height = height;
        this.width = width;
    }

    private HeadshotResponse(Parcel in) {
        this.url = in.readString();
        this.type = in.readString();
        this.height = in.readInt();
        this.width = in.readInt();
    }

    public String getHeadshotUrl() {
        if (this.url != null) {
            // already properly formatted
            if (this.url.startsWith("http")) {
                return this.url;
            } else {
                // missing "http:" or "https:"
                return "https:" + this.url;
            }
        } else return null;
    }

    public String getType() {
        return this.type;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.type);
        dest.writeInt(this.height);
        dest.writeInt(this.width);
    }

    public static final Creator<HeadshotResponse> CREATOR = new Creator<HeadshotResponse>() {
        @Override
        public HeadshotResponse createFromParcel(Parcel source) {
            return new HeadshotResponse(source);
        }

        @Override
        public HeadshotResponse[] newArray(int size) {
            return new HeadshotResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
