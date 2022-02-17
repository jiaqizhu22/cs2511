package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;


public class InstructionsController {
    @FXML
	private Pane instructions;
	
	@FXML 
	private Button backBtn;
	
	private void backtoMenu() throws IOException {
    	Stage curr = (Stage) instructions.getScene().getWindow();
		curr.close();
		Stage primaryStage = new Stage();
    	primaryStage.setTitle("Dungeon Menu");        
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
    	Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	@FXML
	public void backtoMain(MouseEvent event) throws IOException {
		backtoMenu();
	}
}
	