package com.tests.chess;

import org.junit.Test;
import static org.junit.Assert.*;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.MoveTransition;

public class TestCastling {
    // Test white king side
    @Test
    public void testWhiteKingSideCastle() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        final Move castleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("d1"),
                BoardUtils.getCoordFromPos("b1"));
        final MoveTransition t = board.getCurrPlayer().makeMove(castleMove);
        assertTrue(t.getMoveStatus().isDone());
    }
    // test white queen side
    // test black king side
    // test black queen side
    // test white when king moved
    // test white when rook moved
    // test black when king moved
    // test black when rook moved
    // test castling into check
    // test castling thru check
    // test castling out of check
    // test castling when piece is blocking
}
