package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Entity implements EntityObserver {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private BooleanProperty showOnMap;
    private boolean collectable;

    /**
     * Create an entity positioned in square (x,y)
     * 
     * @param x
     * @param y
     */
    public Entity(int x, int y, boolean collectable) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.showOnMap = new SimpleBooleanProperty(true);
        this.collectable = collectable;
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }

    public BooleanProperty isShown() {
        return this.showOnMap;
    }

    public boolean isCollectable() {
        return collectable;
    }

    public void updateCoordinates(int newX, int newY) {
        this.x.set(newX);
        this.y.set(newY);
    }

    public void hideFromMap() {
        this.showOnMap.set(false);
    }

    @Override
    public void update(Dungeon dungeon, Player player) {
    }
}
