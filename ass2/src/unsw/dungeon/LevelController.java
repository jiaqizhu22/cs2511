package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LevelController {

	@FXML
	private Pane levelPane;
	@FXML
	private Pane levelComplete;
	@FXML
	private Pane levelFail;
	@FXML
	private Button replayBtn;
	@FXML
	private Button nextLevelBtn;
	@FXML
	private Button backBtn;

	private static String level;

	@FXML
	public void initialize() {
	} 

	private void setLevel(String level) {
		LevelController.level = level;
	}

	// Back button onAction method
	@FXML
	public void backToMain(ActionEvent event) throws IOException {
		Stage curr = (Stage) ((Node) event.getSource()).getScene().getWindow();
		curr.close();
		backToMenu();
	}

	private void backToMenu() throws IOException {
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Dungeon Menu");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Next level button onAction method
	@FXML
	public void nextLevel(ActionEvent event) throws IOException {
		Stage levelStatus = (Stage) ((Node) event.getSource()).getScene().getWindow();
		levelStatus.close();
		setUpNextLevel();
	}

	private void setUpNextLevel() throws IOException {
		if (level.equals("smallMaze.json")) {
			startLevel2(null);
		} else if (level.equals("advanced.json")) {
			startLevel3(null);
		} else if (level.equals("boulders.json")) {
			startLevel4(null);
		} else if (level.equals("maze.json")) {
			startLevel5(null);
		} else if (level.equals("freezingDungeon.json")) {
			startLevel6(null);
		} else if (level.equals("megaMaze.json")) {
			startLevel6(null);
		}
	}

	@FXML
	public void startLevel1(ActionEvent event) throws IOException {
		if (event != null) {
			Stage level = (Stage) levelPane.getScene().getWindow();
			level.close();
		}
		setLevel("smallMaze.json");
		setUpLevel();
	}

	@FXML
	public void startLevel2(ActionEvent event) throws IOException {
		if (event != null) {
			Stage level = (Stage) levelPane.getScene().getWindow();
			level.close();
		}
		setLevel("advanced.json");
		setUpLevel();
	}

	@FXML
	public void startLevel3(ActionEvent event) throws IOException {
		if (event != null) {
			Stage level = (Stage) levelPane.getScene().getWindow();
			level.close();
		}
		setLevel("boulders.json");
		setUpLevel();
	}

	@FXML
	public void startLevel4(ActionEvent event) throws IOException {
		if (event != null) {
			Stage level = (Stage) levelPane.getScene().getWindow();
			level.close();
		}
		setLevel("maze.json");
		setUpLevel();
	}

	@FXML
	public void startLevel5(ActionEvent event) throws IOException {
		if (event != null) {
			Stage level = (Stage) levelPane.getScene().getWindow();
			level.close();
		}
		setLevel("freezingDungeon.json");
		setUpLevel();
	}

	@FXML
	public void startLevel6(ActionEvent event) throws IOException {
		if (event != null) {
			Stage level = (Stage) levelPane.getScene().getWindow();
			level.close();
		}
		setLevel("megaMaze.json");
		setUpLevel();
	}

	// Replay button onAction method
	@FXML
	public void replayLevel(ActionEvent event) throws IOException {
		Stage levelStatus = (Stage) ((Node) event.getSource()).getScene().getWindow();
		levelStatus.close();
		setUpLevel();
	}

	public void setUpLevel() throws IOException {
		int end = level.indexOf(".");
		Stage primaryStage = new Stage();
		primaryStage.setTitle(level.substring(0, end));

		DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(level);
		DungeonController controller = dungeonLoader.loadController(this);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
		loader.setController(controller);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		root.requestFocus();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}