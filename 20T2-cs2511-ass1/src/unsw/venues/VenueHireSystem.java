/**
 *
 */
package unsw.venues;

import java.time.LocalDate;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

import unsw.venues.Request;
import unsw.venues.Room;
import unsw.venues.Venue;
import unsw.venues.VenueList;

/**
 * Venue Hire System for COMP2511.
 *
 * A basic prototype to serve as the "back-end" of a venue hire system. Input
 * and output is in JSON format.
 *
 * @author Jiaqi Zhu
 *
 */
public class VenueHireSystem {

    /**
     * Constructs a venue hire system. Initially, the system contains no venues,
     * rooms, or bookings.
     */
    private VenueList venueList;

    public VenueHireSystem() {
        this.venueList = new VenueList();
    }

    private void processCommand(JSONObject json) {
        switch (json.getString("command")) {

        case "room":
            String venue = json.getString("venue");
            String room = json.getString("room");
            String size = json.getString("size");
            addRoom(venue, room, size);
            break;

        case "request":
            String id = json.getString("id");
            LocalDate start = LocalDate.parse(json.getString("start"));
            LocalDate end = LocalDate.parse(json.getString("end"));
            int small = json.getInt("small");
            int medium = json.getInt("medium");
            int large = json.getInt("large");

            JSONObject result = request(id, start, end, small, medium, large);

            System.out.println(result.toString(2));
            break;

        case "cancel":
            String ID = json.getString("id");
            cancel(ID);
            break;

        case "change":
            String identifier = json.getString("id");
            LocalDate start_date = LocalDate.parse(json.getString("start"));
            LocalDate end_date = LocalDate.parse(json.getString("end"));
            int s = json.getInt("small");
            int m = json.getInt("medium");
            int l = json.getInt("large");

            JSONObject res = change(identifier, start_date, end_date, s, m, l);

            System.out.println(res.toString(2));
            break;
        
        case "list":
            String v = json.getString("venue");
            JSONArray array = list(v);
            System.out.println(array.toString(2));
            break;
        }
    }

    private void addRoom(String venue, String room, String size) {
        // 1- empty venue list / venue list doesn't have the venue
        if (!venueList.hasVenue(venue)) {
            venueList.addVenue(venue);
            Room r = new Room(room, size);
            Venue v = venueList.getVenue(venue);
            v.addRoom(r);

        } else {
        // 2- there is such venue in the list
            Room r = new Room(room, size);
            for (Venue v : venueList.getVenueList()) {
                if (v.getName().equals(venue)) {
                    v.addRoom(r);
                    break;
                }
            }
        }
    }

    public JSONObject request(String id, LocalDate start, LocalDate end, int small, int medium, int large) {
        JSONObject result = new JSONObject();

        // TODO Process the request commmand
        // 1- Check if there is venue -> no venue -> rejected 
        if (venueList.getVenueList().size() == 0) {
            result.put("status", "rejected");
            return result;
        } 
        
        // Create a new Request
        Request curr = new Request(id, start, end, small, medium, large);
        
        boolean satisfied = false;
        String venue_name = venueList.getVenueList().get(0).getName();
        ArrayList<String> room_names = new ArrayList<String>();
        // Loop through venueList
        for (Venue venue : venueList.getVenueList()) {
            venue_name = venue.getName();
            room_names.clear();
            int small_needed = small;
            int medium_needed = medium;
            int large_needed = large;
            
            // 2- not enough room in current venue -> rejected
            if (venue.getRoomList().size() < small_needed+medium_needed+large_needed) {
                break;
            }

            // Check if there are available rooms that satisfy the size requirements
            for (Room room : venue.getRoomList()) {
                if (room.getSize().equals("small") && small_needed > 0 && room.checkAvailability(start, end)) {
                    small_needed--;
                    room_names.add(room.getName());
                } else if (room.getSize().equals("medium") && medium_needed > 0 && room.checkAvailability(start, end)) {
                    medium_needed--;
                    room_names.add(room.getName());
                } else if (room.getSize().equals("large") && large_needed > 0 && room.checkAvailability(start, end)) {
                    large_needed--;
                    room_names.add(room.getName());
                }
            }

            // 3- Rooms in current venue satisfy the requirements -> success
            if (small_needed == 0 && medium_needed == 0 && large_needed == 0) {
                satisfied = true;
                break;
            }
        }
        
        if (!satisfied) {
            result.put("status", "rejected");
            return result;
        }
        result.put("status", "success");
        result.put("venue", venue_name);
        curr.setVenue(venue_name);
        Venue curr_venue = venueList.getVenue(venue_name);JSONArray rooms = new JSONArray();
        for (String room : room_names) {
            curr.addAssignedRoom(room);
            rooms.put(room);
            for (Room r : curr_venue.getRoomList()) {
                if (r.getName().equals(room)) {
                    r.addRequest(curr);
                    break;
                }
            }
        }
        result.put("rooms", rooms);
        return result;     
    }

