package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * A JavaFX controller for the main menu.
 *
 */
public class MainMenuController {

	@FXML
	private Pane mainMenu;
	@FXML
	private Button playBtn;
	@FXML
	private Button instructionBtn;
	@FXML
	private Button exitBtn;

	@FXML
	public void initialize() {

	}
	
	private void selectLevel() throws IOException {
		Stage main = (Stage) mainMenu.getScene().getWindow();
		main.close();
		DropShadow shadow = new DropShadow();

		Stage primaryStage = new Stage();
		primaryStage.setTitle("Please select the level you want to play");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Level.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

		playBtn.setOnMouseEntered(e -> playBtn.setEffect(shadow));
		playBtn.setOnMouseExited(e -> playBtn.setEffect(null));
	}

	@FXML
	public void startGame(MouseEvent event) throws IOException {
		selectLevel();
	}

	private void showInstructions() throws IOException {
		Stage main = (Stage) mainMenu.getScene().getWindow();
		main.close();
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Instructions");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Instructions.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	public void showGameInstructions(MouseEvent event) throws IOException {
		showInstructions();
	}

	private void closeMainMenu() throws IOException {
		Stage main = (Stage) mainMenu.getScene().getWindow();
		main.close();
	}

	@FXML
	public void exitGame(MouseEvent event) throws IOException {
		closeMainMenu();
	}
}
