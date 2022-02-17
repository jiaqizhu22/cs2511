package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class PotionTest {
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
    void pickUpPotionTest() {
        InvincibilityPotion potion = new InvincibilityPotion(1,1);
        dungeon.addEntity(potion);

        player.moveDown();
        player.moveRight();
        player.pickUp();
        assertTrue(player.isInvincible().get());
    }
    
}