    public void cancel(String id) {
        for (Venue v : venueList.getVenueList()) {
            for (Room r : v.getRoomList()) {
                Request curr = r.getRequest(id);
                if (curr != null) {
                    r.cancelRequest(curr);
                }
            }
        }
    }

    public JSONObject change(String id, LocalDate start, LocalDate end, int small, int medium, int large) {
        JSONObject result = new JSONObject();

        // 1- Check if there is venue -> no venue -> rejected 
        if (venueList.getVenueList().size() == 0) {
            result.put("status", "rejected");
            return result;
        } 
        
        // Create a new Request
        Request curr = new Request(id, start, end, small, medium, large);
        
        boolean satisfied = false;
        String venue_name = venueList.getVenueList().get(0).getName();
        ArrayList<String> room_names = new ArrayList<String>();
        // Loop through venueList
        for (Venue venue : venueList.getVenueList()) {
            venue_name = venue.getName();
            room_names.clear();
            int small_needed = small;
            int medium_needed = medium;
            int large_needed = large;
            
            // 2- not enough room in current venue -> rejected
            if (venue.getRoomList().size() < small_needed+medium_needed+large_needed) {
                break;
            }

            // Check if there are available rooms that satisfy the size requirements
            for (Room room : venue.getRoomList()) {
                if (room.getSize().equals("small") && small_needed > 0 && room.checkAvailability(start, end)) {
                    small_needed--;
                    room_names.add(room.getName());
                } else if (room.getSize().equals("medium") && medium_needed > 0 && room.checkAvailability(start, end)) {
                    medium_needed--;
                    room_names.add(room.getName());
                } else if (room.getSize().equals("large") && large_needed > 0 && room.checkAvailability(start, end)) {
                    large_needed--;
                    room_names.add(room.getName());
                }
            }

            // 3- Rooms in current venue satisfy the requirements -> success
            if (small_needed == 0 && medium_needed == 0 && large_needed == 0) {
                satisfied = true;
                break;
            }
        }
        if (!satisfied) {
            result.put("status", "rejected");
            return result;
        }
        cancel(id);
        result = request(id, start, end, small, medium, large);
        return result;
    }

    public JSONArray list(String venue) {
        JSONArray result = new JSONArray();

        for (Room curr : venueList.getVenue(venue).getRoomList()) {
            JSONArray reservations = new JSONArray();
            JSONObject room = new JSONObject();
            room.put("room", curr.getName());
            for (Request r : curr.getRequests()) {
                JSONObject request = new JSONObject();
                request.put("id", r.getID());
                request.put("start", r.getStartDate());
                request.put("end", r.getEndDate());
                reservations.put(request);
            }
            room.put("reservations", reservations);
            result.put(room);
        }
        return result;
    }

    public static void main(String[] args) {
        VenueHireSystem system = new VenueHireSystem();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.trim().equals("")) {
                JSONObject command = new JSONObject(line);
                system.processCommand(command);
            }
        }
        sc.close();
    }

}
