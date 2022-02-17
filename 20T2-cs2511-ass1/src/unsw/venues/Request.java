package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Venue Hire System - class Request for COMP2511.
 *
 * A public class to serve as a request that contains an identifier,
 * start date, end date, number of rooms in sizes small, medium and large.
 * Each request also contains references to the assigned venue and rooms.
 * 
 * This class implements the Comparable interface and overrides 
 * the compareTo() method to compare its start date 
 * (earlier dates before later dates).
 *
 * @author Jiaqi Zhu
 *
 */
public class Request implements Comparable<Request>{
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int small;
    private int medium;
    private int large;
    private String assignedVenue;
    private ArrayList<String> assignedRooms;

    /**
     * Constructs a new request/booking with all its parameters.
     * Initially the request does not have assigned venue or rooms.
     */
    public Request(String id, LocalDate startDate, LocalDate endDate, int small, int medium, int large){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.small = small;
        this.medium = medium;
        this.large = large;
        this.assignedRooms = new ArrayList<String>();
    }

    /**
     * Sets the name of the assigned venue.
     */
    public void setVenue(String name) {
        this.assignedVenue = name;
    }

    /**
     * @return the start date of booking
     */
    public LocalDate getStartDate(){
        return startDate;
    }

    /**
     * @return the end date of booking
     */
    public LocalDate getEndDate(){
        return endDate;
    }

    /**
     * @return the id of booking
     */
    public String getID() {
        return id;
    }

    /**
     * @return the name of assigned venue
     */
    public String getVenue() {
        return assignedVenue;
    }

    /**
     * @return the list of names of assigned rooms
     */
    public ArrayList<String> getRooms() {
        return assignedRooms;
    }

    /**
     * Adds an assigned room to the room list.
     */
    public void addAssignedRoom(String name) {
        assignedRooms.add(name);
    }

    /**
     * @return true if date A is earlier than date B, otherwise false
     */
    public boolean isBefore(LocalDate A, LocalDate B) {
        if (A.compareTo(B) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true if date A is later than date B, otherwise false
     */
    public boolean isAfter(LocalDate A, LocalDate B) {
        if (A.compareTo(B) < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Overrides the compareTo() method in order to compare two requests
     * based on the start date (ealier dates before later dates).
     */
    public int compareTo(Request compareRequest) {
        LocalDate curr = this.startDate;
        LocalDate compareCurr = ((Request)compareRequest).getStartDate();
        return curr.compareTo(compareCurr);
    }
}