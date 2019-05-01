package android.example.nicksnamegame.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class HeadShotResponse implements Parcelable {

    private String url;
    private String type;
    private int height;
    private int width;

    // TODO: use an Rx Subject to hold the API responses

    public HeadShotResponse(@Nullable String headShotUrl,
                            @Nullable String type,
                            int height,
                            int width) {
        this.url = headShotUrl;
        this.type = type;
        this.height = height;
        this.width = width;
    }

    private HeadShotResponse(Parcel in) {
        this.url = in.readString();
        this.type = in.readString();
        this.height = in.readInt();
        this.width = in.readInt();
    }

    String getHeadShotUrl() {
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

    public static final Creator<HeadShotResponse> CREATOR = new Creator<HeadShotResponse>() {
        @Override
        public HeadShotResponse createFromParcel(Parcel source) {
            return new HeadShotResponse(source);
        }

        @Override
        public HeadShotResponse[] newArray(int size) {
            return new HeadShotResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
