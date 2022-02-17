package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A JavaFX controller for the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private BorderPane border;

    @FXML
    private FlowPane inventory;

    @FXML
    private GridPane squares;

    @FXML
    private Button replayBtn;

    @FXML
    private Button menuBtn;

    @FXML
    private Text goals;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private LevelController level;

    private Text swordTurns;

    private Text treasureNumber;

    private Text keyNumber;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, LevelController level) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.level = level;
    }

    @FXML
    public void initialize() throws FileNotFoundException {
        // Add the ground first so it is below all other entities
        Image ground = new Image((new File("src/images/dirt_0_new.png")).toURI().toString());
        squares.setMinHeight(Region.USE_COMPUTED_SIZE);
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                ImageView imgView = new ImageView(ground);
                imgView.setViewOrder(3);
                squares.add(imgView, x, y);
            }
        }
        for (ImageView entity : initialEntities)
        squares.getChildren().add(entity);
        BorderPane.setAlignment(squares, Pos.CENTER);
        // set prefered height for border pane
        border.prefHeightProperty().bind(squares.heightProperty());
        
        Text goals = new Text();
        String text = "Your goal is:\n" + dungeon.getGoals();
        goals.setText(text);
        border.setBottom(goals);
        BorderPane.setMargin(goals, new Insets(10, 0, 10, 0));
        BorderPane.setAlignment(goals, Pos.CENTER);
        listenerReplayButton();
        listenerMenuButton();

        FlowPane inventory = new FlowPane();
        inventory.setMinHeight(Region.USE_COMPUTED_SIZE);
        inventory.setPadding(new Insets(0, 30, 0, 30));
        inventory.setAlignment(Pos.CENTER);
        FileInputStream input = new FileInputStream("src/images/greatsword_1_new.png");
        Image image = new Image(input);
        ImageView sword = new ImageView(image);
        FileInputStream input2 = new FileInputStream("src/images/gold_pile.png");
        Image image2 = new Image(input2);
        ImageView treasures = new ImageView(image2);
        FileInputStream input3 = new FileInputStream("src/images/key.png");
        Image image3 = new Image(input3);
        ImageView keys = new ImageView(image3);
        this.swordTurns = new Text();
        this.swordTurns.setText("0" + " hits");
        this.treasureNumber = new Text();
        this.treasureNumber.setText("0");
        this.keyNumber = new Text();
        this.keyNumber.setText("0");
        inventory.getChildren().add(sword);
        inventory.getChildren().add(swordTurns);
        inventory.getChildren().add(treasures);
        inventory.getChildren().add(treasureNumber);
        inventory.getChildren().add(keys);
        inventory.getChildren().add(keyNumber);
        inventory.setOrientation(Orientation.VERTICAL);
        border.setRight(inventory);

        listenerGoalCompletion();
        listenerPlayerDead();
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                player.moveUp();
                break;
            case DOWN:
                player.moveDown();
                break;
            case LEFT:
                player.moveLeft();
                break;
            case RIGHT:
                player.moveRight();
                break;
            case SPACE:
                player.pickUp();
                break;
            case T:
                player.talkToGnome();
                break;
            default:
                break;
        }
        int n = player.getTreasureNumbers();
        treasureNumber.setText(String.valueOf(n));
        int t = player.getSwordTurns();
        swordTurns.setText(String.valueOf(t) + " hits");
        int k = player.getKeyNumber();
        keyNumber.setText(String.valueOf(k));
    }

    private void listenerReplayButton() {
        replayBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    Stage dungeon = (Stage) squares.getScene().getWindow();
                    dungeon.close();
                    level.setUpLevel();
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        });
    }

    private void listenerMenuButton() {
        menuBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    Stage dungeon = (Stage) squares.getScene().getWindow();
                    dungeon.close();

                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Dungeon Menu");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        });
    }

    private void closeDungeon() {
        Stage dungeon = (Stage) squares.getScene().getWindow();
        dungeon.close();
    }

    private void listenerGoalCompletion() {
        dungeon.getIsComplete().addListener(e -> {
            if (dungeon.getIsComplete().get()) {
                DungeonApplication app = new DungeonApplication();
                try {
                    closeDungeon();
                    app.complete(new Stage());

                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        });
    }

    private void listenerPlayerDead() {
        player.isDead().addListener(e -> {
            if (player.isDead().get()) {
                DungeonApplication app = new DungeonApplication();
                try {
                    closeDungeon();
                    app.fail(new Stage());

                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        });
    }
}
