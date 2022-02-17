package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;

import unsw.venues.Room;
import unsw.venues.Request;
import unsw.venues.Venue;

/**
 * Venue Hire System - class VenueList for COMP2511.
 *
 * A public class to serve as a collection of present Venues.
 * Includes methods to operate the ArrayList of Venue.
 *
 * @author Jiaqi Zhu
 *
 */
public class VenueList {
    /**
     * Constructs an ArrayList of Venues.
     * Initially there is no venue in the list.
     */
    private ArrayList<Venue> venueList;
    
    public VenueList() {
        this.venueList = new ArrayList<Venue>();
    }

    /**
     * 
     * @return the venue list.
     */
    public ArrayList<Venue> getVenueList() {
        return venueList;
    }

    /**
     * Adds a new Venue to the venue list.
     */
    public void addVenue(String name) {
        Venue v = new Venue(name);
        venueList.add(v);
    }

    /**
     * @return true if venue list has a venue with input name,
     * otherwise false.
     */
    public boolean hasVenue(String name) {
        for (int i = 0; i < venueList.size(); i++) {
            if (venueList.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the Venue with input name.
     * If Venue with name does not exist in venue list, return null.
     */
    public Venue getVenue(String name) {
        for (int i = 0; i < venueList.size(); i++) {
            if (venueList.get(i).getName().equals(name)) {
                return venueList.get(i);
            }
        }
        return null;
    }
}