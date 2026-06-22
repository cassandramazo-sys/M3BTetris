package ph.edu.dlsu.lbycpob.tetris.view;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ph.edu.dlsu.lbycpob.tetris.model.GameBoard;
import ph.edu.dlsu.lbycpob.tetris.model.GameModel;
import ph.edu.dlsu.lbycpob.tetris.model.pieces.TetrominoBase;
import ph.edu.dlsu.lbycpob.tetris.model.pieces.TetrominoColor;


public class GameBoardView {
    private final GridPane gridPane;
    private final Rectangle[][] cells;
    private final int width;
    private final int height;
    private final int cellSize;

    public GameBoardView() {
        this.width = GameModel.BOARD_WIDTH;
        this.height = GameModel.BOARD_HEIGHT;
        this.cellSize = GameModel.CELL_SIZE;

        gridPane = new GridPane();
        gridPane.getStyleClass().add("game-board");

        cells = new Rectangle[height][width];
        initializeCells();
    }

    private void initializeCells() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.getStyleClass().add("board-cell");
                cell.setStroke(Color.DARKGRAY);
                cell.setStrokeWidth(0.5);

                cells[y][x] = cell;
                gridPane.add(cell, x, y);
            }
        }
    }

    public GridPane getRoot() {
        return gridPane;
    }

    public void updateBoard(GameBoard board, TetrominoBase currentPiece) {
        // Clear and update board
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                TetrominoColor color = board.getCell(x, y);
                updateCell(x, y, color);
            }
        }

        // Draw current piece
        if (currentPiece != null) {
            drawPiece(currentPiece);
        }
    }

    private void drawPiece(TetrominoBase piece) {
        int[][] shape = piece.getCurrentShape();
        TetrominoColor color = piece.getColor();

        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x] == 1) {
                    int boardX = piece.getX() + x;
                    int boardY = piece.getY() + y;

                    if (boardX >= 0 && boardX < width && boardY >= 0 && boardY < height) {
                        updateCell(boardX, boardY, color);
                    }
                }
            }
        }
    }

    private void updateCell(int x, int y, TetrominoColor color) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            Rectangle cell = cells[y][x];
            cell.setFill(Color.web(color.getHexValue()));

            if (color == TetrominoColor.EMPTY) {
                cell.getStyleClass().removeAll("filled-cell");
                cell.getStyleClass().add("empty-cell");
            } else {
                cell.getStyleClass().removeAll("empty-cell");
                cell.getStyleClass().add("filled-cell");
            }
        }
    }
}