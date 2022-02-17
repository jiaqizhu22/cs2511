package unsw.dungeon;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Entity> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(Entity entity) {
        items.add(entity);
    }

    public void removeItem(Entity entity) {
        items.remove(entity);
    }

    public ArrayList<Entity> getItems() {
        return items;
    }

    public Entity getKeyById(int id) {
        for (Entity e : items) {
            if (e instanceof Key) {
                Key key = (Key) e;
                if (key.matchID(id)) {
                    return e;
                }
            }
        }
        return null;
    }

    public int getTreasureNumber() {
        int count = 0;
        for (Entity e : items) {
            if (e instanceof Treasure) {
                count++;
            }
        }
        return count;
    }
}