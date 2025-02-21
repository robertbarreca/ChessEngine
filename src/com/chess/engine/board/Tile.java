package com.chess.engine.board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.chess.engine.pieces.Piece;

/**
 * The Tile class represents a single square on a chessboard.
 * It is an abstract class that can either be an EmptyTile or an OccupiedTile.
 */
public abstract class Tile {
    
    protected final int coord;

    // cache all empty tiles for future use
    private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();
    
    /**
     * creates all possible empty tiles and makes it immutable
     * @return Immutable Map of all possible empty tiles
     */
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        // make all possible empty tiles
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        // make empty tiles immutable
        return Collections.unmodifiableMap(emptyTileMap);
    }

    public static Tile createTile(final int coord, final Piece piece) {
        // create new tile if piece exists or cache tile if empty
        return piece != null ? new OccupiedTile(coord, piece) : EMPTY_TILES.get(coord);
    }

    /**
     * Constructor that sets the coordinate of the tile
     * 
     * @param coord the coordinate of the tile
     */
    private Tile(final int coord) {
        this.coord = coord;
    }

    /**
     * Gets the coordinate of the tile
     * @return the tile coordinate
     */
    public int getCoord() {
        return this.coord;
    }
    
    
    /**
     * Determines whether a tile is occupied by a piece
     * 
     * @return ture if it is occupied, and false otherwise
     */
    public abstract boolean isOccupied();

    /**
     * Retrieves the piece occupying the tile 
     * @return the piece occupying the tile
     */
    public abstract Piece getPiece();

    /**
     * This class represents an empty tile on a chessboard.
     */
    public static final class EmptyTile extends Tile {
        /**
         * Constructor that sets the coordinate of the tile
         * @param coord the coordinate of the tile
         */
        private EmptyTile(final int coord) {
            super(coord);
        }

        /**
         * Converts the tile to a string representation
         * @returns the string representation of an unoccupied tile
         */
        @Override
        public String toString() {
            return "-"; 
        }

        /**
         * Checks whether a tile is occupied by a piece
         * @return false, since the tile will always be unoccupied
         */
        @Override
        public boolean isOccupied() {
            return false;
        }

        /**
         * Gets the piece that is occupying the tile
         * @return null since tile will always be unoccupied
         */
        @Override
        public Piece getPiece() {
            return null;
        }
    }
    
    /*
     * Represents a tile that is occupied by a piece
     */
    public static final class OccupiedTile extends Tile {
        private final Piece piece;

        /**
         * Constructor that sets the coordinate and the piece that is occupying the tile
         * @param coord the coordinate of the tile
         * @param piece the piece that is occupying the tile
         */
        private OccupiedTile(final int coord, final Piece piece) {
            super(coord);
            this.piece = piece;
        }

        /**
         * Converts the tile to a string representation
         * @returns the string representation of the piece on the tile
         */
        @Override
        public String toString() {
            return this.getPiece().getAlliance().isBlack() ? 
                    this.getPiece().toString().toLowerCase() : 
                    this.getPiece().toString();
        }

        /**
         * Checks whether a tile is occupied by a piece
         * @return true, since the tile will always be occupied
         */
        @Override
        public boolean isOccupied() {
            return true;
        }

        /**
         * Gets the piece that is occupying the tile
         * @return the piece occupying the tile
         */
        @Override
        public Piece getPiece() {
            return this.piece;
        }
    }
}
