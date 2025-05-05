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
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Class to interact with LASTFM API.
 */
public class LastFMService {

    /** Base url for API. */
    private static final String BASE_URL = "https://ws.audioscrobbler.com/2.0/";

    /** http client for request. */
    private final HttpClient httpClient;

    /** Gson instance to parse. */
    private final Gson gson;

    /** API key to auth. */
    private final String apiKey;

    /** time of last api key for rate limits. */
    private long lastCallTime = 0;

    /** minimum time between calls in millisec. */
    private static final long MIN_TIME_BC = 200;

    /** counter for calls for tracking usage. */
    private int apiCC = 0;

    /**
     * Default constructor with test API key.
     */
    public LastFMService() {
        this.httpClient = HttpClient.newBuilder()
            .version(HttpClient.Redirect.Version.HTTP_2)
            .followedRedirects(HttpClient.Redirect.NORMAL)
            .build();
        // test api
        this.apiKey = "b25b959554ed76058ac220b7b2e0a026";
    }

    /**
     * Rate limits as it has a limit of 5 calls per second.
     */
    private void enforceRateLimit() {
        long currentTime = System.currentTimeMillis();
        long timeSLC = currentTime - lastCT;

        if (timeSLC < MIN_TIME_BC) {
            try{
                long waitTime = MIN_TIME_BC - timeSLC;
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } // if
        lastCT = System.currentTimeMillis();
        apiCC++;
    }

    /**
     * Get artist info from API.
     * @param artistN name of artist
     * @return Arist obj or null if not found
     */
    @SuppressWarnings("unchecked")
    public Artist getArtist(String artistN) {
        enforceRateLimit();

        try {
            String encodedName = URLEncoder.encode(artistN, StandardCharsets.UTF_8);
            String url = BASE_URL + "?method=artist.getinfo&artist=" + encodedName +
                "&api_key=" + apiKey + "&format=json";

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Map<String, Object> jsonResponse = gson.fromJson(response.body(), Map.class);
                if(jsonResponse.containsKey("artist")) {
                    Map<String, Object> artistObj =
                        (Map<String, Object>) jsonResponse.get("artist");
                    Artist artist = new Artist();

                    // extract basic fields
                    if (artistObj.containsKey("name")) {
                        artist.setName((String) artistObj.get("name"));
                    }
                    if (artistObj.containsKey("listeners")) {
                        artist.setListeners((String) artistObj.get("listeners"));
                    }
                    if (artistObj.containsKey("playcount")) {
                        artist.setPlaycount((String) artistObj.get("playcount"));
                    }

                    // extract img
                    if (artistObj.containsKey("image")) {
                        Object imageObj = artistObj.get("image");
                        if (imageObj instanceof List) {
                            List<Map<String, Object>> images = (List<Map<String, Object>>) imageObj;
                            for (Map<String, Object> img : images) {
                                if (img.containsKey("size") && img.get("size").equals("large")) {
                                    if (img.containsKey("#text")) {
                                        artist.setImageUrl((String) img.get("#text"));
                                        break;
                                        }
                                    }
                                }
                            }
                        }
                        if (artistObj.containsKey("url")) {
                            artist.setUrl((String) artistObj.get("url"));
                            }
                                return artist;
                        }
                    }
                    return null;
                } catch (IOException | InterruptedException e) {
                    System.err.println("Error retrieving artist data: " + e.getMessage());
                    return null;
                }
            }

    }
    }

    /**
     * Return top track for an artist.
     * @param artistN for name of artist.
     * @return array for top track names
     */
    @SuppressWarnings("unchecked")
    public String[] getTopTracks(String artistName) {
        enforceRateLimit();

        try {
            String encodedName = URLEncoder.encode(artistName, StandardCharsets.UTF_8);
            String url = BASE_URL + "?method=artist.gettoptracks&artist=" + encodedName +
                   "&api_key=" + apiKey + "&format=json&limit=5";

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Map<String, Object> jsonResponse = gson.fromJson(response.body(), Map.class);
                if(jsonResponse.containsKey("toptracks")) {
                    Map<String, Object> toptracks =
                        (Map<String, Object>) jsonResponse.get("toptracks");
                    if (toptracks.containsKey("track"))
                    Object trackElement = toptracks.get("track");
                    List<Map<String, Object>> trackList;

                    // handle single vs array
                    if (trackElement instanceof List) {
                        trackList = (List<Map<String, Object>>) trackElement;
                    } else {
                        trackList = List.of((Map<String, Object>) trackElement);
                    }

                    String[] tracks = new String[trackList.size()];
                    for (int i = 0; i < trackList.size(); i++) {
                        Map<String, Object> track = trackList.get(i);
                        tracks[i] = (String) track.get("name");
                    }
                    return tracks;
                    }
                }
            }
            return new String[0];
        } catch (IOException | InterruptedException e) {
            System.err.println("Error getting top tracks: " + e.getMessage());
            return new String[0];
        }
    }

    /**
     * Gets tracks for multiple artists to query second API.
     *
     * @param artistN array of artistnames
     * @return list of tracks for all artists
     */
    @SuppressWarnings("unchecked")
    public String[] getSimilarArtists(String artistN) {
        enforceRateLimit();

        try {
            String encodedName = URLEncoder.encode(artistN, StandardCharsets.UTF_8);
            String url = BASE_URL + "?method=artist.getsimilar&artist=" + encodedName +
                  "&api_key=" + apiKey + "&format=json&limit=5";

            HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Map<String, Object> jsonResponse = gson.fromJson(response.body(), Map.class);
                if (jsonResponse.containsKey("similarartists")) {
                    <Map<String, Object>> similarArtists =
                                                   (Map<String, Object>)
                                                   jsonResponse.get("similarartists");
                if (similarArtists.containsKey("artist")) {
                    List<Map<String, Object>> artistList =
                        (List<Map<String, Object>>) similarArtists.get("artist");
                    String[] artists = new String[artistList.size()];
                    for (int i = 0; i < artistList.size(); i++) {
                        Map<String, Object> artist = artistList.get(i);
                        artists[i] = (String) artist.get("name");
                    }
                    return artists;
                }
            }
            return new String[0];
        } catch (IOException | InterruptedException e) {
            System.err.println("Error getting similar artists: " + e.getMessage());
            return new String[0];
        }
    }

    /**
     * Gets tracks for multiple artists.
     *
     * @param artistNames array of artist names
     * @return list of tracks per artist
     */
    @SuppressWarnings("unchecked")
    public List<String> getTracksForMultipleArtists(String[] artistNames) {
        List<String> allTracks = new ArrayList<>();

        for (String artistN : artistNames) {
            String[] tracks = getTopTracks(artistN);
            for (String track : tracks) {
                allTracks.add(artistN + "-" + track);
            }
        }
        return allTracks;
    }

    /**
     * Get artists details and top track/similar artists.
     *
     * @param artistN name of artists
     * @return ArtistWithDetails obj with info top tracks and similar artists
     */
    public ArtistWithDetails getArtistWithDetails(String artistName) {
        Artist artist = getArtist(artistName);
        if (artist == null) {
            return null;
        }

        String [] topTracks = getTopTracks(artistName);
        String[] similarArtists = getSimilarArtists(artistName);

        return new ArtistWithDetails(artist, topTracks, similarArtists);
    }

    /**
     * Helper class with details for API integration.
     */
    public static class ArtistWithDetails {
        private final Artist artist;
        private final String[] topTracks;
        private final String[] similarArtists;

        public ArtistWithDetails(Artist artist, String[] topTracks, String[] similarArtists) {
            this.artist = artist;
            this.topTracks = topTracks;
            this.similarArtists = similarArtists;
        }

        public Artist getArtists() {
            return artist;
        }

        public String[] getTopTracks() {
            return topTracks;
        }

        public String[] getSimilarArtists() {
            return similarArtists;
        }
    }
}
