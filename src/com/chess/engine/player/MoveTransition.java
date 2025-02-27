package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

/*
 * Represents the transition from one board to another after a move is made
 */
public class MoveTransition {

    private final Board updatedBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    MoveTransition(final Board updatedBoard, final Move move, final MoveStatus moveStatus) {
        this.updatedBoard = updatedBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    /**
     * gets the status of a move
     * @return the status of a move
     */
    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    /**
     * Gets the board based on the move made
     * @return the transition board
     */
    public Board getUpdatedBoard() {
        return this.updatedBoard;
    }
}
