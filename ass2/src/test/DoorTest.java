package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class DoorTest {
    Dungeon dungeon;
	Player player;
	Door door1, door2, door3;
	Key key1, key2, key3;

    @BeforeEach
    public void initTest() {
        dungeon = new Dungeon(4, 4);
        player = new Player(dungeon, 0, 0);
		dungeon.setPlayer(player);
		
		Door door1 = new Door(0,2,0);
		dungeon.addEntity(door1);
		Door door2 = new Door(1,3,1);
		dungeon.addEntity(door2);
		Door door3 = new Door(3,3,2);
		dungeon.addEntity(door3);

		Key key1 = new Key(0,1,0);
		dungeon.addEntity(key1);
		Key key2 = new Key(2,0,1);
		dungeon.addEntity(key2);
		Key key3 = new Key(1,2,2);
		dungeon.addEntity(key3);
    }

    @Test
    void noPickUpTest() {
		assertFalse(player.hasKey(door1));
		assertFalse(player.hasKey(door2));
		assertFalse(player.hasKey(door3));
		player.pickUp();
		assertFalse(player.hasKey(door1));
		assertFalse(player.hasKey(door2));
		assertFalse(player.hasKey(door3));
	}
	@Test
	void cannotOpenDoorTest() {
		player.moveDown();
		player.moveDown();
		assertEquals(1, player.getY());
	}

	@Test
	public void pickUpAllKeyTest() {
		player.moveDown();
		player.pickUp();
		assertTrue(player.getInventoryItems().size() == 1);
		assertTrue(player.getKeyById(0) != null);

		player.moveRight();
		player.moveDown();
		player.pickUp();
		assertTrue(player.getInventoryItems().size() == 2);

		player.moveRight();
		player.moveUp();
		player.moveUp();
		player.pickUp();
		assertTrue(player.getInventoryItems().size() == 3);
	}


	@Test
	void keyNotMatchDoorTest() {
		player.moveDown();
		player.pickUp();
		player.moveRight();
		player.moveDown();
		assertEquals(2, player.getY());
		player.moveDown();
		assertEquals(2, player.getY());
	}

	@Test
	void openDoorTest() {
		player.moveDown();
		player.pickUp();
		player.moveRight();
		player.moveDown();
		player.pickUp();
		assertEquals(2, player.getY());
		player.moveDown();
        assertTrue(player.getX() == 1 && player.getY() == 2);
	}

}