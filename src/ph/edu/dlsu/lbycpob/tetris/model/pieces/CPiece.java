package ph.edu.dlsu.lbycpob.tetris.model.pieces;

public class CPiece extends TetrominoBase{
    public CPiece() {
        super(new int[][][] {
                {
                        {1, 1, 1},
                        {1, 0, 0},
                        {1, 1, 1}
                },
                {
                        {1, 1, 1},
                        {1, 0, 1},
                        {1, 0, 1}
                },
                {
                        {1, 1, 1},
                        {0, 0, 1},
                        {1, 1, 1}
                },
                {
                        {1, 0, 1},
                        {1, 0, 1},
                        {1, 1, 1}
                }
        }, TetrominoColor.BEIGE);
    }
}
