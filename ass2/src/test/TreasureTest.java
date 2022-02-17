package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;
import org.junit.jupiter.api.BeforeEach;

class TreasureTest {

    Dungeon dungeon;
    Player player;
    Goal goal;
    Treasure t1;
    Treasure t2;
    Treasure t3;
    Treasure t4;

    @BeforeEach
    public void initTest() {

        dungeon = new Dungeon(5, 5);
        goal = new TreasureGoal();
        dungeon.setGoal(goal);
        player = new Player(dungeon, 0, 0);
        dungeon.setPlayer(player);

        Wall wall = new Wall(4, 1);
        dungeon.addEntity(wall);
        wall = new Wall(4, 2);
        dungeon.addEntity(wall);
        wall = new Wall(4, 3);
        dungeon.addEntity(wall);

        wall = new Wall(1, 2);
        dungeon.addEntity(wall);
        wall = new Wall(2, 2);
        dungeon.addEntity(wall);

        wall = new Wall(0, 3);
        dungeon.addEntity(wall);
        wall = new Wall(0, 4);
        dungeon.addEntity(wall);
        wall = new Wall(1, 4);
        dungeon.addEntity(wall);

        t1 = new Treasure(0, 2);
        dungeon.addEntity(t1);
        t2 = new Treasure(1, 1);
        dungeon.addEntity(t2);
        t3 = new Treasure(4, 0);
        dungeon.addEntity(t3);
        t4 = new Treasure(3, 3);
        dungeon.addEntity(t4);

    }

    @Test
    void collectTreasureTest() {
        player.moveDown();
        assertTrue(dungeon.getEntityFromCoordinates(player.getX(), player.getY()).size() == 0);
        player.moveDown();
        assertTrue(dungeon.getEntityFromCoordinates(player.getX(), player.getY()).size() == 1);

        // player picks up the treasure on (0,2)
        player.pickUp();
        assertTrue(dungeon.getEntityFromCoordinates(player.getX(), player.getY()).size() == 0);
        assertTrue(player.getInventoryItems().get(0) instanceof Treasure);
        assertFalse(dungeon.checkGoalCompletion());

        // player tries to move towards wall and doesn't move at all
        player.moveRight();
        assertTrue(player.getX() == 0 && player.getY() == 2);

        player.moveUp();
        player.moveRight();
        // player picks up the treasure on (1,1)
        player.pickUp();
        assertTrue(player.getInventoryItems().size() == 2);
        assertFalse(dungeon.checkGoalCompletion());

        player.moveRight();
        player.moveRight();
        player.moveUp();
        player.moveRight();
        assertTrue(dungeon.getEntityFromCoordinates(player.getX(), player.getY()).size() == 1);

        // player picks up the treasure on (4,0)
        player.pickUp();
        assertTrue(player.getInventoryItems().size() == 3);
        assertFalse(dungeon.checkGoalCompletion());

        player.moveLeft();
        player.moveDown();
        player.moveDown();
        player.pickUp();
        assertFalse(player.getInventoryItems().size() == 4);
        player.moveDown();
        // player picks up the treasure on (3,3)
        player.pickUp();
        assertTrue(player.getInventoryItems().size() == 4);
        assertTrue(dungeon.checkGoalCompletion());
    }

}
