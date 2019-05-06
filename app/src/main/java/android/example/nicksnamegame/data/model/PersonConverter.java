package android.example.nicksnamegame.data.model;

import android.example.nicksnamegame.game.Person;
import android.example.nicksnamegame.data.NameGameApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonConverter {

    private static final String TAG = PersonConverter.class.getSimpleName();

    List<Person> mapResponse(List<PersonResponse> responseList) {

        ArrayList<Person> personList = new ArrayList<>();

        for (PersonResponse personResponse : responseList) {
            String name = formatName(personResponse.getFirstName(), personResponse.getLastName());
            String headShotUrl = personResponse.getHeadShot().getHeadShotUrl();
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

        // TODO: eliminate people whose picture does not have a face (Google Vision API?)

        return personList;
    }

    public Single<List<Person>> retrievePersonList() {
        NameGameApi api = new Retrofit.Builder()
                .baseUrl("https://www.willowtreeapps.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
                .create(NameGameApi.class);

        Single<List<PersonResponse>> apiCall = api.fetchPeopleList();
        Single<List<Person>> personSingle = apiCall.map(this::mapResponse);

        return personSingle;
    }

    private String formatName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
}

