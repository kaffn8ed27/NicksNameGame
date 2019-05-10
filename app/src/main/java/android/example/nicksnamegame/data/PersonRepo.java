package android.example.nicksnamegame.data;

import android.example.nicksnamegame.data.db.Person;
import android.example.nicksnamegame.data.db.PersonDao;
import android.example.nicksnamegame.data.model.PersonConverter;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class PersonRepo {

    private final PersonDao personDao;
    private final PersonConverter personConverter;
    private static final String TAG = PersonRepo.class.getSimpleName();


    @Inject
    public PersonRepo(PersonDao personDao, PersonConverter personConverter){
        this.personDao = personDao;
        this.personConverter = personConverter;
    }

    public Single<List<Person>> retrievePersonList () {
        return Single.defer(() -> Single.just(personDao.loadPersonList()))
                .flatMap(people -> {
                    if (people.isEmpty()) {
                        return personConverter.retrievePersonList()
                                .map(personList -> {
                                    personDao.insertAll(personList);
                                    return personList;
                                });
                    } else {
                        Log.d(TAG, "Retrieving list from DB");
                        return Single.just(people);
                    }
                })
                .subscribeOn(Schedulers.io());
    }
}
