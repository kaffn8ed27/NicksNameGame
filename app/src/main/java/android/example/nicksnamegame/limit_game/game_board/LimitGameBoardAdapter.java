package android.example.nicksnamegame.limit_game.game_board;

import android.example.nicksnamegame.game.game_board.game_controller.GameBoardFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class LimitGameBoardAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_QUESTIONS = 5;

    LimitGameBoardAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        // change this to return the Fragment at the current position
        return GameBoardFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return NUM_QUESTIONS;
    }
}
