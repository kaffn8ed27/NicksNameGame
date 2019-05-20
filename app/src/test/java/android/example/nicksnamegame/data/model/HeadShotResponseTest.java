package android.example.nicksnamegame.data.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HeadShotResponseTest {

    private HeadShotResponse testHeadShotResponseNoPrefix, testHeadShotResponseWithPrefix;

    @Test
    public void testGetHeadShotUrlWithPrefix() {

        testHeadShotResponseWithPrefix = new HeadShotResponse(
                "http://head.shot",
                "jpg",
                50,
                50);

        String testUntouchedUrl = testHeadShotResponseWithPrefix.getHeadShotUrl();
        assertEquals(
                "Properly formatted head shot URL should not have been altered",
                "http://head.shot",
                testUntouchedUrl);
    }

    @Test
    public void testGetHeadShotUrlNoPrefix() {

        testHeadShotResponseNoPrefix = new HeadShotResponse(
                "//head.shot",
                "jpg",
                50,
                50);

        String testFormattedUrl = testHeadShotResponseNoPrefix.getHeadShotUrl();
        assertEquals(
                "Head shot URL should be in proper URL format",
                "https://head.shot",
                testFormattedUrl);



    }
}