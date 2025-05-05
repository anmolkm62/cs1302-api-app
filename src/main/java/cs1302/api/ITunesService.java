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
 * Class to interact with iTunes search API.
 */

public class ITunesService {

    /** Base url for itunes search api. */
    private static final String BASE_URL =  "https://itunes.apple.com/search";

    /** HTTP client for request. */
    private final HttpClient httpClient;

    /** Gson for parsing JSON. */
    private final Gson gson;

    /**
     * Constructor for iTunes service.
     */
    public ITunesService() {
        this.httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    } // its

    /**
     * Searches for songs by term.
     * @param term search term
     * @return ItunesResponse object
     */
    public ITunesResponse searchMusic (String term) {
        try {
            String encodedTerm = URLEncoder.encode(term, StandardCharsets.UTF_8);
            String url = BASE_URL + "?term=" + encodedTerm + "&media=music&entity=song&limit=20";

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), ITunesResponse.class);
            }
            return null;

        } catch (IOException | InterruptedException e) {
            System.err.println("Error searching iTunes: " + e.getMessage());
            return null;
        }
    }

    /**
     * Search for music via genre and country.
     * @param term search term
     * @param country ISO country code
     * @return ITunes response obj
     */
    public ITunesResponse searchMusicByCountryAndGenre(String term, String country) {
        try {
            String encodedTerm = URLEncoder.encode(term, StandardCharsets.UTF_8);
            String url = BASE_URL + "?term=" + encodedTerm +
                        "&media=music&entity=song&limit=20" +
                        "&country=" + country;

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .build();
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), ITunesResponse.class);
            }
            return null;
        } catch (IOException | InterruptedException e) {
            System.err.println("Error searching iTunes by country: " + e.getMessage());
            return null;
        }
    }


}
