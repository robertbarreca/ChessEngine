package com.tests.chess.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveTransition;
import com.chess.pgn.FenUtils;

public class TestFen {
     @Test
     public void testWriteFenStandardBoard() {
         final Board board = Board.createStandardBoard();
         final String fenString = FenUtils.writeFenFromBoard(board);
         assertEquals(fenString, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
     }
    
    @Test
    public void testWriteFEN2Moves() {
        final Board board = Board.createStandardBoard();

        // move 1
        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e2"),
                        BoardUtils.getCoordFromPos("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final String fenString = FenUtils.writeFenFromBoard(t1.getUpdatedBoard());
        assertEquals(fenString, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");

        // move 2
        final MoveTransition t2 = t1.getUpdatedBoard().getCurrPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("c7"),
                        BoardUtils.getCoordFromPos("c5")));
        assertTrue(t2.getMoveStatus().isDone());
        final String fenString2 = FenUtils.writeFenFromBoard(t2.getUpdatedBoard());
        assertEquals(fenString2, "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 1");

    }
}
