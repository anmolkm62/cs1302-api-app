package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents an event from Bandsintown API.
 */

public class Event {

    /** Event ID. */
    public String id;

    /** Date/time when event is occuring. */
    private String datetime;

    /** venue info. */
    private Venue venue;

    /** event title. */
    private String title;

    /**
     * Venue info.
     */
    public static class Venue {
        private String name;

        private String location;

        private String country;

        private String city;

        /**
         * Constructor for Gson.
         */
        public Venue() {
            //
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return country;
        }

        public String getCity() {
            return city;
        }

        @Override
        public String toString() {
            return String.format("Venue[name=%s, location=%s}", name, location);
        }
    } // venue

    /**
     * Constructor for Gson.
     */
    public Event() {
        // required for Gson
    }

    public String getId() {
        return id;
    }

    public String getDatetime() {
        return datetime;
    }

    public Venue getVenue() {
        return venue;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.format("Event[title=%s, datetime=%s, venue=%s]", title, datetime, venue);
    }
} // event
