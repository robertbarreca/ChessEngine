package com.tests.chess.engine;

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
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        final Move castleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"),
                BoardUtils.getCoordFromPos("g1"));
        final MoveTransition t = board.getCurrPlayer().makeMove(castleMove);
        assertTrue(board.getWhitePlayer().getLegalMoves().contains(castleMove));
        assertTrue(t.getMoveStatus().isDone());
        assertTrue(t.getUpdatedBoard().getWhitePlayer().hasCastled());
    }
    // test white queen side
    @Test
    public void testWhiteQueenSideCastle() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        final Move castleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"),
                BoardUtils.getCoordFromPos("c1"));
        final MoveTransition t = board.getCurrPlayer().makeMove(castleMove);
        assertTrue(board.getWhitePlayer().getLegalMoves().contains(castleMove));
        assertTrue(t.getMoveStatus().isDone());
        assertTrue(t.getUpdatedBoard().getWhitePlayer().hasCastled());
    }
    // test black king side
    @Test
    public void testBlackKingSideCastle() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.BLACK);
        final Board board = builder.build();

        final Move castleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"),
                BoardUtils.getCoordFromPos("g8"));
        final MoveTransition t = board.getCurrPlayer().makeMove(castleMove);
        assertTrue(board.getBlackPlayer().getLegalMoves().contains(castleMove));
        assertTrue(t.getMoveStatus().isDone());
        assertTrue(t.getUpdatedBoard().getBlackPlayer().hasCastled());
    }
    // test black queen side
    @Test
    public void testBlackQueenSideCastle() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.BLACK);
        final Board board = builder.build();

        final Move castleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"),
                BoardUtils.getCoordFromPos("c8"));
        final MoveTransition t = board.getCurrPlayer().makeMove(castleMove);
        assertTrue(board.getBlackPlayer().getLegalMoves().contains(castleMove));
        assertTrue(t.getMoveStatus().isDone());
        assertTrue(t.getUpdatedBoard().getBlackPlayer().hasCastled());
    }
    // test white when king moved
    // test white when rook moved
    // test black when king moved
    // test black when rook moved
    // test castling into check
    // test castling thru check
    // test castling out of check
    // test castling when piece is blocking
}
