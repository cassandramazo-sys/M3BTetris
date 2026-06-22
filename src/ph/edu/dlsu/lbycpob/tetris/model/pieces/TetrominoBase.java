package ph.edu.dlsu.lbycpob.tetris.model.pieces;

/**
 * TetrominoBase - The foundation class for all Tetris pieces
 *
 * This is an abstract class, which means:
 * - You cannot create objects directly from this class
 * - Other classes must extend (inherit from) this class
 * - It provides common functionality that all Tetris pieces share
 *
 * Think of this as a blueprint that defines what every Tetris piece can do.
 */
public abstract class TetrominoBase {

    // === INSTANCE VARIABLES (Properties of each piece) ===

    /**
     * x and y represent the position of the piece on the game board
     * x = horizontal position (left/right)
     * y = vertical position (up/down)
     * protected means: only this class and its subclasses can access these variables
     */
    protected int x, y;

    /**
     * currentRotation keeps track of which rotation the piece is currently in
     * Most Tetris pieces can be rotated into 4 different orientations (0, 1, 2, 3)
     * We start at rotation 0 (the default orientation)
     */
    protected int currentRotation = 0;

    /**
     * shapes is a 3D array that stores all possible rotations of this piece
     * Think of it like this:
     * - shapes[0] = first rotation of the piece
     * - shapes[1] = second rotation of the piece
     * - shapes[2] = third rotation of the piece, etc.
     *
     * Each rotation is a 2D array representing the piece's pattern:
     * For example, an L-piece might look like:
     * [1, 0, 0]
     * [1, 1, 1]  where 1 = filled block, 0 = empty space
     *
     * final means this reference cannot be changed after construction
     */
    protected final int[][][] shapes;

    /**
     * color stores what color this piece should be when drawn
     * Each type of Tetris piece traditionally has its own color
     * final means the color cannot be changed after construction
     */
    protected final TetrominoColor color;

    // === CONSTRUCTOR ===

    /**
     * Constructor - This runs when a new Tetris piece is created
     *
     * @param shapes - All the rotation patterns for this piece type
     * @param color - The color this piece should be displayed in
     */
    public TetrominoBase(int[][][] shapes, TetrominoColor color) {
        this.shapes = shapes;
        this.color = color;
        this.x = 0;
        this.y = 0;
    }

    // Getters (Allow other classes to read our private data)
    public int getX() { return x; }
    public int getY() { return y; }
    public TetrominoColor getColor() { return color; }
    public int getCurrentRotation() { return currentRotation; }

    /**
     * Get the current shape pattern of the piece
     * This returns the 2D array representing how the piece looks right now
     *
     * @return 2D array where 1 = filled block, 0 = empty space
     */
    public int[][] getCurrentShape() {
        return shapes[currentRotation];
    }

    // Movement methods (Allow the piece to be moved around)
    /**
     * Set the piece to a specific position on the board
     * This is like picking up the piece and placing it somewhere new
     *
     * @param x - new horizontal position
     * @param y - new vertical position
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Move the piece one space to the left
     * Decreases x coordinate by 1
     */
    public void moveLeft() { x--; }

    /**
     * Move the piece one space to the right
     * Increases x coordinate by 1
     */
    public void moveRight() { x++; }

    /**
     * Move the piece one space downward
     * Increases y coordinate by 1 (in most coordinate systems, y increases downward)
     */
    public void moveDown() { y++; }

    // === ROTATION METHODS ===

    /**
     * Rotate the piece clockwise to its next orientation
     *
     * The modulo (%) operation ensures we cycle through rotations:
     * If we have 4 rotations (0,1,2,3), after rotation 3 we go back to 0
     *
     * Example: if currentRotation = 2 and shapes.length = 4:
     * currentRotation = (2 + 1) % 4 = 3 % 4 = 3
     *
     * Example: if currentRotation = 3 and shapes.length = 4:
     * currentRotation = (3 + 1) % 4 = 4 % 4 = 0 (back to first rotation)
     */
    public void rotate() {
        currentRotation = (currentRotation + 1) % shapes.length;
    }

    /**
     * Rotate the piece counter-clockwise to its previous orientation
     *
     * This is more complex because we need to handle negative numbers:
     * - Subtract 1 from current rotation
     * - Add shapes.length to handle negative case
     * - Use modulo to wrap around
     *
     * Example: if currentRotation = 0 and shapes.length = 4:
     * currentRotation = (0 - 1 + 4) % 4 = 3 % 4 = 3 (wraps to last rotation)
     */
    public void rotateBack() {
        currentRotation = (currentRotation - 1 + shapes.length) % shapes.length;
    }
}

