
package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a meal from the TheMealDB API.
 * Used to parse JSON responses.
 */
public class Meal {

    /** Meal ID. */
    @SerializedName("idMeal")
    private String mealId;

    /** Meal categeory. */
    @SerializedName("strMeal")
    private String mealName;

    /** Meal category. */
    @SerializedName("strCategory")
    private String mealCategory;

    /** Meal cuisine area. */
    @SerializedName("strArea")
    private String cuisineArea;

    /** Instruct how to prep the meal. */
    @SerializedName("strInstructions")
    private String instruct;

    /** URL to thumbnail picture. */
    @SerializedName("strMealThumb")
    private String tnUrl;

    /**
     * Default constructor for Gson to use.
     */
    public Meal() {
        //
    }

    /** Getter for MealID.
     * @return mealId id for meal
     */
    public String getMealId() {
        return mealId;
    }

    /**
     * Getter for meal name.
     * @return mealName name of meal
     */
    public String getMealName() {
        return mealName;
    }

    /**
     * Getter for meal category.
     * @return mealCategory category of meal
     */
    public String getMealCategory() {
        return mealCategory;
    }

    /**
     * Getter for cuisine area.
     * @return cuisineArea area of cuisine
     */
    public String getCuisineArea() {
        return cuisineArea;
    }

    /**
     * Getter for instructions.
     * @return instruct intructions
     */
    public String getInstruct() {
        return instruct;
    }

    /**
     * Getter for tnurl.
     * @return tnUrl url for thumbnail
     */
    public String geTnUrl() {
        return tnUrl;
    }

    // util methods

    /**
     * Check if meal is originated from area.
     * @param region the country to check
     * @return true if meal matched region.
     */
    public boolean isOrigin(String region) {
        return cuisineArea != null && cuisineArea.equalsIgnoreCase(region);
    }

    /**
     * Checks if meal belongs to category.
     * @param category category to check
     * @return true if meal is same as category
     */
    public boolean isCategory(String category) {
        return this.mealCategory != null && this.mealCategory.equalsIgnoreCase(category);
    }

    /**
     * Gets recipe review for first 100 characters.
     * @return shortened recipe
     */
    public String getRecipePreview() {
        if (instruct == null || instruct.isEmpty()) {
            return "No recipe available";
        }
        if (instruct.length() <= 100) {
            return instruct;
        }
        return instruct.substring(0, 97) + "...";
    }

    /**
     * Cretaes display title for meal.
     * @return meal title
     */
    public String getTitle() {
        return String.format("%s (%s)", mealName, cuisineArea != null ? cuisineArea : "Unknown");
    }

    @Override
    public String toString() {
        return String.format("Meal[name=%s, category=%s, area=%s]",
        mealName, mealCategory, cuisineArea);
    }
} // public class meal
