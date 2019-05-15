package android.example.nicksnamegame.game.game_board.gameBoardManager;

import android.example.nicksnamegame.data.db.Person;

import java.util.List;

public interface ShuffledListListener {
    void onNewShuffledList(List<Person> shuffledList);
}
