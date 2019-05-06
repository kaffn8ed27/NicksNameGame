package android.example.nicksnamegame.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;

@Singleton
public class GameBoardManager implements Parcelable {

    private static final String TAG = GameBoardManager.class.getSimpleName();

    private CompositeDisposable disposables;
    private ShuffledList shuffledList;

    @Inject
    NextButtonManager nextButtonManager;
    @Inject
    PeopleShuffler peopleShuffler;
    @Inject
    PhotoAdapter photoAdapter;


    @Inject
    public GameBoardManager() {
    }


    protected GameBoardManager(Parcel in) {
    }

    PhotoAdapter generateGameBoard(List<Person> personList) {

        nextButtonManager.setEnabled(false);
        if (photoAdapter != null) photoAdapter.clearClickedState();

        try {
            shuffledList = peopleShuffler.chooseCoworkers(personList);
            photoAdapter.setData(shuffledList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photoAdapter;
    }

    String setName() {
        // extract the correct name from the ShuffledList object and set the prompt text
        String namePrompt;
        if (shuffledList != null) {
            int index = shuffledList.getCorrectAnswerIndex();
            List<Person> peopleOnGameBoard = shuffledList.getPeople();
            String name = peopleOnGameBoard.get(index).getName();
            namePrompt = "Who is " + name + "?";
        } else {
            namePrompt = "";
        }
        return namePrompt;
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
