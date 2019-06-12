package android.example.nicksnamegame.game.game_board.gameBoardManager;


import android.example.nicksnamegame.game.dagger.GameBoardScope;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@GameBoardScope
class GameState {

    private List<String> clickedIds = new ArrayList<>();
    private boolean correctAnswerClicked = false;
    private int correctAnswerIndex;


    @Inject
    GameState() {
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

}
