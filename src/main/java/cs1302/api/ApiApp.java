package cs1302.api;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import javafx.geometry.Pos;

/**
 * This App is a culinary music app! It allows users to browse different recipes,
 * then based off of the recipe it matches the user with relevant music.
 */
public class ApiApp extends Application {
    private Stage appWindow;
    private Scene appScene;
    private BorderPane mainLayout;

    // back end
    private MealDBService foodService;
    private ITunesService musicService;

    // ui
    private VBox mainContentContainer;
    private TextField foodSearchInput;
    private Button findButton;
    private ProgressBar loadingIndicator;
    private Label statusMessage;
    private HBox resultsDisplayArea;
    private VBox recipeDetailsPanel;
    private VBox musicPlaylistPanel;
    private ImageView dishPhotoView;

    // current state of app
    private Meal chosenRecipe;
    private ITunesResult[] matchingTracks;
    private String activeScreen;

    public void init() {
        this.mainLayout = new BorderPane();
        this.foodService = new MealDBService();
        this.musicService = new ITunesService();

        createUI();
    } // init
    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
//     */
        //    public ApiApp() {
        //        root = new VBox();
        //    } // ApiApp

    /** {@inheritDoc} */
    @Override
    public void start(Stage appWindow) {
        this.appWindow = appWindow;
        this.appWindow.setTitle("The Taste of Music!");
        this.appScene = new Scene(mainLayout, 1280, 720);
        this.appWindow.setScene(appScene);
        this.appWindow.setResizable(false);
        this.appWindow.sizeToScene();
        this.appWindow.show();

        showHomePage();

    } // start

    /**
     * These are the main user components.
     */
    private void createUI() {
        createHeader();
        createCenter();
        createFooter();
    } // createUI

    /**
     * Header with search functionality.
     */
    private void createHeader() {
        VBox topSectionBox = new VBox();
        topSectionBox.setPadding(new Insets(20, 10, 10, 10));
        topSectionBox.setAlignment(Pos.CENTER);
        topSectionBox.setSpacing(10);

        // title
        Label appHeading = new Label("The Taste of Music");
        // maybe add font

        // search bar
        HBox searchInputArea = new HBox();
        searchInputArea.setAlignment(Pos.CENTER);
        searchInputArea.setSpacing(10);
        searchInputArea.setPadding(new Insets(10));

        foodSearchInput = new TextField();
        foodSearchInput.setPromptText("Enter food name (for example: chicken, pizza)");
        foodSearchInput.setPrefWidth(500);

        findButton = new Button("Go");
        findButton.setOnAction(e -> performSearch());
        foodSearchInput.setOnAction(e -> performSearch());

        searchInputArea.getChildren().addAll(foodSearchInput, findButton);

        // progress bar with updates
        loadingIndicator = new ProgressBar();
        loadingIndicator.setPrefWidth(500);
        loadingIndicator.setVisible(false);

        topSectionBox.getChildren().addAll(appHeading, searchInputArea, loadingIndicator);
        mainLayout.setTop(topSectionBox);
    } // header

    /**
     * Center content area of app.
     */
    private void createCenter() {
        mainContentContainer = new VBox();
        mainContentContainer.setAlignment(Pos.TOP_CENTER);
        mainContentContainer.setSpacing(20);
        mainContentContainer.setPadding(new Insets(25));

        // main content panel
        resultsDisplayArea = new HBox();
        resultsDisplayArea.setSpacing(20);
        resultsDisplayArea.setAlignment(Pos.CENTER);

        // recipe
        recipeDetailsPanel = new VBox();
        recipeDetailsPanel.setPrefWidth(600);
        // maybe add a style here
        recipeDetailsPanel.setSpacing(10);

        // music panel
        musicPlaylistPanel = new VBox();
        musicPlaylistPanel.setPrefWidth(600);
        musicPlaylistPanel.setSpacing(10);

        resultsDisplayArea.getChildren().addAll(recipeDetailsPanel, musicPlaylistPanel);
        mainContentContainer.getChildren().add(resultsDisplayArea);
        mainLayout.setCenter(mainContentContainer);
    } // create center

