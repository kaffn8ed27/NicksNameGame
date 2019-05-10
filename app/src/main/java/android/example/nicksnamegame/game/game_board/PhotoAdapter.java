package android.example.nicksnamegame.game.game_board;

import android.content.Context;
import android.example.nicksnamegame.R;
import android.example.nicksnamegame.data.db.Person;
import android.example.nicksnamegame.game.game_board.gameBoardManager.ShuffledListListener;
import android.example.nicksnamegame.game.game_board.gameBoardManager.GameBoardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhotoAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    private static final String TAG = PhotoAdapter.class.getSimpleName();

    private List<Person> coworkers;


    @Inject
    PhotoAdapter(GameBoardManager gameBoardManager) {
        ShuffledListListener listener = (shuffledList -> {
            if (shuffledList != null) {
                this.coworkers = shuffledList.getPeople();
                notifyDataSetChanged();
            } else Log.d(TAG, "No new list found");
        });
        gameBoardManager.setShuffledListListener(listener);
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForPhotoGroup = R.layout.photo_group;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForPhotoGroup, viewGroup, shouldAttachToParentImmediately);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = coworkers.get(position);
        holder.bind(person);
    }

    @Override
    public int getItemCount() {
        if (coworkers == null) return 0;
        return coworkers.size();
    }

}
