package cs1302.api;

import java.util.Arrays;

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
    private String imageUrl;

    /** Number of users which track the artist. */
    private int trackerCount;

    /** Number of upcoming events for artist. */
    private int upcomingEventCount;

    /** Artist music genre. */
    private String artistGenre;

    /** Arist country of origin. */
    private String countryOrigin;

    /** Members of band. */
    private String[] memberName;

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
    String getArtistName() {
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

    /**
     * Get genre.
     * @return genre
     */
    public String getGenre() {
        return artistGenre;
    }

    /** Gets country of origin.
     * @return country of origin
     */
    public String getCountryOfOrigin() {
        return countryOrigin;
    }

    /**
     * Gets members.
     * @return members if not null
     */
    public String[] getMembers() {
        return memberName != null ? Arrays.copyOf(memberName, memberName.length) : null;
    }

    // util methods

    /**
     * Check if the arist has shows.
     * @return true if there are shows
     */
    public boolean hasUpcomingShows() {
        return upcomingEventCount > 0;
    }

    /**
     * Checks if artist is a band with more than one member.
     * @return true if there are multiple members
     */
    public boolean isBand() {
        return memberName != null && memberName.length > 1;
    }

    /**
     * Gets display name for artist.
     * @return formatted display name
     */
    public String getDisplayName() {
        if (memberName != null && memberName.length > 0) {
            return name + " (" + String.join(", ", memberName) + ")";
        }
        return name;
    } // gpn

    @Override
    public String toString() {
        return String.format("Artist[name=%, genre=%s, country=%s, trackers=%d, upcomingEvents=%d]",
            name, artistGenre, countryOrigin, trackerCount, upcomingEventCount);
    }
} // artist
