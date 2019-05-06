package android.example.nicksnamegame.game;

import android.os.Parcel;
import android.os.Parcelable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameBoardManager implements Parcelable {

    @Inject
    public GameBoardManager() {
    }


    protected GameBoardManager(Parcel in) {
    }

    public static final Creator<GameBoardManager> CREATOR = new Creator<GameBoardManager>() {
        @Override
        public GameBoardManager createFromParcel(Parcel in) {
            return new GameBoardManager(in);
        }

        @Override
        public GameBoardManager[] newArray(int size) {
            return new GameBoardManager[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
