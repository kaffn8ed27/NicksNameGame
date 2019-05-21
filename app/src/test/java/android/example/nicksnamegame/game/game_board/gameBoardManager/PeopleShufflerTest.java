package android.example.nicksnamegame.game.game_board.gameBoardManager;

import android.example.nicksnamegame.data.db.Person;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

        testPeopleShuffler = new PeopleShuffler(3, new Random());

        testPersonList.add(testPerson1);
        testPersonList.add(testPerson2);
        testPersonList.add(testPerson3);

        Log.d(TAG, testPersonList.toString());


        testShuffledList = testPeopleShuffler.chooseCoworkers(testPersonList);

        assertTrue("New shuffled list should contain first person", testShuffledList.contains(testPerson1));
        assertTrue("New shuffled list should contain second person", testShuffledList.contains(testPerson2));
        assertTrue("New shuffled list should contain third person", testShuffledList.contains(testPerson3));

    }

    @Test
    public void testEliminatesDuplicatePerson () {

        // seed the random with a constant so that we can predict the "random" numbers
        testPeopleShuffler = new PeopleShuffler(2, new Random(2));

        testPersonList.add(testPerson1);
        testPersonList.add(testPerson2);


        // in case the implementation of Random changes and we need to find a new seed
        Random random = new Random(2);
        assertEquals("First double: 0.7311469360199058",
                0.7311469360199058,
                random.nextDouble(),
                0.001);
        assertEquals("Second double: 0.9014476240300544",
                0.9014476240300544,
                random.nextDouble(),
                0.001);
        assertEquals("Third double: 0.49682259343089075",
                0.49682259343089075,
                random.nextDouble(),
                0.001);

        // will select "person2" as a candidate twice, but only add it to the new list once
        testShuffledList = testPeopleShuffler.chooseCoworkers(testPersonList);
        assertEquals("Shuffled list should contain no duplicates",
                Arrays.asList(testPerson2, testPerson1),
                testShuffledList);

    }

}