package android.example.nicksnamegame.game.game_board.gameBoardManager;


import android.example.nicksnamegame.data.db.Person;
import android.example.nicksnamegame.game.dagger.GameBoardScope;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@GameBoardScope
class GameState {
    private List<String> clickedIds = new ArrayList<>();
    private List<Person> shuffledList;
    private boolean correctAnswerClicked = false;
    private int correctAnswerIndex;

    @Inject
    GameState() {
    }

    List<Person> getShuffledList() {
        return shuffledList;
    }

    void setShuffledList(List<Person> shuffledList) {
        this.shuffledList = shuffledList;
    }

    void clearClickedIds() {
        clickedIds.clear();
    }

    List<String> getClickedIds() {
        return clickedIds;
    }

    @NonNull
    @Override
    public String toString() {
        return clickedIds.toString();
    }

    void registerNewClickedPerson(String id) {
        if (!clickedIds.contains(id)) {
            clickedIds.add(id);
        }
    }

    boolean getCorrectAnswerClicked() {
        return correctAnswerClicked;
    }

    void setCorrectAnswerClicked(boolean correctAnswerClicked) {
        this.correctAnswerClicked = correctAnswerClicked;
    }

    int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    void saveState(Bundle bundle) {
        bundle.putStringArrayList("clickedIds", new ArrayList<>(clickedIds));
        bundle.putParcelableArrayList("shuffledList", new ArrayList<>(shuffledList));
        bundle.putBoolean("correctAnswerClicked", correctAnswerClicked);
        bundle.putInt("correctAnswerIndex", correctAnswerIndex);
    }

    void restoreState(Bundle bundle) {
        clickedIds = bundle.getStringArrayList("clickedIds");
        shuffledList = bundle.getParcelableArrayList("shuffledList");
        correctAnswerClicked = bundle.getBoolean("correctAnswerClicked");
        correctAnswerIndex = bundle.getInt("correctAnswerIndex");
    }
}
