package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {
    
    final Board board;
    final Piece movedPiece;
    final int destCoord;

    private Move(final Board board, final Piece movedPiece, final int destCoord) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destCoord = destCoord;
    }

    public static final class PassiveMove extends Move {
        public PassiveMove(final Board board, final Piece movedPiece, final int destCoord) {
            super(board, movedPiece, destCoord);
        }
    }

    @Override
    public String toString() {
        return ("(" + this.movedPiece.getPosition() + ", " + destCoord + ")");
    }

    public static final class AttackMove extends Move {
        final Piece capturedPiece;

        public AttackMove(final Board board, final Piece movedPiece, final int destCoord, final Piece capturedPiece) {
            super(board, movedPiece, destCoord);
            this.capturedPiece = capturedPiece;
        }
    }

}
