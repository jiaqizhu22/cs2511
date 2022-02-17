package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Door extends Entity {
    private int id;
    private BooleanProperty opened;

    public Door(int x, int y, int id) {
        super(x, y, false);
        this.id = id;
        this.opened = new SimpleBooleanProperty(false);
    }

    public int getID() {
        return id;
    }

    public BooleanProperty isOpened() {
        return opened;
    }

    public void openDoor() {
        this.opened.set(true);
    }

    @Override
    public void update(Dungeon dungeon, Player player) {
        if (player.getX() == this.getX() && player.getY() == this.getY()) {
            // Door will remain open
            openDoor();
            // Remove key from player's inventory
            int doorID = getID();
            Entity key = player.getKeyById(doorID);
            player.removeItemFromInventory(key);
            
        }

    }
}