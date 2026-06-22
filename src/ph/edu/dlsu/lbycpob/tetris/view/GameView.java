package ph.edu.dlsu.lbycpob.tetris.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameView {
    private final BorderPane root;
    private final GameBoardView gameBoardView;
    private final NextPieceView nextPieceView;
    private final Label scoreLabel;
    private final Label levelLabel;
    private final Label linesLabel;
    private final Label gameStatusLabel;
    private final Button pauseButton;
    private final Button restartButton;

    public GameView() {
        root = new BorderPane();
        root.getStyleClass().add("game-root");

        // Create game board view
        gameBoardView = new GameBoardView();

        // Create next piece view
        nextPieceView = new NextPieceView();

        // Create UI components
        scoreLabel = createInfoLabel("Score: 0");
        levelLabel = createInfoLabel("Level: 1");
        linesLabel = createInfoLabel("Lines: 0");
        gameStatusLabel = createStatusLabel("");
        pauseButton = createControlButton("Pause");
        restartButton = createControlButton("Restart");

        setupLayout();
    }

    private Label createInfoLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("info-label");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        return label;
    }

    private Label createStatusLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("status-label");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        return label;
    }

    private Button createControlButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("control-button");
        button.setPrefWidth(100);
        return button;
    }

    private void setupLayout() {
        // Center - Game board
        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(gameBoardView.getRoot(), gameStatusLabel);
        centerBox.setPadding(new Insets(20));
        root.setCenter(centerBox);

        // Right side panel
        VBox rightPanel = createRightPanel();
        root.setRight(rightPanel);

        // Bottom controls
        HBox bottomControls = createBottomControls();
        root.setBottom(bottomControls);
    }

    private VBox createRightPanel() {
        VBox panel = new VBox(15);
        panel.getStyleClass().add("side-panel");
        panel.setPadding(new Insets(20));
        panel.setPrefWidth(180);
        panel.setAlignment(Pos.TOP_CENTER);

        // Next piece section
        Label nextLabel = new Label("Next Piece");
        nextLabel.getStyleClass().add("section-title");
        nextLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        VBox nextPieceBox = new VBox(10);
        nextPieceBox.setAlignment(Pos.CENTER);
        nextPieceBox.getChildren().addAll(nextLabel, nextPieceView.getRoot());

        // Game info section
        VBox infoBox = new VBox(10);
        infoBox.getStyleClass().add("info-box");
        infoBox.setAlignment(Pos.CENTER);
        infoBox.getChildren().addAll(scoreLabel, levelLabel, linesLabel);

        // Instructions
        Label instructionsTitle = new Label("Controls");
        instructionsTitle.getStyleClass().add("section-title");
        instructionsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        VBox instructionsBox = new VBox(5);
        instructionsBox.getStyleClass().add("instructions-box");
        instructionsBox.getChildren().addAll(
                instructionsTitle,
                new Label("← → Move"),
                new Label("↓ Soft Drop"),
                new Label("↑ Rotate"),
                new Label("Space Hard Drop"),
                new Label("P Pause")
        );

        panel.getChildren().addAll(nextPieceBox, infoBox, instructionsBox);
        return panel;
    }

    private HBox createBottomControls() {
        HBox controls = new HBox(20);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(15));
        controls.getStyleClass().add("bottom-controls");
        controls.getChildren().addAll(pauseButton, restartButton);
        return controls;
    }

    // Getters for components
    public BorderPane getRoot() { return root; }
    public GameBoardView getGameBoardView() { return gameBoardView; }
    public NextPieceView getNextPieceView() { return nextPieceView; }
    public Label getScoreLabel() { return scoreLabel; }
    public Label getLevelLabel() { return levelLabel; }
    public Label getLinesLabel() { return linesLabel; }
    public Label getGameStatusLabel() { return gameStatusLabel; }
    public Button getPauseButton() { return pauseButton; }
    public Button getRestartButton() { return restartButton; }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateLevel(int level) {
        levelLabel.setText("Level: " + level);
    }

    public void updateLines(int lines) {
        linesLabel.setText("Lines: " + lines);
    }

    public void updateGameStatus(String status) {
        gameStatusLabel.setText(status);
    }

    public void updatePauseButton(boolean isPaused) {
        pauseButton.setText(isPaused ? "Resume" : "Pause");
    }
}