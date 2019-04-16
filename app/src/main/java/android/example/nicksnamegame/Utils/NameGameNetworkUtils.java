package android.example.nicksnamegame.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public final class NameGameNetworkUtils {

    private static final String API_URL = "http://www.willowtreeapps.com/api/v1.0/profiles";
    private JSONObject nameGameApiResponse;

    public static URL getUrl (Context context) {
        Uri employeeQueryUri = Uri.parse(API_URL).buildUpon().build();

        try {
            URL employeeQueryUrl = new URL(employeeQueryUri.toString());
            Log.d("NetworkUtils", "URL: " + employeeQueryUrl);
            return employeeQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromApi (URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = connection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            String response = hasInput ? scanner.next() : null;
            scanner.close();
            return response;
        } finally {
            connection.disconnect();
        }
    }
}
