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

    @Inject
    public GameState() {
    }

    void registerNewClickedPerson(String id) {
        if (!clickedIds.contains(id)) {
            clickedIds.add(id);
        }
    }

    public boolean getCorrectAnswerClicked() {
        return correctAnswerClicked;
    }

    public void setCorrectAnswerClicked(boolean correctAnswerClicked) {
        this.correctAnswerClicked = correctAnswerClicked;
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
}
