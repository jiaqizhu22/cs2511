package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;
import org.junit.jupiter.api.BeforeEach;

class ExitAndTreasureTest {

    Dungeon dungeon;
    Player player;
    Goal goal;
    Exit exit;
    Treasure treasure;
    Portal p1;
    Portal p2;

    @BeforeEach
    public void initTest() {

        dungeon = new Dungeon(5, 6);
        Goal goal1 = new ExitGoal();
        Goal goal2 = new TreasureGoal();
        goal = new AndGoal(goal1, goal2);
        dungeon.setGoal(goal);
        player = new Player(dungeon, 0, 0);
        dungeon.setPlayer(player);

        Wall wall = new Wall(0, 1);
        dungeon.addEntity(wall);
        wall = new Wall(0, 2);
        dungeon.addEntity(wall);
        wall = new Wall(0, 3);
        dungeon.addEntity(wall);
        wall = new Wall(0, 4);
        dungeon.addEntity(wall);
        wall = new Wall(0, 5);
        dungeon.addEntity(wall);
        wall = new Wall(1, 1);
        dungeon.addEntity(wall);
        wall = new Wall(2, 1);
        dungeon.addEntity(wall);
        wall = new Wall(3, 1);
        dungeon.addEntity(wall);
        wall = new Wall(4, 1);
        dungeon.addEntity(wall);
        wall = new Wall(3, 2);
        dungeon.addEntity(wall);
        wall = new Wall(3, 3);
        dungeon.addEntity(wall);
        wall = new Wall(3, 4);
        dungeon.addEntity(wall);
        wall = new Wall(2, 4);
        dungeon.addEntity(wall);

        exit = new Exit(2, 3);
        dungeon.addEntity(exit);

        treasure = new Treasure(4, 5);
        dungeon.addEntity(treasure);

        p1 = new Portal(3, 0, 0);
        dungeon.addEntity(p1);
        p2 = new Portal(2, 2, 0);
        dungeon.addEntity(p2);
    }

    @Test
    void andTest() {
        player.moveRight();
        player.moveRight();
        player.moveRight();
        // being teleported to the p2
        assertTrue(player.getX() == 2 && player.getY() == 2);
        assertTrue(dungeon.getEntityFromCoordinates(player.getX(), player.getY()).size() == 1);
        
        // move around the exit and try to exit before completing treasureGoal
        player.moveLeft();
        player.moveDown();
        assertFalse(dungeon.checkGoalCompletion());
        player.moveRight();
        assertFalse(dungeon.checkGoalCompletion());
        assertTrue(player.getX() == 1 && player.getY() == 3);

        player.moveDown();
        player.moveDown();
        player.moveRight();
        player.moveRight();
        player.moveRight();
        // pick up treasure
        player.pickUp();
        assertTrue(dungeon.getEntityFromCoordinates(player.getX(), player.getY()).size() == 0);
        assertTrue(player.getInventoryItems().get(0) instanceof Treasure);

        // move back to exit
        player.moveLeft();
        player.moveLeft();
        player.moveLeft();
        player.moveUp();
        player.moveUp();
        player.moveRight();
        assertTrue(dungeon.checkGoalCompletion());
    }

}
