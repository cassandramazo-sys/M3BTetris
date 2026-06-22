package ph.edu.dlsu.lbycpob.tetris.model.pieces;

public enum TetrominoColor {
    EMPTY("#f5f5f5"),
    CYAN("#00ffff"),
    BLUE("#0000ff"),
    ORANGE("#ff8c00"),
    YELLOW("#ffff00"),
    GREEN("#00ff00"),
    PURPLE("#8a2be2"),
    RED("#ff0000"),
    BEIGE("#ede8d0");

    private final String hexValue;

    TetrominoColor(String hexValue) {
        this.hexValue = hexValue;
    }

    public String getHexValue() {
        return hexValue;
    }
}
