package android.example.nicksnamegame.game.game_board.game_controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GameBoardFragment extends Fragment {

    private static final String TAG = GameBoardFragment.class.getSimpleName();

    /* TODO - tracking: if answered right on the first try, remove coworker from the pool.
     *  If there aren't enough people left in the pool to fill the grid, allow "wrong" answers
     *  to come from the list of known people, but make sure they're never the "right" answer again
     *  until the pool is empty and the game is restarted
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // dependencies, DB & network transactions - any setup that does not require views
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout & return empty view
        return super.onCreateView(inflater, container, savedInstanceState); // replace this with the layout's root view
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // manipulating views i.e. findViewById & data insertion
    }
}
