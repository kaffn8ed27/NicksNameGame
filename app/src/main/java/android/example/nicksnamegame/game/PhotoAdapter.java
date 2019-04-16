package android.example.nicksnamegame.game;

import android.content.Context;
import android.example.nicksnamegame.R;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PersonViewHolder> {

    private List<Person> coworkers;
    private int index;

    public PhotoAdapter(PeopleShuffler.ShuffledList shuffledList) {
        this.coworkers = shuffledList.getPeople();
        this.index = shuffledList.getIndex();
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForPhotoGroup = R.layout.photo_group;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForPhotoGroup, viewGroup, shouldAttachToParentImmediately);
        PersonViewHolder viewHolder = new PersonViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.bind(coworkers.get(position));
    }

    @Override
    public int getItemCount() {
        return coworkers.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

        TextView personNameView;
        ImageView personPhotoView;

        public PersonViewHolder(View v) {
            super(v);
            personNameView = v.findViewById(R.id.coworker_name);
            personPhotoView = v.findViewById(R.id.coworker_photo);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /* set the color for the foreground to change to
             Correct face clicked: chose_wisely
             Incorrect face clicked: chose_poorly
             */
            int foregroundColor =
                    this.getAdapterPosition() == index ? R.color.chose_wisely : R.color.chose_poorly;
            // create the drawable for the foreground color
            Drawable foreground = new ColorDrawable(ContextCompat.getColor(v.getContext(), foregroundColor));
            // make it partially transparent
            foreground.setAlpha(127);

            // draw the color over the photo and show the name of the clicked person
            personPhotoView.setForeground(foreground);
            personNameView.setVisibility(View.VISIBLE);
        }

        void bind (Person person) {
            personPhotoView.setImageResource(person.getId());
            personNameView.setText(person.getName());
        }
    }
}
