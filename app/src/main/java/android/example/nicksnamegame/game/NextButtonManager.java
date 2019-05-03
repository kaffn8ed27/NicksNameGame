package android.example.nicksnamegame.game;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NextButtonManager implements Parcelable {

    private FloatingActionButton fab;
    private boolean correctAnswerClicked = false;

    @Inject
    public NextButtonManager() {
    }

    protected NextButtonManager(Parcel in) {
        correctAnswerClicked = in.readByte() != 0;
    }

    public static final Creator<NextButtonManager> CREATOR = new Creator<NextButtonManager>() {
        @Override
        public NextButtonManager createFromParcel(Parcel in) {
            return new NextButtonManager(in);
        }

        @Override
        public NextButtonManager[] newArray(int size) {
            return new NextButtonManager[size];
        }
    };

    public boolean getCorrectAnswerClicked () {
        return correctAnswerClicked;
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
    }

    public void setEnabled(boolean enabled) {

        correctAnswerClicked = enabled;
        fab.setClickable(enabled);

        if (enabled) fab.show();
        else fab.hide();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (correctAnswerClicked ? 1 : 0));
    }
}
