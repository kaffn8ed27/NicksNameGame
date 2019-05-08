package android.example.nicksnamegame.game;

import android.content.Context;
import android.example.nicksnamegame.R;
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
    private int correctAnswerIndex;
//    private GameBoardManager gameBoardManager;
    private ShuffledListListener listener;

    @Inject
    PhotoAdapter(GameBoardManager gameBoardManager) {
//        this.gameBoardManager = gameBoardManager;
        listener = (shuffledList -> {
            setData(shuffledList);
        });
        gameBoardManager.setShuffledListListener(listener);
    }

    private void setData(ShuffledList shuffledList) {
        if(shuffledList != null) {
            this.coworkers = shuffledList.getPeople();
            this.correctAnswerIndex = shuffledList.getCorrectAnswerIndex();
            notifyDataSetChanged();
        } else Log.d(TAG, "No new list found");
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
