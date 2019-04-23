package android.example.nicksnamegame.data.model;

import android.example.nicksnamegame.game.Person;
import android.example.nicksnamegame.data.NameGameApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonConverter {

    private static final String TAG = PersonConverter.class.getSimpleName();

    public List<Person> personList;

    synchronized public void retrievePersonList(final PersonListHandler responseHandler) {
        NameGameApi api = new Retrofit.Builder()
                .baseUrl("https://www.willowtreeapps.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NameGameApi.class);

        Call<List<PersonResponse>> apiCall = api.fetchPeopleList();

        apiCall.enqueue(new Callback<List<PersonResponse>>() {
            @Override
            public void onResponse(Call<List<PersonResponse>> call, Response<List<PersonResponse>> response) {
                List<PersonResponse> responseList = response.body();
                personList = new ArrayList<>();
                for (PersonResponse personResponse : responseList) {
                    String name = formatName(personResponse.getFirstName(), personResponse.getLastName());
                    String headshotUrl = personResponse.getHeadshot().getHeadshotUrl();
                    String id = personResponse.getId();
                    personList.add(new Person(name, headshotUrl, id));
                }
                responseHandler.onReceivePersonList(personList);
            }

            @Override
            public void onFailure(Call<List<PersonResponse>> call, Throwable t) {
                Log.d(TAG, "API call unsuccessful");
                t.printStackTrace();
            }
        });
    }

    public interface PersonListHandler {

        void onReceivePersonList(List<Person> personList);
    }

    private String formatName(String firstName, String lastName) {
        String name = firstName + " " + lastName;
        return name;
    }
}

