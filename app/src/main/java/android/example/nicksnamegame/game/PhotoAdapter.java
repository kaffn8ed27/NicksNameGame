package android.example.nicksnamegame.game;

import android.content.Context;
import android.example.nicksnamegame.R;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PersonViewHolder>
        implements Parcelable {

    ClickedPeople clickedPeople = new ClickedPeople();

    private static final String TAG = PhotoAdapter.class.getSimpleName();

    private List<Person> coworkers;
    private int index;
    private Context context;
    private boolean correctAnswerFound = false;

    public PhotoAdapter(ShuffledList shuffledList, Context context) {
        this.coworkers = shuffledList.getPeople();
        this.index = shuffledList.getIndex();
        this.context = context;
    }

    protected PhotoAdapter(Parcel in) {
        this.index = in.readInt();
        this.coworkers = in.readParcelable(ShuffledList.class.getClassLoader());
        this.context = in.readParcelable(Context.class.getClassLoader());
    }

    public static final Creator<PhotoAdapter> CREATOR = new Creator<PhotoAdapter>() {
        @Override
        public PhotoAdapter createFromParcel(Parcel in) {
            return new PhotoAdapter(in);
        }

        @Override
        public PhotoAdapter[] newArray(int size) {
            return new PhotoAdapter[size];
        }
    };

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
        Person person = coworkers.get(position);
        holder.bind(person);
    }

    @Override
    public int getItemCount() {
        return coworkers.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
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
            if (!correctAnswerFound) {
                // Add the person's ID to the list of clicked people
                Person person = coworkers.get(getAdapterPosition());
                String id;
                if (person.getId() != null) {
                    id = person.getId();
                    clickedPeople.registerNewClickedPerson(id);
                    Log.d(TAG, "Clicked state: " + clickedPeople.toString());
                }
                bind(person);
            }
        }

        void bind(Person person) {
            String headshotUrl;
            if (person.getHeadshotUrl() != null) {
                headshotUrl = person.getHeadshotUrl();
                Picasso.with(PhotoAdapter.this.context).load(headshotUrl).into(this.personPhotoView);
            } else {
                Log.d(TAG, "No URL for headshot");
            }
            if (person.getName() != null) {
                personNameView.setText(person.getName());
            }

            // If the person has been clicked, color the photo foreground appropriately
            if (person.getId() != null) {
                if (clickedPeople.clickedIds.contains(person.getId())) {
                    int foregroundColor = R.color.chose_poorly;
                    if (this.getAdapterPosition() == index) {
                        foregroundColor = R.color.chose_wisely;
                        /* if the correct answer is clicked, flag the game as such
                         * this will affect several aspects of the game - no more clicking allowed,
                         * "next" button enabled, etc. */
                        correctAnswerFound = true;
                    }
                    // create the drawable for the foreground color
                    Drawable foreground = new ColorDrawable(ContextCompat.getColor(PhotoAdapter.this.context, foregroundColor));
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
    }

    class ClickedPeople {
        private List<String> clickedIds = new ArrayList<>();

        public void registerNewClickedPerson(String id) {
            if (!clickedIds.contains(id)) {
                clickedIds.add(id);
            }
        }

        @NonNull
        @Override
        public String toString() {
            return clickedIds.toString();
        }
    }
}
