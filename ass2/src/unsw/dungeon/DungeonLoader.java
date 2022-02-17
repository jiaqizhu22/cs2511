package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * 
     * @return the Dungeon object
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");
        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        JSONObject jsonGoals = json.getJSONObject("goal-condition");
        loadGoal(dungeon, jsonGoals);
        return dungeon;
    }

    private void loadGoal(Dungeon dungeon, JSONObject json) {
        String mainGoal = json.getString("goal");
        switch (mainGoal) {
            case "exit":
                dungeon.setGoal(new ExitGoal());
                break;
            case "enemies":
                dungeon.setGoal(new EnemyGoal());
                break;
            case "treasure":
                dungeon.setGoal(new TreasureGoal());
                break;
            case "boulders":
                dungeon.setGoal(new SwitchGoal());
                break;
            case "AND":
                dungeon.setGoal(new AndGoal(json.getJSONArray("subgoals")));
                break;
            case "OR":
                dungeon.setGoal(new OrGoal(json.getJSONArray("subgoals")));
                break;
        }
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id;

        Entity entity = null;
        switch (type) {
            case "player":
                Player player = new Player(dungeon, x, y);
                dungeon.setPlayer(player);
                onLoad(player);
                entity = player;
                break;
            case "wall":
                Wall wall = new Wall(x, y);
                onLoad(wall);
                entity = wall;
                break;
            case "key":
                id = json.getInt("id");
                Key key = new Key(x, y, id);
                onLoad(key);
                entity = key;
                break;
            case "door":
                id = json.getInt("id");
                Door door = new Door(x, y, id);
                onLoad(door);
                entity = door;
                break;
            case "exit":
                Exit exit = new Exit(x, y);
                onLoad(exit);
                entity = exit;
                break;
            case "sword":
                Sword sword = new Sword(x, y);
                onLoad(sword);
                entity = sword;
                break;
            case "enemy":
                Enemy enemy = new Enemy(x, y);
                onLoad(enemy);
                entity = enemy;
                break;
            case "boulder":
                Boulder boulder = new Boulder(x, y);
                onLoad(boulder);
                entity = boulder;
                break;
            case "switch":
                Switch fswitch = new Switch(x, y);
                onLoad(fswitch);
                entity = fswitch;
                break;
            case "invincibility":
                InvincibilityPotion ipotion = new InvincibilityPotion(x, y);
                onLoad(ipotion);
                entity = ipotion;
                break;
            case "freezing":
                FreezingPotion fpotion = new FreezingPotion(x, y);
                onLoad(fpotion);
                entity = fpotion;
                break;
            case "treasure":
                Treasure treasure = new Treasure(x, y);
                onLoad(treasure);
                entity = treasure;
                break;
            case "portal":
                id = json.getInt("id");
                Portal portal = new Portal(x, y, id);
                onLoad(portal);
                entity = portal;
                break;
            case "gnome":
                Gnome gnome = new Gnome(x, y);
                onLoad(gnome);
                entity = gnome;
                break;
        }
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Player player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Door door);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Switch fswitch);

    public abstract void onLoad(InvincibilityPotion ipotion);

    public abstract void onLoad(FreezingPotion fpotion);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Gnome gnome);
}
