package com.tests.chess.engine.board;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

public class TestBoard {
    
    @Test
    public void testStartingBoard() {
        final Board board = Board.createStandardBoard();
        final Collection<Piece> whitePieces = board.getWhitePieces();
        final Collection<Piece> blackPieces = board.getBlackPieces();
        assertEquals(16, whitePieces.size());
        assertEquals(16, blackPieces.size());
        final Collection<Move> whiteLegals = board.calcMoves(whitePieces);
        final Collection<Move> blackLegals = board.calcMoves(blackPieces);
        assertEquals(20, whiteLegals.size());
        assertEquals(20, blackLegals.size());
    }
    
}
