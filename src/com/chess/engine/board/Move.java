package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

/**
 * This class represents a move on a chessboard
 */
public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destCoord;

    public static final Move NULL_MOVE = new NullMove();
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
     * gets the position the piece starts on 
     * @return the position the piece starts on 
     */ 
    public int getStartingCoord() {
        return this.movedPiece.getPosition();
    }
    /**
     * gets the position of the piece's destination
     * @return the position of the piece's destination
     */
    public int getDestCoord() {
        return this.destCoord;
    }

    /**
     * gets the moved piece
     * @return the moved piece
     */
    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    /**
     * Converts a move to it's string representation
     * @return the String representation of a move
     */
    @Override
    public String toString() {
        return ("(" + this.movedPiece.getPosition() + ", " + destCoord + ")");
    }

    /**
     * This method rebuilds the board based on the move made
     * @return the new board based on the move
     */
    public Board execute() {
        final Board.Builder builder = new Board.Builder();
        // set curr player's pieces except for moved piece
        for (final Piece piece : this.board.getCurrPlayer().getActivePieces()) {
            if (!piece.equals(this.movedPiece)) {
                builder.setPiece(piece);
            }
        }
        // set opponents pieces
        for (final Piece piece : this.board.getCurrPlayer().getOpponent().getActivePieces()) {
            if (!piece.equals(this.movedPiece)) {
                builder.setPiece(piece);
            }
        }
        // set movedPiece
        builder.setPiece(this.movedPiece.movePiece(this));
        // switch active player
        builder.setCurrPlayerAlliance(this.board.getCurrPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    /**
     * This class represents an invalid move on a chessboard
     */
    public static final class NullMove extends Move {
        /**
         * Constructor for a null move
         * @param board the board the move is being played on
         * @param movedPiece the piece being moved
         * @param destCoord the destination of the piece
         */
        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Cannot execute a null move!");
        }
    }

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
    }

    /**
     * This class represents a attack move on a chessboard
     */
    public static class AttackMove extends Move {
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

        /**
         * This method rebuilds the board based on the move made
         * @return the new board based on the move
         */
        @Override
        public Board execute() {
            return null;
        }
    }

    /**
     * This class represents a pawn move on a chessboard
     */
    public static final class PawnMove extends Move {
        /**
         * Constructor for a pawn move
         * @param board the board the move is being played on
         * @param movedPiece the pawn being moved
         * @param destCoord the destination of the pawn
         */
        public PawnMove(final Board board, final Piece movedPiece, final int destCoord) {
            super(board, movedPiece, destCoord);
        }
    }

    /**
     * This class represents a pawn jump move on a chessboard
     */
    public static final class PawnJumpMove extends Move {
        /**
         * Constructor for a move
         * @param board the board the move is being played on
         * @param movedPiece the pawn being moved
         * @param destCoord the destination of the pawn
         */
        public PawnJumpMove(final Board board, final Piece movedPiece, final int destCoord) {
            super(board, movedPiece, destCoord);
        }
    }

    /**
     * This class represents a pawn attack move on a chessboard
     */
    public static class PawnAttackMove extends AttackMove {
        /**
         * Constructor for a move
         * @param board the board the move is being played on
         * @param movedPiece the pawn being moved
         * @param destCoord the destination of the pawn
         * @param capturedPiece the piece the pawn is attacking 
         */
        public PawnAttackMove(final Board board, final Piece movedPiece, final int destCoord,
                final Piece capturedPiece) {
            super(board, movedPiece, destCoord, capturedPiece);
        }
    }

    // TODO: EN PASSANT MOVES
    // /**
    //  * This class represents an en Passant move on a chessboard
    //  */
    // public static class PawnEnPassantAttackMove extends PawnAttackMove {
    //     /**
    //      * Constructor for an en Passant move
    //      * @param board the board the move is being played on
    //      * @param movedPiece the pawn being moved
    //      * @param destCoord the destination of the pawn
    //      * @param capturedPiece the piece the pawn is attacking
    //      */
    //     public PawnEnPassantAttackMove(final Board board, final Piece movedPiece, final int destCoord,
    //             final Piece capturedPiece) {
    //         super(board, movedPiece, destCoord, capturedPiece);
    //     }
    // }
    // TODO: CASTLE MOVES
    // /**
    //  * This class represents a attack move on a chessboard
    //  */
    // public abstract class CastleMove extends Move {
    //     /**
    //      * Constructor for a move
    //      * @param board the board the move is being played on
    //      * @param movedPiece the piece being moved
    //      * @param destCoord the destination of the piece
    //      */
    //     public CastleMove(final Board board, final Piece movedPiece, final int destCoord) {
    //         super(board, movedPiece, destCoord);
    //     }
    // }

    // /**
    //  * This class represents a attack move on a chessboard
    //  */
    // public abstract class KingSideCastleMove extends CastleMove {
    //     /**
    //      * Constructor for a move
    //      * @param board the board the move is being played on
    //      * @param movedPiece the piece being moved
    //      * @param destCoord the destination of the piece
    //      */
    //     public KingSideCastleMove(final Board board, final Piece movedPiece, final int destCoord) {
    //         super(board, movedPiece, destCoord);
    //     }
    // }

    // /**
    //  * This class represents a attack move on a chessboard
    //  */
    // public abstract class QueenSideCastleMove extends CastleMove {
    //     /**
    //      * Constructor for a move
    //      * @param board the board the move is being played on
    //      * @param movedPiece the piece being moved
    //      * @param destCoord the destination of the piece
    //      */
    //     public QueenSideCastleMove(final Board board, final Piece movedPiece, final int destCoord) {
    //         super(board, movedPiece, destCoord);
    //     }
    // }

    public static class MoveFactory{

        private MoveFactory() {
            throw new RuntimeException("Not instantiable");
        }

        public static Move createMove(final Board board, final int currCoord, final int destCoord) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.getStartingCoord() == currCoord && move.getDestCoord() == destCoord) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}


