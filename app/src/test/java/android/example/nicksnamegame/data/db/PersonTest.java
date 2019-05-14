package android.example.nicksnamegame.data.db;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    Person validPerson;
    String name = "Valid Person";
    String headShotUrl = "www.google.com";
    String id = "Test1";

    @Before
    public void setUp() {

        validPerson = new Person(name, headShotUrl, id);
    }

    @Test
    public void Person() {

        String testPersonName = validPerson.getName();
        String testPersonHeadShotUrl = validPerson.getHeadShotUrl();
        String testPersonId = validPerson.getId();

        assertEquals("Name correctly assigned", name, testPersonName);
        assertEquals("Head shot URL correctly assigned", headShotUrl, testPersonHeadShotUrl);
        assertEquals("ID correctly assigned", id, testPersonId);

    }

}