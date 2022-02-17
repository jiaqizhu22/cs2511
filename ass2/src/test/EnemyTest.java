package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import unsw.dungeon.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


class EnemyTest {
	
	Dungeon dungeon;
	Player player;
	Enemy enemy;
	Sword sword;
	Goal goal;

	@BeforeEach
	public void initTests() {
		
		goal = new EnemyGoal();
        
		dungeon = new Dungeon(8, 8);
		dungeon.setGoal(goal);
		player = new Player(dungeon, 0, 0);
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
		wall = new Wall(3, 4);
		dungeon.addEntity(wall);
		wall = new Wall(3, 5);
		dungeon.addEntity(wall);
		wall = new Wall(3, 6);
		dungeon.addEntity(wall);
		wall = new Wall(3, 7);
		dungeon.addEntity(wall);
		wall = new Wall(4, 7);
		dungeon.addEntity(wall);
		wall = new Wall(5, 7);
		dungeon.addEntity(wall);
		wall = new Wall(6, 4);
		dungeon.addEntity(wall);
		wall = new Wall(7, 4);
		dungeon.addEntity(wall);
		wall = new Wall(7, 5);
		dungeon.addEntity(wall);
		wall = new Wall(7, 6);
		dungeon.addEntity(wall);
		
		enemy = new Enemy(6,5);
		dungeon.addEntity(enemy);
		
		sword = new Sword(3,2);
		dungeon.addEntity(sword);

	}
	
	@Test
	void pickupSwordtest() {
		player.moveDown();
		player.moveDown();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		
		assertFalse(player.hasSword(),"Player must not find sword in inventory before pickup");
		assertTrue(dungeon.hasSwordAtCoordinates(3, 2),"Player must find sword in dungeon before pickup");
		assertFalse(player.isWithSword());
		System.out.println(player.getInventoryItems().size());
		//player picks up the sword
		player.pickUp();
		System.out.println(player.getInventoryItems().size());
		
		assertTrue(player.isWithSword());
		assertTrue(player.hasSword(),"Player must find sword in inventory after pickup");
		assertFalse(dungeon.hasSwordAtCoordinates(3, 2),"Player must not find sword in dungeon after pickup");
	}
	
	@Test
	void enemyMovementTest() {
		Enemy enemy2 = new Enemy(7,0);
		dungeon.addEntity(enemy2);
		
		player.moveDown();
		System.out.println(enemy.getX() + ", " + enemy.getY());
		assertEquals(0, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(1, player.getY(), "Player must move down on the y-axis when moving down");
		assertEquals(5, enemy.getX(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(5, enemy.getY(), "Enemy must move to the location where it gets closer to the player");	
		assertEquals(6, enemy2.getX(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(0, enemy2.getY(), "Enemy must move to the location where it gets closer to the player");
		
		player.moveDown();
		System.out.println(enemy.getX() + ", " + enemy.getY());
		assertEquals(0, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(2, player.getY(), "Player must move down on the y-axis when moving down");	
		assertEquals(4, enemy.getX(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(5, enemy.getY(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(6, enemy2.getX(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(1, enemy2.getY(), "Enemy must move to the location where it gets closer to the player");
		
		player.moveRight();
		System.out.println(enemy.getX() + ", " + enemy.getY());
		assertEquals(1, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(2, player.getY(), "Player must move down on the y-axis when moving down");
		assertEquals(4, enemy.getX(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(4, enemy.getY(), "Enemy must move to the location where it gets closer to the player");	
		assertEquals(6, enemy2.getX(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(2, enemy2.getY(), "Enemy must move to the location where it gets closer to the player");
		
		player.moveRight();
		System.out.println(enemy.getX() + ", " + enemy.getY());
		assertEquals(2, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(2, player.getY(), "Player must move down on the y-axis when moving down");
		assertEquals(4, enemy.getX(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(3, enemy.getY(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(5, enemy2.getX(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(2, enemy2.getY(), "Enemy must move to the location where it gets closer to the player");
		
		player.moveRight();
		System.out.println(enemy.getX() + ", " + enemy.getY());
		assertEquals(3, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(2, player.getY(), "Player must move down on the y-axis when moving down");
		assertEquals(3, enemy.getX(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(3, enemy.getY(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(4, enemy2.getX(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(2, enemy2.getY(), "Enemy must move to the location where it gets closer to the player");
		
		player.moveUp();
		System.out.println(enemy.getX() + ", " + enemy.getY());
		assertEquals(3, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(1, player.getY(), "Player must move down on the y-axis when moving down");
		assertEquals(3, enemy.getX(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(3, enemy.getY(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(4, enemy2.getX(), "Enemy must move to the location where it gets closer to the player");
		assertEquals(1, enemy2.getY(), "Enemy must move to the location where it gets closer to the player");
		
		dungeon.removeEntity(enemy2);
	}
	
	@Test
	void killEnemyTest() {
		//player moves to the sword location
		System.out.println(enemy.getX() + ", " + enemy.getY());
		player.moveDown();
		System.out.println(enemy.getX() + ", " + enemy.getY());
		player.moveDown();
		System.out.println(enemy.getX() + ", " + enemy.getY());
		player.moveRight();
		System.out.println(enemy.getX() + ", " + enemy.getY());
		player.moveRight();
		System.out.println(enemy.getX() + ", " + enemy.getY());
		player.moveRight();
		System.out.println(enemy.getX() + ", " + enemy.getY());
		

		assertTrue(dungeon.hasEnemy(),"Enemy must be on the board before killing");
		
		assertEquals(3, player.getX(), "Player must not move on the x-axis when moving down");
		assertEquals(2, player.getY(), "Player must move down on the y-axis when moving down");
		assertTrue(dungeon.hasSwordAtCoordinates(3, 2));
		//player picks up the sword and kills the enemy
		player.pickUp();
		System.out.println(player.isWithSword());
		System.out.println(enemy.getX() + ", " + enemy.getY());
		assertFalse(enemy.isAlive());
		assertFalse(dungeon.hasEnemy(),"Enemy must not be on the board after being killed");		
		assertTrue(player.hasSword(),"Player must find sword in inventory after pickup");
		assertEquals(4, sword.getTurns(), "The sword must lose the number of turns if enemy is killed");
		
	}
	
	@Test
	void killPlayerTest() {
		player.moveDown();
		player.moveDown();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		//when the player reaches the same location as the enemy
		player.moveDown();
		assertTrue(player.isDead().get(),"The player must be killed if they walk into the enemy");		
		
	}
	
	@AfterEach
	public void resetTest() {
		dungeon.removeEntity(enemy);
		dungeon.removeEntity(sword);
		player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		enemy = new Enemy(6, 5);
		dungeon.addEntity(enemy);
		sword = new Sword(3, 2);
		dungeon.addEntity(sword);

	}

}