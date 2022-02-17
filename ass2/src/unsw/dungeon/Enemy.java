package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Enemy extends Entity {
    private MoveStrategy moveStrategy;
    private boolean alive;
    private BooleanProperty frozen;

    public Enemy(int x, int y) {
        super(x, y, false);
        this.moveStrategy = new MoveTowards();
        this.alive = true;
        this.frozen = new SimpleBooleanProperty(false);
    }

    public <Strategy extends MoveStrategy> void setStrategy(Strategy strategy) {
        this.moveStrategy = strategy;
        if (strategy instanceof MoveFrozen) {
            this.frozen.set(true);
        }
    }

    public boolean isMovingTowardsPlayer() {
        return (moveStrategy instanceof MoveTowards);
    }

    public void move(Dungeon dungeon, Player player) {
        moveStrategy.move(dungeon, player, this);
    }

    public boolean checkMove(Dungeon dungeon, int direction) {
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
        return dungeon.checkEnemyMovability(x, y);
    }

    public boolean isAlive() {
        return this.alive;
    }

    public BooleanProperty isFrozen() {
        return frozen;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void defreeze() {
        this.frozen.set(false);
    }

    public void killEnemy(Player p) {
        p.removeFromDungeon(this);
        setAlive(false);
    }

    @Override
    public void update(Dungeon dungeon, Player p) {
        if (this.isAlive()) {
            this.move(dungeon, p);
        }
    }
}