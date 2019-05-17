package android.example.nicksnamegame.data.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeadShotResponseTest {

    private HeadShotResponse testHeadShotResponseNoPrefix, testHeadShotResponseWithPrefix;

    @Before
    public void setUp() {
        testHeadShotResponseNoPrefix = new HeadShotResponse(
                "//head.shot",
                "jpg",
                50,
                50);

        testHeadShotResponseWithPrefix = new HeadShotResponse(
                "http://head.shot",
                "jpg",
                50,
                50);
    }

    @Test
    public void testGetHeadShotUrl() {
        String testFormattedUrl = testHeadShotResponseNoPrefix.getHeadShotUrl();
        String testUntouchedUrl = testHeadShotResponseWithPrefix.getHeadShotUrl();

        assertEquals(
                "Head shot URL should be in proper URL format",
                "https://head.shot",
                testFormattedUrl);
        assertEquals(
                "Properly formatted head shot URL should not have been altered",
                "http://head.shot",
                testUntouchedUrl);
    }
}