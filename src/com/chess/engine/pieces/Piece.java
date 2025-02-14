package com.chess.engine.pieces;

import java.util.Collection;
import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

/**
 * This class represents a single piece on a chessboard
 */
public abstract class Piece {
    protected final int position;
    protected final Alliance alliance;

    /**
     * Constructor that sets the position and alliance of the piece
     * 
     * @param position the location on a chessboard
     * @param alliance the team the piece is aligned with
     */
    Piece(int position, Alliance alliance) {
        this.position = position;
        this.alliance = alliance;
    }

    /**
     * Gets the alliance of a piece 
     * @return the alliance of the piece
     */
    public Alliance getAlliance() {
        return this.alliance;
    }
    
    /**
     * Calculates all legal moves that a piece can make
     * @param board the current state of the board
     * @return a list of all legal moves a piece can make
     */
    public abstract Collection<Move> calcLegalMoves(final Board board);
}
