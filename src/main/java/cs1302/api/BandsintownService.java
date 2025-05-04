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

    public Bandsintown
