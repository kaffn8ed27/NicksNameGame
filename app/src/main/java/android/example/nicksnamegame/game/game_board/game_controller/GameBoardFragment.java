package android.example.nicksnamegame.game.game_board.game_controller;

import android.example.nicksnamegame.R;
import android.example.nicksnamegame.data.PersonRepo;
import android.example.nicksnamegame.data.db.Person;
import android.example.nicksnamegame.game.dagger.DaggerGameBoardComponent;
import android.example.nicksnamegame.game.dagger.GameApplication;
import android.example.nicksnamegame.game.dagger.GameBoardComponent;
import android.example.nicksnamegame.game.game_board.PhotoAdapter;
import android.example.nicksnamegame.game.game_board.gameBoardManager.GameBoardManager;
import android.example.nicksnamegame.game.game_board.gameBoardManager.ShuffledListListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GameBoardFragment extends Fragment {

    private static final String TAG = GameBoardFragment.class.getSimpleName();
    private static String fragment_value_key;
    @Inject
    PhotoAdapter photoAdapter;
    @Inject
    GameBoardManager gameBoardManager;
    @Inject
    PersonRepo personRepo;
    private int fragmentValue;
    private RecyclerView peopleRecyclerView;
    private TextView gamePromptTextView;
    private ProgressBar progressBar;
    private ShuffledListListener namePromptListener;
    private CompositeDisposable disposables;
    private GameBoardComponent gameBoardComponent;

    /* TODO - tracking: if answered right on the first try, remove coworker from the pool.
     *  If there aren't enough people left in the pool to fill the grid, allow "wrong" answers
     *  to come from the list of known people, but make sure they're never the "right" answer again
     *  until the pool is empty and the game is restarted
     */

    public static GameBoardFragment newInstance(int fragmentValue) {
        GameBoardFragment newGameBoardFragment = new GameBoardFragment();
        Bundle args = new Bundle();
        args.putInt(fragment_value_key, fragmentValue);
        newGameBoardFragment.setArguments(args);
        return newGameBoardFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // dependencies, DB & network transactions - any setup that does not require views
        super.onCreate(savedInstanceState);
        fragment_value_key = getString(R.string.fragment_value_key);
        // create a GameBoardComponent to inject dependencies
        gameBoardComponent = DaggerGameBoardComponent.builder()
                .appComponent(
                        ((GameApplication) requireActivity().getApplication()).getAppComponent())
                .build();
        // inject dependencies
        gameBoardComponent.injectInto(GameBoardFragment.this);
        this.fragmentValue = getArguments() != null ? getArguments().getInt(fragment_value_key) : -1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout & return empty view
        return inflater.inflate(R.layout.fragment_game_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // manipulating views i.e. findViewById & data insertion
        peopleRecyclerView = view.findViewById(R.id.rv_people);
        gamePromptTextView = view.findViewById(R.id.game_prompt);
        progressBar = view.findViewById(R.id.progress_bar);

        // hide the game board while everything else loads
        setGameVisibility(false);

        namePromptListener = (shuffledList -> {
            if (shuffledList != null) {
                gamePromptTextView.setText(createNamePrompt(shuffledList, gameBoardManager.getCorrectAnswerIndex()));
            } else {
                gamePromptTextView.setText(R.string.generic_error);
            }
            // show the game board
            setGameVisibility(true);
        });
        gameBoardManager.setShuffledListListener(namePromptListener);

        int numberOfColumns = this.getResources().getInteger(R.integer.number_game_columns);
        GridLayoutManager photoManager = new GridLayoutManager(getActivity(), numberOfColumns);
        peopleRecyclerView.setLayoutManager(photoManager);
        peopleRecyclerView.setHasFixedSize(true);
        peopleRecyclerView.setAdapter(photoAdapter);


        if (savedInstanceState == null) {
            // make the network call to retrieve the list of people
            Disposable personListSubscription = personRepo.retrievePersonList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(personList -> {
                        Log.d(TAG, "Retrieved new list from repository: " + personList);
                        gameBoardManager.generateGameBoard(personList);
                        // show the game board now that everything has loaded
                        setGameVisibility(true);
                    }, throwable -> {
                        gamePromptTextView.setText(R.string.generic_error);
                        setGameVisibility(true);
                    });

            // set up the personList subscription to be disposed of when the activity is destroyed
            disposables = new CompositeDisposable();
            disposables.add(personListSubscription);
        } else {
            gameBoardManager.restoreState(savedInstanceState);
            setGameVisibility(true); // if re-creating (i.e. rotating), just show the game board
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        gameBoardManager.saveState(outState);
    }

    @Override
    public void onDestroy() {
        if (disposables != null) disposables.dispose();
        gameBoardManager.unsetShuffledListListener(namePromptListener);
        super.onDestroy();
    }

    private void setGameVisibility(boolean makeVisible) {
        if (makeVisible) {
            // hide progress bar
            progressBar.setVisibility(View.GONE);
            // show photos and prompt text
            peopleRecyclerView.setVisibility(View.VISIBLE);
            gamePromptTextView.setVisibility(View.VISIBLE);
        } else {
            // hide photos and prompt text
            peopleRecyclerView.setVisibility(View.GONE);
            gamePromptTextView.setVisibility(View.GONE);
            // show progress bar
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private String createNamePrompt(List<Person> shuffledList, int index) {
        String namePrompt;
        String name = shuffledList.get(index).getName();
        namePrompt = "Who is " + name + "?";

        return namePrompt;
    }

    public GameBoardManager getGameBoardManager() {
        return this.gameBoardManager;
    }

    public int getFragmentValue() {
        return fragmentValue;
    }
}
