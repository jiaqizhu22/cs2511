package unsw.dungeon;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Random;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * The player entity
 * 
 * @author OhNice
 *
 */
public class Player extends Entity {
    PlayerState normalState;
    PlayerState invincibleState;
    PlayerState deadState;
    PlayerState withSwordState;

    private Dungeon dungeon;
    private Inventory inventory;
    private PlayerState playerState;
    private int lastPressed;
    private BooleanProperty invincibility;
    private BooleanProperty dead;

    /**
     * Create a player positioned in square (x,y)
     * 
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y, false);
        // set up all different states for the player
        this.normalState = new NormalState(this);
        this.invincibleState = new InvincibleState(this, -1);
        this.withSwordState = new WithSwordState(this);
        this.deadState = new DeadState(this);

        this.dungeon = dungeon;
        this.inventory = new Inventory();
        this.playerState = normalState;
        // 0 - UP, 1 - DOWN, 2 - LEFT, 3 - RIGHT, 4 - SPACE, 5 - T
        this.lastPressed = -1;
        this.invincibility = new SimpleBooleanProperty(false);
        this.dead = new SimpleBooleanProperty(false);
    }

    public void setPlayerStateNormal() {
        this.invincibility.set(false);
        this.playerState = normalState;
        setEnemyMoveStrategy(new MoveTowards());
    }

    public void setPlayerStateSword() {
        this.invincibility.set(false);
        this.playerState = withSwordState;
        setEnemyMoveStrategy(new MoveTowards());
    }

    public void setPlayerStateInvincible(InvincibilityPotion potion) {
        int duration = potion.getDuration();
        InvincibleState invin = (InvincibleState) invincibleState;
        invin.setDuration(duration);
        this.playerState = (PlayerState) invin;
        this.invincibility.set(true);
        setEnemyMoveStrategy(new MoveAway());
    }

    public void setPlayerStateDead() {
        this.invincibility.set(false);
        this.playerState = deadState;
        this.dead.set(true);
    }

    public void setEnemyMoveStrategy(MoveStrategy s) {
        dungeon.setEnemyMoveStrategy(s);
    }

    public ArrayList<Entity> getInventoryItems() {
        return inventory.getItems();
    }

    public int getLastPressed() {
        return lastPressed;
    }

    public void setlastPressed(int key) {
        this.lastPressed = key;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void useSword() {
        for (Entity e : getInventoryItems()) {
            if (e instanceof Sword) {
                Sword sword = (Sword) e;
                sword.useSword();
                if (sword.getTurns() == 0) {
                    removeItemFromInventory(sword);
                }
                break;
            }
        }
    }

    public Entity getKeyById(int id) {
        return inventory.getKeyById(id);
    }

    public void addItemToInventory(Entity e) {
        this.inventory.addItem(e);
        removeFromDungeon(e);
    }

    public void removeItemFromInventory(Entity e) {
        this.inventory.removeItem(e);
    }

    public void removeFromDungeon(Entity e) {
        dungeon.removeEntity(e);
    }

    public void countDown() {
        this.playerState.countDown();
    }

    public void moveUp() {
        if (getY() > 0 && checkMove(0)) {
            y().set(getY() - 1);
            setlastPressed(0);
            dungeon.notifyObserver();
            countDown();
        }
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1 && checkMove(1)) {
            y().set(getY() + 1);
            setlastPressed(1);
            dungeon.notifyObserver();
            countDown();
        }
    }

    public void moveLeft() {
        if (getX() > 0 && checkMove(2)) {
            x().set(getX() - 1);
            setlastPressed(2);
            dungeon.notifyObserver();
            countDown();
        }
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1 && checkMove(3)) {
            x().set(getX() + 1);
            setlastPressed(3);
            dungeon.notifyObserver();
            countDown();
        }
    }

    /**
     * When the T key is pressed, The player may talk to a gnome
     */
    public void talkToGnome() {
        setlastPressed(5);
        Entity result = dungeon.findGnome();
        if (result != null) {
            if (isAdjacent(this, result)) {
                // TODO give a present
                getRandom: {
                    ArrayList<Entity> collectables = dungeon.getAllCollectable();
                    if (collectables.size() == 0) {
                        break getRandom;
                    }
                    Random rd = new Random();
                    int num = rd.nextInt(5001) % collectables.size();
                    Entity random = collectables.get(num);
                    if (random instanceof Sword) {
                        if (isInvincible().get()) {
                            addItemToInventory(random);
                        } else {
                            addItemToInventory(random);
                            setPlayerStateSword();
                        }
                    } else if (random instanceof Treasure) {
                        addItemToInventory(random);
                    } else if (random instanceof Key) {
                        addItemToInventory(random);
                    } else if (random instanceof InvincibilityPotion) {
                        setPlayerStateInvincible((InvincibilityPotion) random);
                        random.hideFromMap();
                    } else if (random instanceof FreezingPotion) {
                        FreezingPotion potion = (FreezingPotion) random;
                        int duration = potion.getDuration();
                        MoveFrozen frozen = new MoveFrozen(duration);
                        setEnemyMoveStrategy(frozen);
                        random.hideFromMap();
                    }
                }
                removeFromDungeon(result);
                dungeon.notifyObserver();
            }

        }
    }

