package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dungeon");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void complete(Stage primaryStage) throws IOException {
        primaryStage.setTitle("You nailed it!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LevelComplete.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void fail(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Game Over!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LevelFail.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
