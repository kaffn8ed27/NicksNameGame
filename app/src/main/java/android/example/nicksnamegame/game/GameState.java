package android.example.nicksnamegame.game;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class GameState {
    private List<String> clickedIds = new ArrayList<>();
    private ShuffledList shuffledList;

    @Inject
    public GameState() {
    }

    void registerNewClickedPerson(String id) {
        if (!clickedIds.contains(id)) {
            clickedIds.add(id);
        }
    }

    void clear() {
        clickedIds.clear();
    }

    public List<String> getClickedIds() {
        return clickedIds;
    }

    public ShuffledList getShuffledList() {
        return shuffledList;
    }

    public void setShuffledList(ShuffledList shuffledList) {
        this.shuffledList = shuffledList;
    }

    @NonNull
    @Override
    public String toString() {
        return clickedIds.toString();
    }
}
