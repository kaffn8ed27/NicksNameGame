package android.example.nicksnamegame.game.game_board.gameBoardManager;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class GameState {

    private List<String> clickedIds = new ArrayList<>();
    private boolean correctAnswerClicked = false;
    private int correctAnswerIndex;


    @Inject
    GameState() {
    }

    @NonNull
    @Override
    public String toString() {
        return clickedIds.toString();
    }

    List<String> getClickedIds() {
        return clickedIds;
    }

    void registerNewClickedPerson(String id) {
        if (!clickedIds.contains(id)) {
            clickedIds.add(id);
        }
    }

    void clearClickedIds() {
        clickedIds.clear();
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
}
