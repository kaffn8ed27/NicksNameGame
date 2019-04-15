package android.example.nicksnamegame.game;

import android.content.Context;
import android.example.nicksnamegame.R;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PersonViewHolder> {

    private List<Person> coworkers;

    public PhotoAdapter(List<Person> coworkers) {
        this.coworkers = coworkers;
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
            int foregroundColor;
            foregroundColor = R.color.chose_wisely;
            personPhotoView.setForeground(new ColorDrawable(v.getResources().getColor(foregroundColor)));
            personNameView.setVisibility(View.VISIBLE);
        }

        void bind (Person person) {
            personPhotoView.setImageResource(person.getId());
            personNameView.setText(person.getName());
        }
    }
}
