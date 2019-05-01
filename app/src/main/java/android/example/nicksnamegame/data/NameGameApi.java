package android.example.nicksnamegame.data;

import android.example.nicksnamegame.data.model.PersonResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NameGameApi {
    @GET ("/api/v1.0/profiles")
    Single<List<PersonResponse>> fetchPeopleList();
}
