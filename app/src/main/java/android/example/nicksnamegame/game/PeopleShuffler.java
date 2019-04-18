package android.example.nicksnamegame.game;

import android.content.Context;
import android.example.nicksnamegame.R;
import android.example.nicksnamegame.data.NameGameApi;
import android.example.nicksnamegame.data.model.PersonResponse;
import android.util.Log;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PeopleShuffler {

    private static final String TAG = PeopleShuffler.class.getSimpleName();
    private static final int NUM_COWORKERS_TO_SHOW = 6;

    public ShuffledList createShuffledList (Context context) {

        final List<Person> personArrayList = new ArrayList<>();

        NameGameApi api = new Retrofit.Builder()
                .baseUrl("https://www.willowtreeapps.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NameGameApi.class);

        Call<List<PersonResponse>> apiCall = api.fetchPeopleList();

        apiCall.enqueue(new Callback<List<PersonResponse>>() {
            @Override
            public void onResponse(Call<List<PersonResponse>> call, Response<List<PersonResponse>> response) {
                Log.d(TAG, "Response successful");
            }

            @Override
            public void onFailure(Call<List<PersonResponse>> call, Throwable t) {
                Log.d(TAG, "Response failed");
            }
        });

        Person john = new Person(context.getString(R.string.John), R.drawable.john);
        Person paul = new Person(context.getString(R.string.Paul), R.drawable.paul);
        Person george = new Person(context.getString(R.string.George), R.drawable.george);
        Person ringo = new Person(context.getString(R.string.Ringo), R.drawable.ringo);
        Person yoko = new Person(context.getString(R.string.Yoko), R.drawable.yoko);

        personArrayList.add(john);
        personArrayList.add(paul);
        personArrayList.add(george);
        personArrayList.add(ringo);
        personArrayList.add(yoko);

        Collections.shuffle(personArrayList);

        // generate a random integer to select the "correct" answer for this list of people
        int correctAnswerIndex = ThreadLocalRandom.current().nextInt(0, personArrayList.size());
        return new ShuffledList(personArrayList, correctAnswerIndex);
    }

    public class ShuffledList {

        private List<Person> people;
        private int index;

        public ShuffledList (List<Person> people, int personToQuery) {
            this.people = people;
            this.index = personToQuery;
        }

        public List<Person> getPeople () {
            return this.people;
        }

        public int getIndex() {
            return this.index;
        }
    }
}
