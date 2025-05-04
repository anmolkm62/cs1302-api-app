package cs1302.api;

/**
 * Response from TheMealBN API.
 */
public class MealDBResponse {

    /** Array of meals from response. */
    private Meal[] meals;

    /**
     * Default constructor for Gson.
     */
    public MealDBResponse() {
        //
    }

    /**
     * Gets meals from response.
     * @return array of meals
     */
    public Meal[] getMeals() {
        return meals;
    }
}
