package android.example.nicksnamegame.data.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HeadShotResponseTest {

    @Test
    public void testGetHeadShotUrlWithPrefix() {

        HeadShotResponse testHeadShotResponseWithPrefix = new HeadShotResponse(
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

        HeadShotResponse testHeadShotResponseNoPrefix = new HeadShotResponse(
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