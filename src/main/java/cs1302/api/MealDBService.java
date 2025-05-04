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
 * Class interacts with TheMealDb API;
 */
public class MealDBService {

    /** Base url the API. */
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    /** HTTP client for request. */
    private final HttpClient clientHttp;

    /** Gson for JSON pasing. */
    private final Gson gson;

    /**
     * Constructor for parsing.
     */
    public MealDBService() {
        this.clientHttp = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

          this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    }

    /**
     * Searches meal by their names.
     * @param mealN the name to search for
     * @return array of Meal obj or null if it is not found
    */
    public Meal[] searchMealsByName(String mealN) {
        try {
            String encodedName = URLEncoder.encode(mealN, StandardCharsets.UTF_8);
            String url = BASE_URL + "search.php?s=" + encodedName;

            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .build();
            HttpResponse<String> response = clientHttp.send(httpRequest, BodyHandlers.ofString());

            if (response.statusCode() == 200) {

                MealDBResponse mealResponse = gson.fromJson(response.body(), MealDBResponse.class);
                if (mealResponse != null && mealResponse.getMeals() != null) {
                    return mealResponse.getMeals();
                }
            } // if
            return null;
        } catch (IOException | InterruptedException e) {
            System.err.println("Error searching meals: " + e.getMessage());
            return null;
        }
    } // search meals by name

    /**
     * Gets meals based off of categories/
     * @param category the category of food
     * @return array of different meal objects if found else null
     */
    public Meal[] getMealsByCategory(String category) {
        try {
            String encodedCategory = URLEncoder.encode(category, StandardCharsets.UTF_8);
            String url = BASE_URL + "filter.php?c=" + encodedCategory;

            HttpRequest requestHttp = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .build();

            HttpResponse<String> response = clientHttp.send(requestHttp, BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                MealDBResponse mealResponse = gson.fromJson(response.body(), MealDBResponse.class);
                if (mealResponse != null && mealResponse.getMeals() != null) {
                    return mealResponse.getMeals();
                } // if
            } // if
            return null;
        } catch (IOException | InterruptedException e) {
            System.err.println("Error getting  meals by category: " + e.getMessage());
            return null;
        }
    }
}
