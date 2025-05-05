package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Wrapper class for ITunes API.
 */
public class ITunesResponse {
    /** Number of results. */
    @SerializedName("resultCount")
    private int resultCount;

    /** Array of results. */
    @SerializedName("results")
    private ITunesResult[] results;

    /**
     * Default Gson constructor.
     */
    public ITunesResponse() {
     //
    }

    /**
     * Returns result count.
     * @return resultCount the result count
     */
    public int getResultCount() {
        return resultCount;
    }

    /**
     * Returns results from itunes.
     * @return results result from API
     */
    public ITunesResult[] getResults() {
        return results;
    }

    @Override
    public String toString() {
        return String.format("ITunesResponse[resultCount=%d]", resultCount);
    }
}
