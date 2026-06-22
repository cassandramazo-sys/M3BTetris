package ph.edu.dlsu.lbycpob.tetris.model.pieces;


public class IPiece extends TetrominoBase {


    public IPiece() {
        super(new int[][][] {
                {
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {0, 0, 1, 0},
                        {0, 0, 1, 0},
                        {0, 0, 1, 0},
                        {0, 0, 1, 0}
                }
        }, TetrominoColor.CYAN);
    }
}
