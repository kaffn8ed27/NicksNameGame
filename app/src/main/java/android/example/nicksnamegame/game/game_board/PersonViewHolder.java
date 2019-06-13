package android.example.nicksnamegame.game.game_board;

import android.example.nicksnamegame.R;
import android.example.nicksnamegame.data.db.Person;
import android.example.nicksnamegame.game.dagger.PersonViewScope;
import android.example.nicksnamegame.game.game_board.gameBoardManager.GameBoardManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

@PersonViewScope
public class PersonViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private final String TAG = PersonViewHolder.class.getSimpleName();

    private GameBoardManager gameBoardManager;
    private TextView personNameView;
    private ImageView personPhotoView;
    private Person person;

    @Inject
    PersonViewHolder(View v, GameBoardManager gameBoardManager) {
        super(v);
        this.gameBoardManager = gameBoardManager;
        personNameView = v.findViewById(R.id.coworker_name);
        personPhotoView = v.findViewById(R.id.coworker_photo);
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // need listeners for correct click event
        Log.d(TAG, "Click registered");
        if (!gameBoardManager.getCorrectAnswerClicked()) {
            // Add the person's ID to the list of clicked people
            String id = person.getId();
            gameBoardManager.registerNewClickedPerson(id);
            Log.d(TAG, "Clicked state: " + gameBoardManager.getGameState());
            bind(person);
        }
    }

    void bind(Person person) {
        this.person = person;
        String headShotUrl;
        if (person.getHeadShotUrl() != null) {
            headShotUrl = person.getHeadShotUrl();
            Picasso.with(this.itemView.getContext()).load(headShotUrl)
                    .placeholder(R.drawable.wt_loading)
                    .into(this.personPhotoView);
        }
        if (person.getName() != null) {
            personNameView.setText(person.getName());
        }

        // If the person has been clicked, color the photo foreground appropriately
        if (gameBoardManager.getClickedIds().contains(person.getId())) {
            // check if this is the correct answer
            int correctAnswerIndex = gameBoardManager.getCorrectAnswerIndex();
            boolean isCorrectAnswer = (correctAnswerIndex == this.getAdapterPosition());
            if (isCorrectAnswer) {
                gameBoardManager.setCorrectAnswerClicked(true);
            }
            // determine what color to shade the photo with
            int foregroundColor = isCorrectAnswer ? R.color.chose_wisely : R.color.chose_poorly;
            // create the drawable for the foreground color
            Drawable foreground = new ColorDrawable(
                    ContextCompat.getColor(this.itemView.getContext(), foregroundColor));
            // make it partially transparent
            foreground.setAlpha(127);
            // draw the color over the photo and show the name of the clicked person
            itemView.setForeground(foreground);
            personNameView.setVisibility(View.VISIBLE);
        } else {
            itemView.setForeground(null);
            personNameView.setVisibility(View.INVISIBLE);
        }
    }
}
