package com.tests.chess.engine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;

public class TestBoard {
    
    @Test
    public void testStartingBoard() {
        final Board board = Board.createStandardBoard();
        assertEquals(20, board.getCurrPlayer().getLegalMoves().size());
        assertEquals(20, board.getCurrPlayer().getOpponent().getLegalMoves().size());
        assertFalse(board.getCurrPlayer().isInCheck());
        assertFalse(board.getCurrPlayer().isInCheckmate());
        assertFalse(board.getCurrPlayer().hasCastled());
        assertEquals(board.getCurrPlayer(), board.getWhitePlayer());
        assertEquals(board.getCurrPlayer().getOpponent(), board.getBlackPlayer());
        assertFalse(board.getCurrPlayer().getOpponent().isInCheck());
        assertFalse(board.getCurrPlayer().getOpponent().isInCheckmate());
        assertFalse(board.getCurrPlayer().getOpponent().hasCastled());

        List<Move> allMoves = new ArrayList<>(board.getWhitePlayer().getLegalMoves());
        allMoves.addAll(board.getWhitePlayer().getLegalMoves());
        for(final Move move : allMoves) {
            assertFalse(move.isAttackingMove());
            assertFalse(move.isCastlingMove());
        }
    }

    @Test
    public void testInvalidBoard() {
        final Builder builder = new Builder();
        // no kings on board
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Pawn(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        //white to move
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        //build the board
        assertThrows(RuntimeException.class, () -> builder.build());

        // one king on board
        builder.setPiece(new King(Alliance.WHITE, 60, true, true));
        assertThrows(RuntimeException.class, () -> builder.build());
    }
    
    @Test
    public void testAlgebraicNotation() {
        assertEquals("a8", BoardUtils.getPosFromCoord(0));
        assertEquals("b8", BoardUtils.getPosFromCoord(1));
        assertEquals("c8", BoardUtils.getPosFromCoord(2));
        assertEquals("d8", BoardUtils.getPosFromCoord(3));
        assertEquals("e8", BoardUtils.getPosFromCoord(4));
        assertEquals("f8", BoardUtils.getPosFromCoord(5));
        assertEquals("g8", BoardUtils.getPosFromCoord(6));
        assertEquals("h8", BoardUtils.getPosFromCoord(7));
    }

    @Test
    public void testGetCoordFromPos() {
        assertEquals(0, BoardUtils.getCoordFromPos("a8"));
        assertEquals(1, BoardUtils.getCoordFromPos("b8"));
        assertEquals(2, BoardUtils.getCoordFromPos("c8"));
        assertEquals(3, BoardUtils.getCoordFromPos("d8"));
        assertEquals(4, BoardUtils.getCoordFromPos("e8"));
        assertEquals(5, BoardUtils.getCoordFromPos("f8"));
        assertEquals(6, BoardUtils.getCoordFromPos("g8"));
        assertEquals(7, BoardUtils.getCoordFromPos("h8"));
    }

    @Test
    public void testStartingBoardEquals() {
        final Board board1 = Board.createStandardBoard();
        final Board board2 = Board.createStandardBoard();
        // check two different instances of starting board
        assertEquals(board1, board2);
        assertEquals(board1, board1);
        assertEquals(board2, board2);

        // check different after one move
        final Move m1 = MoveFactory.createMove(board1, BoardUtils.getCoordFromPos("e2"),
                BoardUtils.getCoordFromPos("e4"));
        final MoveTransition t1_2 = board1.getCurrPlayer().makeMove(m1);
        
        assertNotEquals(t1_2.getUpdatedBoard(), board1);
        assertNotEquals(t1_2.getUpdatedBoard(), board2);
    }
}