    /**
     * Create footer with info about status.
     */
    private void createFooter() {
        HBox footerBox = new HBox();
        footerBox.setPadding(new Insets(10));
        footerBox.setAlignment(Pos.CENTER_LEFT);

        statusMessage = new Label("Ready!");
        footerBox.getChildren().add(statusMessage);
        mainLayout.setBottom(footerBox);
    } // create footer

    private void showHomePage() {
        activeScreen = "home";
        resultsDisplayArea.setVisible(false);

        VBox homePage = new VBox();
        homePage.setAlignment(Pos.CENTER);
        homePage.setSpacing(20);
        homePage.setPadding(new Insets(50));

        Label welcomeLabel = new Label ("Welcome to the Taste of Music!");
        welcomeLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Label instructionLabel = new Label(
            "Discover recipes across the world and music to go with it!\n\n" +
            "How to navigate:\n" +
            "Step 1: Enter a food name in search bar above\n" +
            "Step 2: Browse through and find a recipe you like\n" +
            "Step 3: Chose your recipe and find your music to match!\n" +
            "Enjoy!");
        instructionLabel.setStyle("-fx-font-size: 16px;");
        instructionLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        instructionLabel.setWrapText(true);
        instructionLabel.setMaxWidth(750);

        homePage.getChildren().addAll(welcomeLabel, instructionLabel);
        mainContentContainer.getChildren().clear();
        mainContentContainer.getChildren().add(homePage);
    } // showHomePage

    /**
     * Use MealDB to perform food search.
     */
    private void performSearch() {
        String userQuery = foodSearchInput.getText().trim();

        if (userQuery.isEmpty()) {
            showError("Error", "Please enter a food name to search.");
            return;
        } // if

        activeScreen = "search";
        loadingIndicator.setVisible(true);
        loadingIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        findButton.setDisable(true);
        statusMessage.setText("Searching for recipes...stay tuned");

        Runnable foodSearchTask = () -> {
            Meal[] foundRecipes = foodService.searchMealsByName(userQuery);
            Platform.runLater(() -> {
                loadingIndicator.setVisible(false);
                loadingIndicator.setProgress(0);
                findButton.setDisable(false);

                if (foundRecipes == null || foundRecipes.length == 0) {
                    showError("No results", "No recipes found for " + userQuery + ".");
                    statusMessage.setText("No results found.");
                } else {
                    displaySearchResults(foundRecipes);
                    statusMessage.setText("Found " + foundRecipes.length + " recipe(s).");
                }
            });
        };

        Thread searchWorker = new Thread(foodSearchTask);
        searchWorker.setDaemon(true);
        searchWorker.start();
    } // performSearch

    /**
     * Displays search results based on user selection.
     * @param foundRecipes array of results
     */
    private void displaySearchResults(Meal[] foundRecipes) {
        activeScreen = "results";
        resultsDisplayArea.setVisible(true);

        VBox foundRecipesContainer = new VBox();
        foundRecipesContainer.setAlignment(Pos.TOP_CENTER);
        foundRecipesContainer.setSpacing(10);
        foundRecipesContainer.setPadding(new Insets(20));

        Label foundRecipesHeading = new Label ("Search Results");
        foundRecipesHeading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        ScrollPane recipesScrollArea = new ScrollPane();
        recipesScrollArea.setFitToWidth(true);
        recipesScrollArea.setPrefHeight(300);

        VBox recipesListContainer = new VBox(10);

        for (Meal recipe : foundRecipes) {
            Button recipeButton = new Button(recipe.getTitle());
            recipeButton.setPrefWidth(500);
            recipeButton.setOnAction(e -> selectMeal(recipe));
            recipesListContainer.getChildren().add(recipeButton);
        } // for

        recipesScrollArea.setContent(recipesListContainer);
        foundRecipesContainer.getChildren().addAll(foundRecipesHeading, recipesScrollArea);
        mainContentContainer.getChildren().clear();
        mainContentContainer.getChildren().addAll(foundRecipesContainer, resultsDisplayArea);

        // clear panels
        recipeDetailsPanel.getChildren().clear();
        musicPlaylistPanel.getChildren().clear();
    } // DPS

