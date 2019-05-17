package android.example.nicksnamegame.data.model;

import android.example.nicksnamegame.data.db.Person;
import android.example.nicksnamegame.data.NameGameApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonConverter {

    @Inject
    PersonConverter() {
    }

    private static final String TAG = PersonConverter.class.getSimpleName();


    List<Person> mapResponse(List<PersonResponse> responseList) {

        ArrayList<Person> personList = new ArrayList<>();

        for (PersonResponse personResponse : responseList) {
            String name = formatName(personResponse.getFirstName(), personResponse.getLastName());
            String headShotUrl = personResponse.getHeadShotUrl();
            String id = personResponse.getId();

            Person person = new Person(name, headShotUrl, id);
            if (headShotUrl == null) {
                Log.d(TAG, "Removing " + name + ": no head shot URL");
                continue;
            } else if (headShotUrl.contains("featured-image-TEST1.png") || headShotUrl.contains("WT_Logo-Hye-tTeI0Z.png")) {
                Log.d(TAG, "Removing " + name + ": invalid head shot URL");
                continue;
            }
            personList.add(person);
        }

        Log.d(TAG, "Removed " + (responseList.size() - personList.size()) + " people from list");

        return personList;

    }

    public Single<List<Person>> retrievePersonList() {
        Log.d(TAG, "Retrieving new list from API");
        NameGameApi api = new Retrofit.Builder()
                .baseUrl("https://www.willowtreeapps.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
                .create(NameGameApi.class);

        Single<List<PersonResponse>> apiCall = api.fetchPeopleList();

        return apiCall.map(this::mapResponse);
    }

    private String formatName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
}

