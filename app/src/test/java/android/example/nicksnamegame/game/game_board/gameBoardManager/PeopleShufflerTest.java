package android.example.nicksnamegame.game.game_board.gameBoardManager;

import android.example.nicksnamegame.data.db.Person;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PeopleShufflerTest {

    private static final String TAG = PeopleShufflerTest.class.getSimpleName();
    private PeopleShuffler testPeopleShuffler;
    private List<Person> testPersonList;
    private List<Person> testShuffledList;
    private Person testPerson1;
    private Person testPerson2;
    private Person testPerson3;

    @Before
    public void setUp() {

        testPersonList = new ArrayList<>();

        testPerson1 = new Person("Person One", "head.shot1", "Person1");
        testPerson2 = new Person("Person Two", "head.shot2", "Person2");
        testPerson3 = new Person("Person Three", "head.shot3", "Person3");

    }

    @Test
    public void testChooseCoworkersAllValid() {

        testPeopleShuffler = new PeopleShuffler(3);

        testPersonList.add(testPerson1);
        testPersonList.add(testPerson2);
        testPersonList.add(testPerson3);

        Log.d(TAG, testPersonList.toString());


        testShuffledList = testPeopleShuffler.chooseCoworkers(testPersonList);

        assertTrue("New shuffled list should contain first person", testShuffledList.contains(testPerson1));
        assertTrue("New shuffled list should contain second person", testShuffledList.contains(testPerson2));
        assertTrue("New shuffled list should contain third person", testShuffledList.contains(testPerson3));

    }

    // TODO: is it possible to "guarantee" that an attempt is made to add a duplicate person?

}