    /**
     * Handles meal selection then retrieves music.
     * @param selectedDish selected meal object
     */
    private void selectMeal(Meal selectedDish) {
        chosenRecipe = selectedDish;
        statusMessage.setText("Searching for relevant music...");
        loadingIndicator.setVisible(true);
        loadingIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

        Runnable musicSearchTask = () -> {
            ITunesResponse musicSearch = null;
            if (selectedDish.getCuisineArea() !=
                null && !selectedDish.getCuisineArea().equalsIgnoreCase("Unknown")) {
                musicSearch = musicService.searchMusic(selectedDish.getCuisineArea());
            } // if
            final ITunesResponse foundMusic = musicSearch;
            Platform.runLater(() -> {
                loadingIndicator.setVisible(false);
                loadingIndicator.setProgress(0);
                displayMealAndMusic(selectedDish, foundMusic);

                if (foundMusic != null && foundMusic.getResults() != null) {
                    statusMessage.setText("Found " +
                        foundMusic.getResults().length + " music tracks.");
                } else {
                    statusMessage.setText("No music found.");
                } // if
            });
        };

        Thread musicWorker = new Thread(musicSearchTask);
        musicWorker.setDaemon(true);
        musicWorker.start();
    } // selectMeal

    /**
     * Display chosen meal and music info.
     * @param dishToDisplay selected meal
     * @param foundMusicResults music search response
     */
    private void displayMealAndMusic(Meal dishToDisplay, ITunesResponse foundMusicResults) {
        recipeDetailsPanel.getChildren().clear();

        Label dishNameLabel = new Label ("Recipe: " + dishToDisplay.getMealName());
        dishNameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label cuisineTypeLabel = new Label("Cuisine: " + dishToDisplay.getCuisineArea());
        Label foodCategoryLabel = new Label("Category: " + dishToDisplay.getMealCategory());

        // recipe image
        Label instructionsLabel = new Label("Instructions:");
        instructionsLabel.setStyle("-fx-font-weight: bold;");

        TextArea cookingInstructionsArea = new TextArea(dishToDisplay. getInstruct());
        cookingInstructionsArea.setWrapText(true);
        cookingInstructionsArea.setEditable(false);
        cookingInstructionsArea.setPrefHeight(150);

        recipeDetailsPanel.getChildren().addAll(dishNameLabel,
            cuisineTypeLabel, foodCategoryLabel, instructionsLabel, cookingInstructionsArea);

        // music pane;
        musicPlaylistPanel.getChildren().clear();

        Label musicHeading = new Label("Relevant Music");
        musicHeading.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        if (foundMusicResults != null && foundMusicResults.getResults() != null &&
        foundMusicResults.getResults().length > 0) {
            ScrollPane playlistScrollPane = new ScrollPane();
            playlistScrollPane.setFitToWidth(true);
            playlistScrollPane.setPrefHeight(350);

            VBox trackList = new VBox(10);

            for (ITunesResult song : foundMusicResults.getResults()) {
                VBox trackItemBox = new VBox(5);
                trackItemBox.setStyle("-fx-background-color:white; -fx-padding: 10;");

                Label songName = new Label(song.getTrackName());
                songName.setStyle("-fx-font-weight: bold;");

                Label artistName = new Label("Artist: " + song.getArtistName());
                Label albumName = new Label("Album: " + song.getCollectionName());
                Label duration = new Label("Duration: " + song.getTrackDuration());

                trackItemBox.getChildren().addAll(songName, artistName, albumName, duration);
                trackList.getChildren().add(trackItemBox);
            } // for

            playlistScrollPane.setContent(trackList);
            musicPlaylistPanel.getChildren().addAll(musicHeading, playlistScrollPane);
        } else {
            Label noMusic = new Label("No relevant music found for " +
            dishToDisplay.getCuisineArea());
            musicPlaylistPanel.getChildren().addAll(musicHeading, noMusic);
        } // if
    } //dmam

    /**
     * Shows error.
     * @param dialogHeading error error title
     * @param errorDetails error message
     */
    private void showError(String dialogHeading, String errorDetails) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(dialogHeading);
        alert.setHeaderText(errorDetails);
        alert.showAndWait();
    } // error
}   // ApiApp
