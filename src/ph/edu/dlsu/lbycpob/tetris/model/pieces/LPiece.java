package ph.edu.dlsu.lbycpob.tetris.model.pieces;


public class LPiece extends TetrominoBase {
    public LPiece() {
        super(new int[][][] {
                {
                        {0, 0, 1},
                        {1, 1, 1},
                        {0, 0, 0}
                },
                {
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 1}
                },
                {
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 0}
                },
                {
                        {1, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0}
                }
        }, TetrominoColor.ORANGE);
    }
}
