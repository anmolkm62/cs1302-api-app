package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * A representation of singleiTunes search result.
 */
public class ITunesResult {

    /** Track id. */
    @SerializedName("trackId")
    private String trackId;

    /** Track name. */
    @SerializedName("trackName")
    private String trackName;

    /** Artist name. */
    @SerializedName("artistName")
    private String artistName;

    /** Album name. */
    @SerializedName("collectionName")
    private String collectionName;

    /** Preview of URL. */
    @SerializedName("previewUrl")
    private String previewUrl;

    /** Artwork url. */
    @SerializedName("artworkUrl100")
    private String artworkUrl;

    /** Track time in MS. */
    @SerializedName("trackTimeMillis")
    private int trackTimeMillis;

    /** Primary genre. */
    @SerializedName("primaryGenreName")
    private String primaryGenreName;

    /** Release dates. */
    @SerializedName("releaseDate")
    private String releaseDate;

    /**
     * Gson default constructor.
     */
    public ITunesResult() {
        //
    }

    /**
     * Gets trackid.
     * @return trackId
     */
    public String getTrackId() {
        return trackId;
    }

    /**
     * Gets track name.
     * @return trackName
     */
    public String getTrackName() {
        return trackName;
    }

    /**
     * Gets artist name.
     * @return artistName
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Get collection name.
     * @return collection name
     */
    public String getCollectionName() {
        return collectionName;
    }

    /**
     * Get artwork url.
     * @return artworkURL
     */
    public String getPreviewUrl() {
        return previewUrl;
    }

    /**
     * Get artwork url.
     * @return artworkUrl
     */
    public String getArtWorkUrl() {
        return artworkUrl;
    }


    /**
     * Gets track time in milliseconds.
     * @return trackTimeMillis
     */
    public int getTrackTimeMillis() {
        return trackTimeMillis;
    }

    /**
     * Returns primary genre.
     * @return primaryGenreName name of primary genre
     */
    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    /**
     * Returns release date.
     * @return releaseDate release date
    */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Gets track duration.
     * @return trck duration as mm : ss
     */
    public String getTrackDuration() {
        if (trackTimeMillis <= 0) {
            return "unknown";
        }
        int totalSec = trackTimeMillis / 1000;
        int min = totalSec / 60;
        int sec = totalSec % 60;
        return String.format("%d:%02d", min, sec);
    }

    @Override
    public String toString() {
        return String.format("ITunesResult[track=%s, artist=%s, album=%s]",
            trackName, artistName, collectionName);
    }
}
