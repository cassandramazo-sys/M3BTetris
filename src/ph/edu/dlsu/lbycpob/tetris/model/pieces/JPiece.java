package ph.edu.dlsu.lbycpob.tetris.model.pieces;


public class JPiece extends TetrominoBase {
    public JPiece() {
        super(new int[][][] {
                {
                        {1, 0, 0},
                        {1, 1, 1},
                        {0, 0, 0}
                },
                {
                        {0, 1, 1},
                        {0, 1, 0},
                        {0, 1, 0}
                },
                {
                        {0, 0, 0},
                        {1, 1, 1},
                        {0, 0, 1}
                },
                {
                        {0, 1, 0},
                        {0, 1, 0},
                        {1, 1, 0}
                }
        }, TetrominoColor.BLUE);
    }
}
