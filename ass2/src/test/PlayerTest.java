/**
 * Tests for player movement, inventory and NormalState
 */
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class PlayerTest {
    Dungeon dungeon;
    Player player;
    Goal goal;

    @BeforeEach
    public void initTest() {
        goal = new EnemyGoal();
        dungeon = new Dungeon(4, 4);
        dungeon.setGoal(goal);
        player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		Wall wall = new Wall(2, 0);
		dungeon.addEntity(wall);
		wall = new Wall(3, 0);
		dungeon.addEntity(wall);
		wall = new Wall(3, 1);
		dungeon.addEntity(wall);
		wall = new Wall(3, 2);
		dungeon.addEntity(wall);
		
	}

	@AfterEach
	public void resetTest() {
		dungeon.removePlayer();
		Player player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
	}

    @Test
    void playerMovementTest() {
		player.moveDown();
		assertEquals(0, player.getX());
		assertEquals(1, player.getY());

		//Out of boundary
		player.moveLeft();
		assertEquals(0, player.getX());
		assertEquals(1, player.getY());

		player.moveRight();
		player.moveRight();
		assertEquals(2, player.getX());
		assertEquals(1, player.getY());

		//Blocked by wall
		player.moveUp();
		assertEquals(2, player.getX());
		assertEquals(1, player.getY());
	}
	
}