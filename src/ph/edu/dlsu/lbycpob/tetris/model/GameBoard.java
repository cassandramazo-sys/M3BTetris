package ph.edu.dlsu.lbycpob.tetris.model;


import ph.edu.dlsu.lbycpob.tetris.model.pieces.TetrominoBase;
import ph.edu.dlsu.lbycpob.tetris.model.pieces.TetrominoColor;

public class GameBoard {
    private final int width;
    private final int height;
    private final TetrominoColor[][] grid;

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new TetrominoColor[height][width];
        clear();
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public TetrominoColor getCell(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return grid[y][x];
    }

    public void setCell(int x, int y, TetrominoColor color) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[y][x] = color;
        }
    }

    public void clear() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = TetrominoColor.EMPTY;
            }
        }
    }

    public boolean isValidPosition(TetrominoBase piece, int newX, int newY) {
        if (piece == null) return false;

        int[][] shape = piece.getCurrentShape();

        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x] == 1) {
                    int boardX = newX + x;
                    int boardY = newY + y;

                    // Check boundaries
                    if (boardX < 0 || boardX >= width || boardY >= height) {
                        return false;
                    }

                    // Check collision with existing pieces (but allow negative Y for spawning)
                    if (boardY >= 0 && grid[boardY][boardX] != TetrominoColor.EMPTY) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void placePiece(TetrominoBase piece) {
        if (piece == null) return;

        int[][] shape = piece.getCurrentShape();
        TetrominoColor color = piece.getColor();

        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x] == 1) {
                    int boardX = piece.getX() + x;
                    int boardY = piece.getY() + y;

                    if (boardX >= 0 && boardX < width && boardY >= 0 && boardY < height) {
                        grid[boardY][boardX] = color;
                    }
                }
            }
        }
    }

    public int clearCompletedLines() {
        int clearedCount = 0;

        for (int y = height - 1; y >= 0; y--) {
            if (isLineFull(y)) {
                clearLine(y);
                dropLinesAbove(y);
                clearedCount++;
                y++; // Check the same line again as lines above have dropped
            }
        }

        return clearedCount;
    }

    private boolean isLineFull(int y) {
        for (int x = 0; x < width; x++) {
            if (grid[y][x] == TetrominoColor.EMPTY) {
                return false;
            }
        }
        return true;
    }

    private void clearLine(int y) {
        for (int x = 0; x < width; x++) {
            grid[y][x] = TetrominoColor.EMPTY;
        }
    }

    private void dropLinesAbove(int clearedLine) {
        for (int y = clearedLine; y > 0; y--) {
            System.arraycopy(grid[y - 1], 0, grid[y], 0, width);
        }
        // Clear the top line
        for (int x = 0; x < width; x++) {
            grid[0][x] = TetrominoColor.EMPTY;
        }
    }
}
