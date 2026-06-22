package ph.edu.dlsu.lbycpob.tetris.view;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ph.edu.dlsu.lbycpob.tetris.model.pieces.TetrominoBase;
import ph.edu.dlsu.lbycpob.tetris.model.pieces.TetrominoColor;

public class NextPieceView {
    private static final int PREVIEW_SIZE = 4;
    private static final int CELL_SIZE = 20;

    private final GridPane gridPane;
    private final Rectangle[][] cells;

    public NextPieceView() {
        gridPane = new GridPane();
        gridPane.getStyleClass().add("next-piece-view");

        cells = new Rectangle[PREVIEW_SIZE][PREVIEW_SIZE];
        initializeCells();
    }

    private void initializeCells() {
        for (int y = 0; y < PREVIEW_SIZE; y++) {
            for (int x = 0; x < PREVIEW_SIZE; x++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.getStyleClass().add("preview-cell");
                cell.setFill(Color.web(TetrominoColor.EMPTY.getHexValue()));
                cell.setStroke(Color.LIGHTGRAY);
                cell.setStrokeWidth(0.5);

                cells[y][x] = cell;
                gridPane.add(cell, x, y);
            }
        }
    }

    public GridPane getRoot() {
        return gridPane;
    }

    public void updateNextPiece(TetrominoBase nextPiece) {
        // Clear preview area
        for (int y = 0; y < PREVIEW_SIZE; y++) {
            for (int x = 0; x < PREVIEW_SIZE; x++) {
                cells[y][x].setFill(Color.web(TetrominoColor.EMPTY.getHexValue()));
            }
        }

        if (nextPiece != null) {
            int[][] shape = nextPiece.getCurrentShape();
            TetrominoColor color = nextPiece.getColor();

            // Center the piece in the preview area
            int offsetX = (PREVIEW_SIZE - shape[0].length) / 2;
            int offsetY = (PREVIEW_SIZE - shape.length) / 2;

            for (int y = 0; y < shape.length; y++) {
                for (int x = 0; x < shape[y].length; x++) {
                    if (shape[y][x] == 1) {
                        int previewX = offsetX + x;
                        int previewY = offsetY + y;

                        if (previewX >= 0 && previewX < PREVIEW_SIZE &&
                                previewY >= 0 && previewY < PREVIEW_SIZE) {
                            cells[previewY][previewX].setFill(Color.web(color.getHexValue()));
                        }
                    }
                }
            }
        }
    }
}
