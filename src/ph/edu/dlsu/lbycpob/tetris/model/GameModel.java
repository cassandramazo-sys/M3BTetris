package ph.edu.dlsu.lbycpob.tetris.model;

import javafx.beans.property.*;
import ph.edu.dlsu.lbycpob.tetris.model.pieces.*;

import java.util.*;

public class GameModel {
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int CELL_SIZE = 30;

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty level = new SimpleIntegerProperty(1);
    private final IntegerProperty linesCleared = new SimpleIntegerProperty(0);
    private final BooleanProperty gameOver = new SimpleBooleanProperty(false);
    private final BooleanProperty paused = new SimpleBooleanProperty(false);

    private final GameBoard board;
    private TetrominoBase currentPiece;
    private TetrominoBase nextPiece;
    private final Random random = new Random();
    private final List<GameModelListener> listeners = new ArrayList<>();

    public GameModel() {
        this.board = new GameBoard(BOARD_WIDTH, BOARD_HEIGHT);
        generateNewPiece();
        generateNextPiece();
    }

    // Property getters
    public IntegerProperty scoreProperty() { return score; }
    public IntegerProperty levelProperty() { return level; }
    public IntegerProperty linesClearedProperty() { return linesCleared; }
    public BooleanProperty gameOverProperty() { return gameOver; }
    public BooleanProperty pausedProperty() { return paused; }

    public int getScore() { return score.get(); }
    public int getLevel() { return level.get(); }
    public int getLinesCleared() { return linesCleared.get(); }
    public boolean isGameOver() { return gameOver.get(); }
    public boolean isPaused() { return paused.get(); }

    public GameBoard getBoard() { return board; }
    public TetrominoBase getCurrentPiece() { return currentPiece; }
    public TetrominoBase getNextPiece() { return nextPiece; }

    public void addListener(GameModelListener listener) {
        listeners.add(listener);
    }

    public void removeListener(GameModelListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        listeners.forEach(GameModelListener::onModelChanged);
    }

    public void resetGame() {
        board.clear();
        score.set(0);
        level.set(1);
        linesCleared.set(0);
        gameOver.set(false);
        paused.set(false);
        generateNewPiece();
        generateNextPiece();
        notifyListeners();
    }

    public void togglePause() {
        paused.set(!paused.get());
        notifyListeners();
    }

    public boolean movePieceLeft() {
        if (gameOver.get() || paused.get() || currentPiece == null) return false;

        if (board.isValidPosition(currentPiece, currentPiece.getX() - 1, currentPiece.getY())) {
            currentPiece.moveLeft();
            notifyListeners();
            return true;
        }
        return false;
    }

    public boolean movePieceRight() {
        if (gameOver.get() || paused.get() || currentPiece == null) return false;

        if (board.isValidPosition(currentPiece, currentPiece.getX() + 1, currentPiece.getY())) {
            currentPiece.moveRight();
            notifyListeners();
            return true;
        }
        return false;
    }

    public boolean movePieceDown() {
        if (gameOver.get() || paused.get() || currentPiece == null) return false;

        if (board.isValidPosition(currentPiece, currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.moveDown();
            notifyListeners();
            return true;
        } else {
            // Piece has landed
            lockPiece();
            return false;
        }
    }

    public boolean rotatePiece() {
        if (gameOver.get() || paused.get() || currentPiece == null) return false;

        currentPiece.rotate();
        if (!board.isValidPosition(currentPiece, currentPiece.getX(), currentPiece.getY())) {
            // Try wall kicks
            int[] kicksX = {-1, 1, -2, 2};
            int[] kicksY = {0, 0, 0, 0};

            boolean validRotation = false;
            for (int i = 0; i < kicksX.length; i++) {
                if (board.isValidPosition(currentPiece, currentPiece.getX() + kicksX[i],
                        currentPiece.getY() + kicksY[i])) {
                    currentPiece.setPosition(currentPiece.getX() + kicksX[i],
                            currentPiece.getY() + kicksY[i]);
                    validRotation = true;
                    break;
                }
            }

            if (!validRotation) {
                currentPiece.rotateBack();
                return false;
            }
        }

        notifyListeners();
        return true;
    }

    public void dropPiece() {
        if (gameOver.get() || paused.get() || currentPiece == null) return;

        while (movePieceDown()) {
            // Keep dropping until it can't move down
        }
        addScore(20); // Bonus for hard drop
    }

    private void lockPiece() {
        if (currentPiece == null) return;

        board.placePiece(currentPiece);

        // Check for completed lines
        int clearedLines = board.clearCompletedLines();
        if (clearedLines > 0) {
            updateScore(clearedLines);
            linesCleared.set(linesCleared.get() + clearedLines);
            updateLevel();
        }

        // Generate next piece
        currentPiece = nextPiece;
        generateNextPiece();

        // Check game over
        if (!board.isValidPosition(currentPiece, currentPiece.getX(), currentPiece.getY())) {
            gameOver.set(true);
        }
        notifyListeners();
    }

    private void generateNewPiece() {
        currentPiece = createRandomTetromino();
        currentPiece.setPosition(BOARD_WIDTH / 2 - 1, 0);
    }

    private void generateNextPiece() {
        nextPiece = createRandomTetromino();
    }

    private TetrominoBase createRandomTetromino() {
        TetrominoType[] types = TetrominoType.values();
        TetrominoType randomType = types[random.nextInt(types.length)];

        return switch (randomType) {
            case I -> new IPiece();
            case O -> new OPiece();
            case T -> new TPiece();
            case S -> new SPiece();
            case Z -> new ZPiece();
            case J -> new JPiece();
            case L -> new LPiece();
            case C -> new CPiece();
        };
    }

    private void updateScore(int clearedLines) {
        int baseScore = switch (clearedLines) {
            case 1 -> 100;
            case 2 -> 300;
            case 3 -> 500;
            case 4 -> 800;
            default -> 0;
        };
        addScore(baseScore * level.get());
    }

    private void addScore(int points) {
        score.set(score.get() + points);
    }

    private void updateLevel() {
        int newLevel = Math.min(10, (linesCleared.get() / 10) + 1);
        level.set(newLevel);
    }

    public long getDropInterval() {
        return Math.max(50, 500 - (level.get() - 1) * 50);
    }
}
