package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class BoulderSwitchTest {
    Dungeon dungeon;
    Player player;
    Boulder boulder;
    Switch fswitch;
    Exit exit;
    Goal goal;

    @BeforeEach
    public void initTests() {
        Goal goal1 = new SwitchGoal();
        Goal goal2 = new ExitGoal();
        goal = new OrGoal(goal1, goal2);
        dungeon = new Dungeon(6, 6);
        dungeon.setGoal(goal);
        player = new Player(dungeon, 1, 1);
        dungeon.setPlayer(player);

        Wall wall = new Wall(2, 0);
        dungeon.addEntity(wall);
        wall = new Wall(3, 0);
        dungeon.addEntity(wall);
        wall = new Wall(4, 0);
        dungeon.addEntity(wall);
        wall = new Wall(5, 0);
        dungeon.addEntity(wall);
        wall = new Wall(5, 1);
        dungeon.addEntity(wall);
        wall = new Wall(0, 2);
        dungeon.addEntity(wall);
        wall = new Wall(0, 3);
        dungeon.addEntity(wall);
        wall = new Wall(0, 4);
        dungeon.addEntity(wall);
        wall = new Wall(0, 5);
        dungeon.addEntity(wall);
        wall = new Wall(1, 5);
        dungeon.addEntity(wall);
        wall = new Wall(3, 2);
        dungeon.addEntity(wall);
        wall = new Wall(3, 3);
        dungeon.addEntity(wall);

        boulder = new Boulder(2, 2);
        dungeon.addEntity(boulder);
        boulder = new Boulder(4, 2);
        dungeon.addEntity(boulder);

        fswitch = new Switch(2, 3);
        dungeon.addEntity(fswitch);
        fswitch = new Switch(2, 4);
        dungeon.addEntity(fswitch);

        exit = new Exit(5, 5);
        dungeon.addEntity(exit);
    }

    @Test
    void completeSwitchOrGoalTest() {
        player.moveRight();
        // move boulder on (2,2) onto the switch(2,3)
        player.moveDown();
        assertTrue(player.getX() == 2 && player.getY() == 2);

        player.moveUp();
        player.moveRight();
        player.moveRight();
        // move boulder on (4,2) onto the switch(2,4)
        System.out.println("player x: " + player.getX() + ", y: " + player.getY());
        player.moveDown();
        player.moveDown();
        player.moveRight();
        player.moveDown();
        assertTrue(player.getX() == 5 && player.getY() == 4);
        player.moveLeft();
        player.moveLeft();
        assertTrue(dungeon.checkGoalCompletion());
        //player back to (2,2)
        player.moveRight();
        player.moveUp();
        player.moveUp();
        player.moveUp();
        player.moveLeft();
        player.moveLeft();
        player.moveDown();
        // player try to move down but there are two adjacent boulders on her direction
        player.moveDown();
        player.moveDown();
        assertTrue(player.getX() == 2 && player.getY() == 2);
    }

    @Test
    void completeExitOrGoalTest() {
        player.moveRight();
        player.moveRight();
        player.moveRight();
        player.moveDown();
        player.moveRight();
        player.moveDown();
        player.moveDown();
        player.moveDown();
        assertTrue(dungeon.checkGoalCompletion());
    }

    @Test
    void stuckedTest() {
        player.moveRight();
        player.moveDown();
        player.moveDown();
        player.moveDown();
        // should not move, the boulder permanently stopped at (2,5)
        player.moveDown();
        assertTrue(player.getX() == 2 && player.getY() == 4);
        // going to push the boulder onto the exit
        player.moveUp();
        player.moveUp();
        player.moveUp();
        player.moveRight();
        player.moveRight();
        player.moveDown();
        player.moveDown();
        player.moveDown();
        player.moveLeft();
        player.moveDown();
        // trying to push the boulder at (4,5) onto the exit, should not move
        player.moveRight();
        assertTrue(player.getX() == 3 && player.getY() == 5);
    }

    @Test
    void moveBoulderAwayFromSwitchTest() {
        // move boulder on (2,2) to switch (2,3)
        player.moveRight();
        player.moveDown();
        // 
        player.moveLeft();
        player.moveDown();
        player.moveDown();
        player.moveRight();
        assertTrue(player.getX() == 2 && player.getY() == 4);
        // going to push the boulder away from the switch
        player.moveUp();
        assertTrue(player.getX() == 2 && player.getY() == 3);
    }
}