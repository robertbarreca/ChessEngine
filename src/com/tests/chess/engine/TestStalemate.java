package com.tests.chess.engine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.player.MoveTransition;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Pawn;

public class TestStalemate {
    @Test
    public void testAnandKramnikStaleMate() {

        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 21));
        builder.setPiece(new King(Alliance.BLACK, 36, false, true));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 29));
        builder.setPiece(new King(Alliance.WHITE, 31, false, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 39));
        // Set the current player
        builder.setCurrPlayerAlliance(Alliance.BLACK);
        final Board board = builder.build();

        assertFalse(board.getCurrPlayer().isInStalemate());

        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils
                        .getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f5")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getUpdatedBoard().getCurrPlayer().isInStalemate());
        assertFalse(t1.getUpdatedBoard().getCurrPlayer().isInCheck());
        assertFalse(t1.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }

    @Test
    public void testAnonymousStaleMate() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 2, false, true));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 10));
        builder.setPiece(new King(Alliance.WHITE, 26, false, true));
        // Set the current player
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        assertFalse(board.getCurrPlayer().isInStalemate());

        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c5"),
                        BoardUtils.getCoordFromPos("c6")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getUpdatedBoard().getCurrPlayer().isInStalemate());
        assertFalse(t1.getUpdatedBoard().getCurrPlayer().isInCheck());
        assertFalse(t1.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }

    @Test
    public void testAnonymousStaleMate2() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 0, false, true));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 16));
        builder.setPiece(new King(Alliance.WHITE, 17, false, true));
        builder.setPiece(new Bishop(Alliance.WHITE, 19));
        // Set the current player
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        assertFalse(board.getCurrPlayer().isInStalemate());

        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils
                        .getCoordFromPos("a6"), BoardUtils.getCoordFromPos("a7")));
                
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getUpdatedBoard().getCurrPlayer().isInStalemate());
        assertFalse(t1.getUpdatedBoard().getCurrPlayer().isInCheck());
        assertFalse(t1.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }
}