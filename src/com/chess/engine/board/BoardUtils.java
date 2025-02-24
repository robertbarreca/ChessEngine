package com.chess.engine.board;


/**
 * Utility class for board-related constants and methods in a chess engine.
 */
public class BoardUtils {

    public static final boolean[] COLUMN_A = initColumn(0);
    public static final boolean[] COLUMN_B = initColumn(1);
    public static final boolean[] COLUMN_G = initColumn(6);
    public static final boolean[] COLUMN_H = initColumn(7);

    public static final boolean[] RANK_8 = initRow(0);
    public static final boolean[] RANK_7 = initRow(8);
    public static final boolean[] RANK_6 = initRow(16);
    public static final boolean[] RANK_5 = initRow(24);
    public static final boolean[] RANK_4 = initRow(32);
    public static final boolean[] RANK_3 = initRow(40);
    public static final boolean[] RANK_2 = initRow(48);
    public static final boolean[] RANK_1 = initRow(56);
    
    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    /**
     * Initializes a boolean array representing a specific column on the chessboard.
     * 
     * @param colNum The column index to initialize.
     * @return A boolean array where only the tiles in the specified column are set to true.
     */
    private static boolean[] initColumn(int colNum) {
        final boolean[] column = new boolean[NUM_TILES];
        do {
            column[colNum] = true;
            colNum += NUM_TILES_PER_ROW;
        } while (colNum < NUM_TILES);

        return column;
    }
    
    private static boolean[] initRow(int rowNum){
        final boolean[] row = new boolean[NUM_TILES];
        do {
            row[rowNum] = true;
            rowNum++;
        } while (rowNum % NUM_TILES_PER_ROW != 0);
        return row;
    }

    /**
     * Constructor to prevent instantiation of this utility class.
     * @throws a RuntimeException if an attempt is made to instantiate.
     */
    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate BoardUtils!");
    }

    /**
     * Checks if a given tile coordinate is within the range of the chessboard.
     * 
     * @param coord The tile coordinate to check.
     * @return true if the coordinate is in bounds, and false otherwise.
     */
    public static boolean isValidTileCoord(final int coord) {
            return coord >= 0 && coord < NUM_TILES;
        }
}
