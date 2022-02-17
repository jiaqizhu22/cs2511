package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;
import org.junit.jupiter.api.BeforeEach;

class PortalTest {

    Dungeon dungeon;
    Player player;
    Goal goal;
    Exit exit;
    Portal p1;
    Portal p2;

    @BeforeEach
    public void initTest() {

        dungeon = new Dungeon(5, 6);
        goal = new ExitGoal();
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

        p1 = new Portal(3, 0, 0);
        dungeon.addEntity(p1);
        p2 = new Portal(2, 2, 0);
        dungeon.addEntity(p2);
    }

    @Test
    void portalTest() {
        player.moveRight();
        player.moveRight();
        player.moveRight();
        // being teleported to the p2
        assertTrue(player.getX() == 2 && player.getY() == 2);
        assertTrue(dungeon.getEntityFromCoordinates(player.getX(), player.getY()).size() == 1);
        // move away from the destination portal
        player.moveLeft();
        player.moveRight();
        // being teleported to the p1
        assertTrue(player.getX() == 3 && player.getY() == 0);
        assertTrue(dungeon.getEntityFromCoordinates(player.getX(), player.getY()).size() == 1);
    }

}
