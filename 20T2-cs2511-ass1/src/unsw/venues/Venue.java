package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;

import unsw.venues.Room;
import unsw.venues.Request;

/**
 * Venue Hire System - class Venue for COMP2511.
 *
 * A public class to serve as a venue that contains a list of rooms.
 *
 * @author Jiaqi Zhu
 *
 */
public class Venue {
    /**
     * Constructs a venue with a name and an ArrayList of Rooms.
     * Initially there is no room in the room list.
     */
    private String name;
    private ArrayList<Room> roomList;

    public Venue(String name) {
        this.name = name;
        this.roomList = new ArrayList<Room>();
    }

    /**
     * 
     * @return the name of venue.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the room list of venue.
     */
    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    /**
     * Adds a new room to the room list.
     */
    public void addRoom(Room new_room) {
        roomList.add(new_room);
    }

    /**
     * @return the room with input name.
     * If room with name does not exist, return null.
     */
    public Room getRoom(String name) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getName().equals(name)) {
                return roomList.get(i);
            }
        }
        return null;
    }
}