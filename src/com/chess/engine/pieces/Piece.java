package com.chess.engine.pieces;

import java.util.Collection;
import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

/**
 * This class represents a single piece on a chessboard
 */
public abstract class Piece {
    protected final PieceType pieceType;
    protected final int position;
    protected final Alliance alliance;
    protected final boolean hasMoved;
    private final int cachedHashCode;

    /**
     * Constructor that sets the position and alliance of the piece
     * 
     * @param position the location on a chessboard
     * @param alliance the team the piece is aligned with
     * @param hasMoved says whether the piece has moved or not
     */
    Piece(final PieceType pieceType, final Alliance alliance, final int position, final boolean hasMoved) {
        this.pieceType = pieceType;
        this.position = position;
        this.alliance = alliance;
        this.hasMoved = hasMoved;
        this.cachedHashCode = computeHashCode();
    }

    /**
     * Computes the hash of a piece
     * @return the hascode of a piece
     */
    private int computeHashCode() {
        int res = this.pieceType.hashCode();
        res = 31 * res + this.alliance.hashCode();
        res = 31 * res + this.position;
        res = 31 * res + (this.hasMoved ? 1 : 0);
        return res;
    }
    
    /**
     * Checks whether to passed object is equivlant to the current piece
     * @param other the object we are comparing to
     * @return true if they are both the same type of piece, have the same alliance, have the same position, and both have or have not moved
     */
    @Override
    public boolean equals(final Object other) {
        // referentially the same
        if (this == other) {
            return true;
        }
        // not a piece
        if (!(other instanceof Piece)) {
            return false;
        }

        // check member field equality
        final Piece otherPiece = (Piece) other;
        return this.position == otherPiece.position && this.pieceType == otherPiece.pieceType
                && this.alliance == otherPiece.alliance && this.hasMoved == otherPiece.hasMoved;
    }
    
    /**
     * Gets the hashcode of a piece
     * @return the hashcode of a piece
     */
    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }

    /**
     * Gets the alliance of a piece 
     * @return the alliance of the piece
     */
    public Alliance getAlliance() {
        return this.alliance;
    }

    /**
     * Says whether a piece has moved or not
     * @return the member var hasMoved
     */
    public boolean hasMoved() {
        return this.hasMoved;
    }

    /**
     * Gets the current coordinate of a piece
     * @return the current coordinate of a piece
     */
    public int getPosition() {
        return this.position;
    }
    
    /**
     * gets the piece's type
     * @return the piece's type
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    public int getValue() {
        return this.pieceType.getValue();
    }
    
    /**
     * Calculates all legal moves that a piece can make
     * @param board the current state of the board
     * @return a list of all legal moves a piece can make
     */
    public abstract Collection<Move> calcLegalMoves(final Board board);

    /**
     * Creates a new piece based on the move
     * @param move the move that was made
     * @return the new piece created from the move
     */
    public abstract Piece movePiece(Move move);

    public enum PieceType {
        PAWN("P", 100),
        KNIGHT("N", 300),
        BISHOP("B", 305),
        ROOK("R", 500) {
            /**
            * Says whether the current piece type is a rook or not
            * @return true
            */
            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q", 900),
        KING("K", 10000) {
            /**
            * Says whether the current piece type is a king or not
            * @return true
            */
            @Override
            public boolean isKing(){
                return true;
            }
        };

        private String pieceName;
        private int value;

        /**
         * Constructor that sets the type of the piece
         * @param pieceName the name of the piece
         * @param value the value of a piece type
         */
        PieceType(final String pieceName, final int value) {
            this.pieceName = pieceName;
            this.value = value;
        }

        /**
         * Converts the PieceType to a string
         * @return the String representation of the piece type
         */
        @Override
        public String toString() {
            return this.pieceName;
        }

        public int getValue() {
            return this.value;
        }

        /**
         * Says whether the current piece is a king or not
         * @return false since most pieces aren't kings
         */
        public boolean isKing() {
            return false;
        }

        /**
         * Says whether the current piece is a rook or not
         * @return false since most pieces aren't rooks
         */
        public boolean isRook() {
            return false;
        }
    }
}
