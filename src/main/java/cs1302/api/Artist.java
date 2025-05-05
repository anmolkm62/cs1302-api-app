package cs1302.api;

import java.util.Arrays;
import com.google.gson.annotations.SerializedName;
/**
 * Artist from BandsInTown API.
 * This class is used by Gson to parse JSON response from API.
 */

public class Artist {

    /** The artists name. */
    private String name;

    /** URL to artist's page. */
    private String url;

    /** URL to image. */
    @SerializedName("image_url")
    private String imageUrl;

    /** URL to FB page. */
    @SerializedName("facebook_page_url")
    private String facebookUrl;

    /** Number of users which track the artist. */
    @SerializedName("tracker_count")
    private int trackerCount;

    /** Number of upcoming events for artist. */
    @SerializedName("upcoming_event_count")
    private int upcomingEventCount;




    /**
     * Default Gson constructor.
     */
    public Artist() {
        //
    }

    /**
     * Gets the artists name.
     * @return artist name
     */
    public String getArtistName() {
        return name;
    }

    /**
     * Gets artist URL.
     * @return URl
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets image URL.
     * @return image URL
     */
    public String getImageUrl() {
        return imageUrl;
    }


    /**
     * Gets number of users that track artist.
     * @return tracker count
     */
    public int getTrackerCount() {
        return trackerCount;
    }

    /**
     * Gets number of upcoming events.
     * @return upcoming event
     */
    public int getUpcomingShows() {
        return upcomingEventCount;
    }



    // util methods

    /**
     * Check if the arist has shows.
     * @return true if there are shows
     */
    public boolean hasUpcomingShows() {
        return upcomingEventCount > 0;
    }



    @Override
    public String toString() {
        return String.format("Artist[name=%s, trackers=%d, upcomingEvents=%d]",
            name, trackerCount, upcomingEventCount);
    }
} // artist
