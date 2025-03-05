package com.chess.engine.board;

/*
 * Represents the transition from one board to another after a move is made
 */
public class MoveTransition {

    private final Board updatedBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveTransition(final Board updatedBoard, final Move move, final MoveStatus moveStatus) {
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

    /**
     * Get's the move transition's move
     * @return the move transition's move
     */
    public Move getTransitionMove() {
        return this.move;
    }
}