    private boolean isAdjacent(Player player, Entity entity) {
        int pX = player.getX();
        int pY = player.getY();
        int eX = entity.getX();
        int eY = entity.getY();
        if ((abs(eX - pX) == 1 && eY == pY) || (abs(eY - pY) == 1 && eX == pX)) {
            return true;
        }
        return false;
    }

    /**
     * When the SPACE key is pressed, The player may pick up a collectable entity
     */
    public void pickUp() {
        setlastPressed(4);
        int x = getX();
        int y = getY();
        ArrayList<Entity> entities = dungeon.getEntityFromCoordinates(x, y);
        Entity e = (entities.size() > 1) ? entities.get(0) : null;
        if (e != null) {
            if (e instanceof Player) {
                e = entities.get(1);
            }
            if (e instanceof Sword) {
                if (isInvincible().get()) {
                    addItemToInventory(e);
                } else {
                    addItemToInventory(e);
                    setPlayerStateSword();
                }
            } else if (e instanceof Treasure) {
                addItemToInventory(e);
            } else if (e instanceof Key) {
                addItemToInventory(e);
            } else if (e instanceof InvincibilityPotion) {
                setPlayerStateInvincible((InvincibilityPotion) e);
                e.hideFromMap();
            } else if (e instanceof FreezingPotion) {
                FreezingPotion potion = (FreezingPotion) e;
                int duration = potion.getDuration();
                MoveFrozen frozen = new MoveFrozen(duration);
                setEnemyMoveStrategy(frozen);
                e.hideFromMap();
            }
            // TODO
            dungeon.notifyObserver();
        }
    }

    private boolean checkMove(int direction) {
        int x = getX();
        int y = getY();
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
        return dungeon.checkMovability(direction, x, y);
    }

    public boolean hasKey(Door d) {
        if (getInventoryItems().size() == 0)
            return false;
        int doorID = d.getID();
        for (Entity e : getInventoryItems()) {
            if (e instanceof Key) {
                Key entity = (Key) e;
                if (entity.matchID(doorID)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasSword() {
        for (Entity e : getInventoryItems()) {
            if (e instanceof Sword) {
                return true;
            }
        }
        return false;
    }

    public int getTurns() {
        for (Entity e : getInventoryItems()) {
            if (e instanceof Sword) {
                Sword s = (Sword) e;
                return s.getTurns();
            }
        }
        return 0;
    }

    public boolean isWithSword() {
        return (playerState instanceof WithSwordState);
    }

    public BooleanProperty isInvincible() {
        return invincibility;
    }

    public BooleanProperty isDead() {
        return dead;
    }

    public void collideWithEnemy(Enemy enemy) {
        playerState.collide(dungeon, enemy);
    }

    public int getTreasureNumbers() {
        return inventory.getTreasureNumber();
    }

    public int getSwordTurns() {
        if (hasSword() || isWithSword()) {
            return getTurns();
        }
        return 0;
    }

    public int getKeyNumber() {
        int sum = 0;
        for (Entity e : getInventoryItems()) {
            if (e instanceof Key) {
                sum++;
            }
        }
        return sum;
    }

    @Override
    public void update(Dungeon dungeon, Player player) {
    }
}
