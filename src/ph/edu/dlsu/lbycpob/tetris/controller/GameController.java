package ph.edu.dlsu.lbycpob.tetris.controller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ph.edu.dlsu.lbycpob.tetris.model.GameModel;
import ph.edu.dlsu.lbycpob.tetris.model.GameModelListener;
import ph.edu.dlsu.lbycpob.tetris.view.GameView;


public class GameController implements GameModelListener {
    private final GameModel model;
    private final GameView view;
    private AnimationTimer gameTimer;
    private long lastDropTime = 0;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;

        initializeController();
    }

    private void initializeController() {
        // Add model listener
        model.addListener(this);

        // Setup event handlers
        setupEventHandlers();

        // Initialize view with model data
        updateView();

        // Setup key handling
        setupKeyHandling();
    }

    private void setupEventHandlers() {
        view.getPauseButton().setOnAction(e -> togglePause());
        view.getRestartButton().setOnAction(e -> restartGame());
    }

//    private void setupKeyHandling() {
//        view.getRoot().setOnKeyPressed(this::handleKeyPressed);
//        view.getRoot().setFocusTraversable(true);
//        view.getRoot().requestFocus();
//    }

    private void setupKeyHandling() {
        view.getRoot().setOnKeyPressed(this::handleKeyPressed);
        view.getRoot().setFocusTraversable(true);

        // Add this to ensure focus is gained when clicked
        view.getRoot().setOnMouseClicked(e -> view.getRoot().requestFocus());

        // Request focus after scene is shown
        Platform.runLater(() -> {
            view.getRoot().requestFocus();
        });
    }

    private void handleKeyPressed(KeyEvent event) {
        System.out.println("Key pressed: " + event.getCode());
        if (model.isGameOver()) {
            if (event.getCode() == KeyCode.R) {
                restartGame();
            }
            return;
        }

        switch (event.getCode()) {
            case LEFT:
            case A:
                model.movePieceLeft();
                break;
            case RIGHT:
            case D:
                model.movePieceRight();
                break;
            case DOWN:
            case S:
                model.movePieceDown();
                break;
            case UP:
            case W:
                model.rotatePiece();
                break;
            case SPACE:
                model.dropPiece();
                break;
            case P:
                model.togglePause();
                break;
            case R:
                model.resetGame();
                break;
        }

        event.consume();
    }

    public void startGame() {
        if (gameTimer != null) {
            gameTimer.stop();
        }

        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (model.isGameOver() || model.isPaused()) {
                    return;
                }
                // Keep everything in nanoseconds
                if (now - lastDropTime >= model.getDropInterval() * 1_000_000) {
                    model.movePieceDown();
                    lastDropTime = now;
                }
            }
        };

        gameTimer.start();
        lastDropTime = System.nanoTime(); // Use nanoseconds
    }

    public void stopGame() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    private void togglePause() {
        model.togglePause();
    }

    private void restartGame() {
        model.resetGame();
        lastDropTime = System.nanoTime();
    }

    @Override
    public void onModelChanged() {
        updateView();
    }

    private void updateView() {
        // Update game board
        view.getGameBoardView().updateBoard(model.getBoard(), model.getCurrentPiece());

        // Update next piece
        view.getNextPieceView().updateNextPiece(model.getNextPiece());

        // Update score and stats
        view.updateScore(model.getScore());
        view.updateLevel(model.getLevel());
        view.updateLines(model.getLinesCleared());

        // Update game status
        updateGameStatus();

        // Update pause button
        view.updatePauseButton(model.isPaused());
    }

    private void updateGameStatus() {
        if (model.isGameOver()) {
            view.updateGameStatus("GAME OVER! Press R to restart");
            view.getGameStatusLabel().getStyleClass().removeAll("paused-status");
            view.getGameStatusLabel().getStyleClass().add("game-over-status");
        } else if (model.isPaused()) {
            view.updateGameStatus("PAUSED");
            view.getGameStatusLabel().getStyleClass().removeAll("game-over-status");
            view.getGameStatusLabel().getStyleClass().add("paused-status");
        } else {
            view.updateGameStatus("");
            view.getGameStatusLabel().getStyleClass().removeAll("game-over-status", "paused-status");
        }
    }
}