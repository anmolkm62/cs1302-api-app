package cs1302.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;

/**
 * Service class to interact with API.
 */
public class BandsintownService {

    /** base url for api. */
    private static final String BASE_URL = "https://rest.bandsintown.com/artists/";

    /** HTTP client for making requests. */
    private final HttpClient clientHttp;

    /** Gson instance for pasring. */
    private final Gson gson;

    /**
     * Constructor.
     */

    public BandsintownService() {
        this.clientHttp = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    }

    /**
     * Returns artist info from Bandsintown API.
     * @param artistN the name of artist
     * @return Artist object or null if failed to find
     */
    public Artist getArtist(String artistN) {
        try {
            String encodedName = URLEncoder.encode(artistN, StandardCharsets.UTF_8);
            String url = BASE_URL + encodedName;

            HttpRequest requestHttp = HttpRequest.newBuilder()
                 .uri(URI.create(url))
                 .header("Accept", "application/json")
                 .build();

            HttpResponse<String> response = clientHttp.send(requestHttp, BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), Artist.class);
            }
            return null;
        } catch (IOException | InterruptedException e) {
            System.err.println("Error retrieving artist data: " + e.getMessage());
            return null;
        }
    }

    /**
     * Returns upcoming events for a specific artist.
     * @param artistN the name of artist
     * @return array of EVent obj or null if failed to find
     */
    public Event[] getArtistEvents(String artistN) {
        try {
            String encodedName = URLEncoder.encode(artistN, StandardCharsets. UTF_8);
            String url = BASE_URL + encodedName + "/events";

            HttpRequest requestHttp = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application.json")
                .build();

            HttpResponse<String> response = clientHttp.send(requestHttp, BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), Event[].class);
            }
            return null;
        } catch (IOException | InterruptedException e) {
            System.err.println("Error getting events data: " + e.getMessage());
                return null;
        }
    }


}
