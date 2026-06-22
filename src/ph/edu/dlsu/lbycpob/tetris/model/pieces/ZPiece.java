package ph.edu.dlsu.lbycpob.tetris.model.pieces;

public class ZPiece extends TetrominoBase {
    public ZPiece() {
        super(new int[][][] {
                {
                        {1, 1, 0},
                        {0, 1, 1},
                        {0, 0, 0}
                },
                {
                        {0, 0, 1},
                        {0, 1, 1},
                        {0, 1, 0}
                }
        }, TetrominoColor.RED);
    }
}
