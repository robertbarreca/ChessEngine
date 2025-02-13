package com.chess.engine.board;

import java.util.HashMap;
import java.util.Map;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

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
        for (int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        // make empty tiles immutable
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int coord, final Piece piece) {
        // create new tile if piece exists or get cached tile if empty
        return piece != null ? new OccupiedTile(coord, piece) : EMPTY_TILES.get(coord);
    }

    /**
     * Constructor that sets the coordinate of the tile
     * 
     * @param coord the coordinate of the tile
     */
    private Tile(int coord) {
        this.coord = coord;
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
        private OccupiedTile(final int coord, Piece piece) {
            super(coord);
            this.piece = piece;
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
