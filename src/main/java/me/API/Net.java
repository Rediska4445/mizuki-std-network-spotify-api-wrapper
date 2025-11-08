package me.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Net {
    public static Net netty = new Net();

    public HttpURLConnection openConnection(String url) throws IOException {
        URL obj = new URL(url);
        return (HttpURLConnection) obj.openConnection();
    }

    public String sendGETForFindRequest(String nameTrack) throws IOException {
        return sendGETForFindRequest(nameTrack, new Params(20));
    }

    public String sendGETForFindRequest(String nameTrack, Params par) throws IOException {
        return sendGETRequest(
                ("https://dubolt.com/api/search/?query="+ nameTrack + "&type=track&limit=" + par.limit)
                        .replaceAll(" ", "%20")
        );
    }

    public String sendGETRequest(String url) throws IOException {
        HttpURLConnection connection = openConnection(url);
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return response.toString();
    }
}
