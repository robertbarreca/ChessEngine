package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

/**
 * This class represents a move on a chessboard
 */
public abstract class Move {
    
    final Board board;
    final Piece movedPiece;
    final int destCoord;

    /**
     * Constructor for a move
     * @param board the board the move is being played on
     * @param movedPiece the piece being moved
     * @param destCoord the destination of the piece
     */
    private Move(final Board board, final Piece movedPiece, final int destCoord) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destCoord = destCoord;
    }

    /**
     * gets the position of the piece's destination
     * @return the position of the piece's destination
     */
    public int getDestCoord() {
        return this.destCoord;
    }

    /**
     * Converts a move to it's string representation
     * @return the String representation of a move
     */
    @Override
    public String toString() {
        return ("(" + this.movedPiece.getPosition() + ", " + destCoord + ")");
    }

    public abstract Board execute();

    /**
    * This class represents a non-attacking move on a chessboard
    */
    public static final class PassiveMove extends Move {
        /**
         * Constructor for a move
         * @param board the board the move is being played on
         * @param movedPiece the piece being moved
         * @param destCoord the destination of the piece
         */
        public PassiveMove(final Board board, final Piece movedPiece, final int destCoord) {
            super(board, movedPiece, destCoord);
        }

        @Override
        public Board execute() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'execute'");
        }
    }

    /**
     * This class represents a attack move on a chessboard
     */
    public static final class AttackMove extends Move {
        final Piece capturedPiece;

        /**
         * Constructor for a move
         * @param board the board the move is being played on
         * @param movedPiece the piece being moved
         * @param destCoord the destination of the piece
         * @param capturedPiece the piece being attacked 
         */
        public AttackMove(final Board board, final Piece movedPiece, final int destCoord, final Piece capturedPiece) {
            super(board, movedPiece, destCoord);
            this.capturedPiece = capturedPiece;
        }

        @Override
        public Board execute() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'execute'");
        }
    }
}
