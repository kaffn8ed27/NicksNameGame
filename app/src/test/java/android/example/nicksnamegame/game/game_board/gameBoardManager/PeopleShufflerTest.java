package android.example.nicksnamegame.game.game_board.gameBoardManager;

import android.example.nicksnamegame.data.db.Person;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PeopleShufflerTest {

    private PeopleShuffler peopleShuffler;
    private List<Person> personList;
    private Person person1, person2, person3;

    private static final String TAG = PeopleShufflerTest.class.getSimpleName();

    @Test
    public void chooseCoworkers() {

        Log.d(TAG, personList.toString());

        List<Person> testShuffledList = peopleShuffler.chooseCoworkers(personList);

        assertTrue("New shuffled list should contain first person", testShuffledList.contains(person1));
        assertTrue("New shuffled list should contain second person", testShuffledList.contains(person2));
        assertTrue("New shuffled list should contain third person", testShuffledList.contains(person3));

    }

    @Before
    public void setUp() {

        peopleShuffler = new PeopleShuffler(3);
        personList = new ArrayList<>();
        person1 = new Person("Person One", "head.shot1", "Person1");
        person2 = new Person("Person Two", "head.shot2", "Person2");
        person3 = new Person("Person Three", "head.shot3", "Person3");

        personList.add(person1);
        personList.add(person2);
        personList.add(person3);

    }
}