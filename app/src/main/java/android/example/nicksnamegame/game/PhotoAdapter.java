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

import javax.inject.Inject;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PersonViewHolder>
        implements Parcelable {

    private ClickedPeople clickedPeople;

    private static final String TAG = PhotoAdapter.class.getSimpleName();

    private List<Person> coworkers;
    private int correctAnswerIndex;
    private Context context;

    @Inject
    NextButtonManager nextButtonManager;

    @Inject
    PhotoAdapter(Context context) {
        clickedPeople = new ClickedPeople();
        this.context = context;
    }

    private PhotoAdapter(Parcel in) {
        this.correctAnswerIndex = in.readInt();
        this.coworkers = in.readParcelable(ShuffledList.class.getClassLoader());
        this.context = in.readParcelable(Context.class.getClassLoader());
        this.nextButtonManager = in.readParcelable(Context.class.getClassLoader());
        this.clickedPeople = in.readParcelable(Context.class.getClassLoader());
    }

    public void setData(ShuffledList shuffledList) {
        this.coworkers = shuffledList.getPeople();
        this.correctAnswerIndex = shuffledList.getCorrectAnswerIndex();
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
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.d(TAG, "New view holder created");
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
        return coworkers.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(correctAnswerIndex);
    }

    public void clearClickedState () {
        clickedPeople.clear();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final String TAG = PersonViewHolder.class.getSimpleName();

        TextView personNameView;
        ImageView personPhotoView;

        PersonViewHolder(View v) {
            super(v);
            personNameView = v.findViewById(R.id.coworker_name);
            personPhotoView = v.findViewById(R.id.coworker_photo);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (!nextButtonManager.getCorrectAnswerClicked()) {
                Log.d(TAG, "Click registered");
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
            String headShotUrl;
            if (person.getHeadShotUrl() != null) {
                headShotUrl = person.getHeadShotUrl();
                Picasso.with(PhotoAdapter.this.context).load(headShotUrl).into(this.personPhotoView);
            } else {
                Log.d(TAG, person.getName() + " has no head shot");
            }

            if (person.getName() != null) {
                personNameView.setText(person.getName());
            }

            // If the person has been clicked, color the photo foreground appropriately
            if (person.getId() != null) {
                if (clickedPeople.clickedIds.contains(person.getId())) {
                    int foregroundColor = R.color.chose_poorly;
                    if (this.getAdapterPosition() == correctAnswerIndex) {
                        foregroundColor = R.color.chose_wisely;

                        /* if the correct answer is clicked, flag the game as such
                         * this will affect several aspects of the game - no more clicking allowed,
                         * "next" button enabled, etc. */
                        nextButtonManager.setEnabled(true);
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
            Log.d(TAG, "Correct: " + nextButtonManager.getCorrectAnswerClicked());
        }
    }

    class ClickedPeople {
        private List<String> clickedIds = new ArrayList<>();

        void registerNewClickedPerson(String id) {
            if (!clickedIds.contains(id)) {
                clickedIds.add(id);
            }
        }

        void clear() {
            clickedIds.clear();
        }

        @NonNull
        @Override
        public String toString() {
            return clickedIds.toString();
        }
    }
}
