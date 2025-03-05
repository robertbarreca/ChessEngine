package com.chess.engine.board;

public enum MoveStatus {
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }
    },
    ILLEGAL,
    LEAVES_PLAYER_IN_CHECK;


    public boolean isDone() {
        return false;
    }
}
