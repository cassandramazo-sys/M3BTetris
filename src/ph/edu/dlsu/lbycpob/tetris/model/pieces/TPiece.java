package ph.edu.dlsu.lbycpob.tetris.model.pieces;

public class TPiece extends TetrominoBase {
    public TPiece() {
        super(new int[][][] {
                {
                        {0, 1, 0},
                        {1, 1, 1},
                        {0, 0, 0}
                },
                {
                        {0, 1, 0},
                        {0, 1, 1},
                        {0, 1, 0}
                },
                {
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 1, 0}
                },
                {
                        {0, 1, 0},
                        {1, 1, 0},
                        {0, 1, 0}
                }
        }, TetrominoColor.PURPLE);
    }
}
