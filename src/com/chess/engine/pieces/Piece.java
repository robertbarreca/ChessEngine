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

    /**
     * Constructor that sets the position and alliance of the piece
     * 
     * @param position the location on a chessboard
     * @param alliance the team the piece is aligned with
     */
    Piece(final PieceType pieceType, final Alliance alliance, final int position) {
        this.pieceType = pieceType;
        this.position = position;
        this.alliance = alliance;
        this.hasMoved = false;
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
    
    public PieceType getPieceType() {
        return this.pieceType;
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
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K") {
            /**
            * Says whether the current piece is a king or not
            * @return true
            */
            @Override
            public boolean isKing(){
                return true;
            }
        };

        private String pieceName;

        /**
         * Constructor that sets the type of the piece
         * @param pieceName the name of the piece
         */
        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        /**
         * Converts the PieceType to a string
         * @return the String representation of the piece type
         */
        @Override
        public String toString() {
            return this.pieceName;
        }

        /**
         * Says whether the current piece is a king or not
         * @return false since most pieces aren't kings
         */
        public boolean isKing() {
            return false;
        }
    }
}
