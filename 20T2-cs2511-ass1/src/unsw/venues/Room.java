package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import unsw.venues.Request;

/**
 * Venue Hire System - class Room for COMP2511.
 *
 * A public class to serve as a room with size 
 * that contains a list of bookings(requests).
 *
 * @author Jiaqi Zhu
 *
 */
public class Room {
    /**
     * Constructs a room with a name, size and a list of reservations.
     * Initially there is no reservation present. 
     */
    private String name;
    private String size;
    private ArrayList<Request> requests;

    public Room(String name, String size) {
        this.name = name;
        this.size = size;
        this.requests = new ArrayList<Request>();
    }

    /**
     *
     * @return the size of room
     */
    public String getSize() {
        return size;
    }

    /**
     *
     * @return the name of room
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the list of reservations
     */
    public ArrayList<Request> getRequests() {
        return requests;
    }

    /**
     * Adds a new Request as a booking to the reservation list.
     * The reservation list is sorted (earlier dates before later dates).
     */
    public void addRequest(Request new_request) {
        requests.add(new_request);
        Collections.sort(requests);
    }

    /**
     * Removes a booking from list.
     */
    public void cancelRequest(Request target) {
        requests.remove(target);
    }

    /**
     * @return the request with input id
     * If there is no such request with id, return null.
     */
    public Request getRequest(String id) {
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getID().equals(id)) {
                return requests.get(i);
            }
        }
        return null;
    }

    /**
     * Checks if the room is available between the start and end dates.
     */
    public boolean checkAvailability(LocalDate start, LocalDate end) {
        int size = requests.size();
        if (size == 0 || end.isBefore(requests.get(0).getStartDate()) || start.isAfter(requests.get(size-1).getEndDate())) {
            return true;
        } 
        else if (size == 1) {
            if (requests.get(0).getEndDate().isBefore(start) || requests.get(0).getStartDate().isAfter(end)) {
                return true;
            } else {
                return false;
            }
        }
        else {
            for (int i = 1; i < size; i++) {
                if (requests.get(i).getStartDate().isAfter(end) && requests.get(i-1).getEndDate().isBefore(start)) {
                    return true;
                }
            }
            return false;
        }
    }
}