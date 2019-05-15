package android.example.nicksnamegame.data.db;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    private Person validPerson;
    private String name = "Valid Person";
    private String headShotUrl = "www.google.com";
    private String id = "Test1";

    @Before
    public void setUp() {

        validPerson = new Person(name, headShotUrl, id);
    }

    @Test
    public void Person() {

        String testPersonName = validPerson.getName();
        String testPersonHeadShotUrl = validPerson.getHeadShotUrl();
        String testPersonId = validPerson.getId();

        assertEquals("Name should match " + name, name, testPersonName);
        assertEquals("Head shot URL should match " + headShotUrl, headShotUrl, testPersonHeadShotUrl);
        assertEquals("ID should match " + id, id, testPersonId);

    }

}