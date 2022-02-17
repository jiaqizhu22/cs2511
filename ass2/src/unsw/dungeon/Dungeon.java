package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements Subject {

    private int width, height;
    private ArrayList<Entity> entities;
    private Player player;
    private ArrayList<EntityObserver> observers;
    private Goal goal;
    private BooleanProperty isComplete;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.observers = new ArrayList<EntityObserver>();
        this.goal = null;
        this.isComplete = new SimpleBooleanProperty(false);
    }

    /**
     * check if a player can move onto the square with coordinates x and y
     * 
     * @param direction in which the player intends to move
     * @param x
     * @param y
     * @return boolean
     */
    public boolean checkMovability(int direction, int x, int y) {
        if (!validCoordinates(x, y))
            return false;

        // Get the entitys on the square [x,y]
        ArrayList<Entity> square = getEntityFromCoordinates(x, y);
        if (square.size() == 0) {
            return true;
        }
        if (square.get(0) instanceof Wall) {
            return false;
        } else if (square.get(0) instanceof Gnome) {
            return false;
        } else if (square.get(0) instanceof Door) {
            Door d = (Door) square.get(0);
            if (!d.isOpened().get() && !player.hasKey(d)) {
                return false;
            }
        } else if (square.get(0) instanceof Boulder) {
            Boulder b = (Boulder) square.get(0);
            switch (direction) {
                case 0:
                    y--;
                    break;
                case 1:
                    y++;
                    break;
                case 2:
                    x--;
                    break;
                case 3:
                    x++;
                    break;
            }
            return checkBoulderMovability(b, x, y);
        } else if (square.size() == 2 && square.get(1) instanceof Boulder) {
            Boulder b = (Boulder) square.get(1);
            switch (direction) {
                case 0:
                    y--;
                    break;
                case 1:
                    y++;
                    break;
                case 2:
                    x--;
                    break;
                case 3:
                    x++;
                    break;
            }
            return checkBoulderMovability(b, x, y);
        }
        return true;
    }

    /**
     * get a list of entities on the given coordinate(x,y)
     * 
     * @param x
     * @param y
     * @return ArrayList<Entity>
     */
    public ArrayList<Entity> getEntityFromCoordinates(int x, int y) {
        ArrayList<Entity> found = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y) {
                found.add(e);
            }
        }
        return found;
    }

    public Entity findGnome() {
        for (Entity e : entities) {
            if (e instanceof Gnome) {
                return e;
            }
        }
        return null;
    }

    public ArrayList<Entity> getAllCollectable() {
        ArrayList<Entity> found = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e.isCollectable()) {
                found.add(e);
            }
        }
        return found;
    }

    public boolean hasSwordAtCoordinates(int x, int y) {
        ArrayList<Entity> found = getEntityFromCoordinates(x, y);
        if (found.size() == 0)
            return false;
        for (Entity e : found) {
            if (e instanceof Sword) {
                return true;
            }
        }
        return false;
    }

    public Enemy getEnemyFromCoordinates(int x, int y) {
        ArrayList<Entity> found = getEntityFromCoordinates(x, y);
        for (Entity e : found) {
            if (e instanceof Enemy) {
                return (Enemy) e;
            }
        }
        return null;
    }

    public void setEnemyMoveStrategy(MoveStrategy s) {
        for (Entity e : entities) {
            if (e instanceof Enemy) {
                Enemy enemy = (Enemy) e;
                enemy.setStrategy(s);
            }
        }
    }

    /**
     * check if a given indexed coordinate is on the map
     * 
     * @param x
     * @param y
     * @return
     */
    public boolean validCoordinates(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Check if the given boulder can be moved onto the given square with coordinats
     * x and y. If yes, move the boulder onto that square.
     * 
     * @param b boulder to be pushed
     * @param x
     * @param y
     * @return True if the square is clear or is a switch, otherwise false.
     */
    public boolean checkBoulderMovability(Boulder b, int x, int y) {
        if (!validCoordinates(x, y))
            return false;
        ArrayList<Entity> square = getEntityFromCoordinates(x, y);
        if (square.size() == 0) {
            return true;
        } else if (square.size() == 1 && square.get(0) instanceof Switch) {
            return true;
        }
        return false;
    }

    /**
     * check if an enemy can move onto the square with coordinates x and y
     * 
     * @param direction in which the enemy intends to move
     * @param x
     * @param y
     * @return boolean
     */
    public boolean checkEnemyMovability(int x, int y) {
        if (!validCoordinates(x, y))
            return false;

        // Get the entities on the square [x,y]
        ArrayList<Entity> square = getEntityFromCoordinates(x, y);
        if (square.size() == 0) {
            return true;
        } else if (square.size() == 1 && square.get(0) instanceof Door) {
            Door d = (Door) square.get(0);
            if (d.isOpened().get()) {
                return true;
            }
        }
        return false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setPlayer(Player player) {
        this.player = player;
        attachObserver((EntityObserver) player);
    }

    /**
     * To remove the player of the dungeon
     */
    public void removePlayer() {
        this.player = null;
        detachObserver(player);
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public ArrayList<Integer> getExitCoordinates() {
        ArrayList<Integer> coord = new ArrayList<Integer>();
        for (Entity e : entities) {
            if (e instanceof Exit) {
                coord.add(e.getX());
                coord.add(e.getY());
                break;
            }
        }
        return coord;
    }

    /**
     * add an entity to the dungeon
     * 
     * @param e the entity to be added
     */
    public <E extends Entity> void addEntity(E e) {
        entities.add(e);
        // add the observer for the entity
        attachObserver((EntityObserver) e);
    }

    /**
     * remove an entity from the dungeon
     * 
     * @param entity the entity to be removed
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
        entity.hideFromMap();
        // remove the observer for the entity
        detachObserver(entity);
    }

    /**
     * check if there's any treasure on the map
     * 
     * @return true if there are some, false if no treasure left
     */
    public boolean hasTreasureLeft() {
        if (entities.size() == 0)
            return false;
        for (Entity e : entities) {
            if (e instanceof Treasure) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if there's any enemy on the map
     * 
     * @return true if there exists an enemy, false if no enemy has been found
     */
    public boolean hasEnemy() {
        if (entities.size() == 0)
            return false;
        for (Entity e : entities) {
            if (e instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void attachObserver(EntityObserver obs) {
        if (!observers.contains(obs)) {
            observers.add(obs);
        }
    }

    @Override
    public void detachObserver(EntityObserver obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObserver() {
        for (EntityObserver obs : observers) {
            obs.update(this, player);
        }
        entityCollide();
        this.isComplete.set(checkGoalCompletion());
    }

    public void entityCollide() {
        Enemy enemyDelete = null;
        Portal p = null;
        for (Entity e : entities) {
            if (e instanceof Enemy) {
                if (e.getX() == player.getX() && e.getY() == player.getY()) {
                    enemyDelete = (Enemy) e;
                    break;
                }
            } 
            if (e instanceof Portal) {
                if (e.getX() == player.getX() && e.getY() == player.getY()) {
                    p = (Portal) e;
                    break;
                }
            }
        }
        if (enemyDelete != null)
            player.collideWithEnemy(enemyDelete);
        if (p != null) {
            p.teleport(this, player);
        }
    }

    public String getGoals() {
        return goal.toString();
    }

    /**
     * Check if the goal(s) is/are met
     */
    public boolean checkGoalCompletion() {
        if (this.goal == null)
            return true;
        return this.goal.hasMetGoal(this, player);
    }

    public BooleanProperty getIsComplete() {
        return isComplete;
    }

}
