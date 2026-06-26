package ph.edu.dlsu.lbycpob.tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ph.edu.dlsu.lbycpob.tetris.controller.GameController;
import ph.edu.dlsu.lbycpob.tetris.model.GameModel;
import ph.edu.dlsu.lbycpob.tetris.view.GameView;

// NOTE: USE THE TetrisLauncher to avoid the following error:
// "Error: JavaFX runtime components are missing, and are required to run this application"
public class TetrisApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create MVC components
        GameModel model = new GameModel();
        GameView view = new GameView();
        GameController controller = new GameController(model, view);

        // Setup the scene
        Scene scene = new Scene(view.getRoot(), 550, 700);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setTitle("LBYCPEI Tetris Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Start the game
        controller.startGame();

        // Handle window closing
        primaryStage.setOnCloseRequest(e -> controller.stopGame());
    }

    public static void main() {
        launch();
    }
}
