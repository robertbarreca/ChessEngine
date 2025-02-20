package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

public class BlackPlayer extends Player{

    /**
     * Constructor that creates a black player
     * @param board the board used to play the game
     * @param whiteMoves All legal moves that white can make
     * @param blackMoves All legal moves that black can make
     */
    public BlackPlayer
        (final Board board, final Collection<Move> whiteMoves, final Collection<Move> blackMoves) {
        super(board, blackMoves, whiteMoves);
    }

    /**
     * Gets all the active pieces for the player
     * @return All black pieces on the board
     */
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    /**
     * Gets the alliance of the player
     * @return the black alliance
     */
    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    /**
     * get the opposing player
     * @return the white player
     */
    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }

        
    
}
