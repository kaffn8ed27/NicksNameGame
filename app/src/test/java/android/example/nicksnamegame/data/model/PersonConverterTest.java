package android.example.nicksnamegame.data.model;

import android.example.nicksnamegame.data.db.Person;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersonConverterTest {

    private static final String TAG = PersonConverterTest.class.getSimpleName();

    private PersonConverter testPersonConverter;
    private List<PersonResponse> testPersonResponseList;
    private List<Person> testPersonList;

    @Before
    public void setUp() {

        testPersonConverter = new PersonConverter();
        testPersonResponseList = new ArrayList<>();

    }

    @Test
    public void testMapResponseSuccess() {

        // golden path scenario: head shot URL is valid, will be added to list
        String testValidHeadShotUrl = "head.shot";
        String testPersonFirstName = "Person";
        String testPersonLastName = "One";

        // using Mockito to create a mock head shot response object
        HeadShotResponse mockValidHeadshotResponse = mock(HeadShotResponse.class);
        // mock head shot returns dummy URL for testing purposes
        when(mockValidHeadshotResponse.getHeadShotUrl()).thenReturn(testValidHeadShotUrl);
        PersonResponse testValidPersonResponse = new PersonResponse("TestPerson1",
                testPersonFirstName,
                testPersonLastName,
                mockValidHeadshotResponse);
        testPersonResponseList.add(testValidPersonResponse);

        testPersonList = testPersonConverter.mapResponse(testPersonResponseList);
        Person testValidPerson = testPersonList.get(0);

        assertEquals("Output name should match input first + last name",
                testPersonFirstName + " " + testPersonLastName,
                testValidPerson.getName());

        assertEquals("Output URL should match head shot URL",
                testValidHeadShotUrl,
                testValidPerson.getHeadShotUrl());

    }

    @Test
    public void testMapResponseEliminations() {

        /* failure 1 & 2 scenario: known head shot URLs that point to generic pictures
         * should not be added to list
         */
        HeadShotResponse mockInvalidHeadShotResponse1 = mock(HeadShotResponse.class);
        String invalidHeadShotUrl1 = "www.WillowTreeApps.com/featured-image-TEST1.png";
        when(mockInvalidHeadShotResponse1.getHeadShotUrl()).thenReturn(invalidHeadShotUrl1);
        PersonResponse testBadUrlPersonResponse1 = new PersonResponse(
                "TestPerson2",
                "Bad",
                "Head Shot",
                mockInvalidHeadShotResponse1);
        testPersonResponseList.add(testBadUrlPersonResponse1);

        HeadShotResponse mockInvalidHeadShotResponse2 = mock(HeadShotResponse.class);
        String invalidHeadShotUrl2 = "www.WillowTreeApps.com/WT_Logo-Hye-tTeI0Z.png";
        when(mockInvalidHeadShotResponse2.getHeadShotUrl()).thenReturn(invalidHeadShotUrl2);
        PersonResponse testBadUrlPersonResponse2 = new PersonResponse(
                "TestPerson3",
                "Bad",
                "Head Shot",
                mockInvalidHeadShotResponse2);
        testPersonResponseList.add(testBadUrlPersonResponse2);

        /* failure 3 scenario: no head shot URL
         * should not be added to list
         */
        HeadShotResponse mockMissingHeadShotResponse = mock(HeadShotResponse.class);
        when(mockMissingHeadShotResponse.getHeadShotUrl()).thenReturn(null);
        PersonResponse testMissingUrlPersonResponse = new PersonResponse(
                "TestPerson4",
                "Missing",
                "Head Shot",
                mockMissingHeadShotResponse);
        testPersonResponseList.add(testMissingUrlPersonResponse);

        Log.d(TAG, testPersonResponseList.toString());
        // PersonConverter should reject all of the above responses and return an empty list
        testPersonList = testPersonConverter.mapResponse(testPersonResponseList);

        assertTrue(
                "PersonConverter should not allow invalid or missing head shot URL",
                testPersonList.isEmpty());
    }

}