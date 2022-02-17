package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    // Images
    private Image playerImage;
    private Image wallImage;
    private Image keyImage;
    private Image doorImage;
    private Image exitImage;
    private Image swordImage;
    private Image enemyImage;
    private Image boulderImage;
    private Image switchImage;
    private Image ipotionImage;
    private Image fpotionImage;
    private Image treasureImage;
    private Image portalImage;
    private Image gnomeImage;

    public DungeonControllerLoader(String filename) throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image((new File("src/images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("src/images/brick_brown_0.png")).toURI().toString());
        keyImage = new Image((new File("src/images/key.png")).toURI().toString());
        doorImage = new Image((new File("src/images/closed_door.png")).toURI().toString());
        exitImage = new Image((new File("src/images/exit.png")).toURI().toString());
        swordImage = new Image((new File("src/images/greatsword_1_new.png")).toURI().toString());
        enemyImage = new Image((new File("src/images/deep_elf_master_archer.png")).toURI().toString());
        boulderImage = new Image((new File("src/images/boulder.png")).toURI().toString());
        switchImage = new Image((new File("src/images/pressure_plate.png")).toURI().toString());
        ipotionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
        fpotionImage = new Image((new File("src/images/bubbly.png")).toURI().toString());
        treasureImage = new Image((new File("src/images/gold_pile.png")).toURI().toString());
        portalImage = new Image((new File("src/images/portal.png")).toURI().toString());
        gnomeImage = new Image((new File("src/images/gnome.png")).toURI().toString());
    }

    @Override
    public void onLoad(Player player) {
        ImageView view = new ImageView(playerImage);
        view.setViewOrder(0);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        view.setViewOrder(1);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        view.setViewOrder(1);
        addEntity(key, view);
    }

    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorImage);
        view.setViewOrder(1);
        addEntity(door, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        view.setViewOrder(1);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        view.setViewOrder(1);
        addEntity(sword, view);
    }

    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        view.setViewOrder(0);
        addEntity(enemy, view);
    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        view.setViewOrder(1);
        addEntity(boulder, view);
    }

    @Override
    public void onLoad(Switch fswitch) {
        ImageView view = new ImageView(switchImage);
        view.setViewOrder(2);
        addEntity(fswitch, view);
    }

    @Override
    public void onLoad(InvincibilityPotion potion) {
        ImageView view = new ImageView(ipotionImage);
        view.setViewOrder(1);
        addEntity(potion, view);
    }

    @Override
    public void onLoad(FreezingPotion potion) {
        ImageView view = new ImageView(fpotionImage);
        view.setViewOrder(1);
        addEntity(potion, view);
    }

    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        view.setViewOrder(1);
        addEntity(treasure, view);
    }

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        view.setViewOrder(1);
        addEntity(portal, view);
    }

    @Override
    public void onLoad(Gnome gnome) {
        ImageView view = new ImageView(gnomeImage);
        view.setViewOrder(0);
        addEntity(gnome, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an entity
     * in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the model
     * will automatically be reflected in the view.
     * 
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });

        // listen to the existence of an entity
        entity.isShown().addListener(e -> {
            ImageView i = (ImageView) node;
            if (!entity.isShown().get()) {
                i.setImage(null);
            }
        });

        // listen to player invinvibility
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.isInvincible().addListener(e -> {
                ImageView i = (ImageView) node;
                if (player.isInvincible().get()) {
                    i.setImage(new Image((new File("src/images/human_invincible.png")).toURI().toString()));
                } else {
                    i.setImage(new Image((new File("src/images/human_new.png")).toURI().toString()));
                }
            });
        }

        // listen to status of an enemy
        if (entity instanceof Enemy) {
            Enemy enemy = (Enemy) entity;
            enemy.isFrozen().addListener(e -> {
                ImageView i = (ImageView) node;
                if (enemy.isFrozen().get()) {
                    i.setImage(new Image((new File("src/images/enemy_frozen.png")).toURI().toString()));
                } else {
                    i.setImage(new Image((new File("src/images/deep_elf_master_archer.png")).toURI().toString()));
                }
            });
        }

        // listen to the status of a door
        if (entity instanceof Door) {
            Door door = (Door) entity;
            door.isOpened().addListener(e -> {
                ImageView i = (ImageView) node;
                i.setImage(new Image((new File("src/images/open_door.png")).toURI().toString()));
            });
        }

    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * 
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController(LevelController level) throws FileNotFoundException {
        return new DungeonController(load(), entities, level);
    }

}
