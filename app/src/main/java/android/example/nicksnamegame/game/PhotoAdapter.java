package android.example.nicksnamegame.game;

import android.content.Context;
import android.example.nicksnamegame.R;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhotoAdapter extends RecyclerView.Adapter<PersonViewHolder>
        implements Parcelable {

    private static final String TAG = PhotoAdapter.class.getSimpleName();

    private List<Person> coworkers;
    private int correctAnswerIndex;

    @Inject
    PhotoAdapter() {
    }

    private PhotoAdapter(Parcel in) {
        this.correctAnswerIndex = in.readInt();
        this.coworkers = in.readParcelable(ShuffledList.class.getClassLoader());
    }

    void setData(ShuffledList shuffledList) {
        this.coworkers = shuffledList.getPeople();
        this.correctAnswerIndex = shuffledList.getCorrectAnswerIndex();
        notifyDataSetChanged();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(correctAnswerIndex);
    }
}
