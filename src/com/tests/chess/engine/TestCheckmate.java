package com.tests.chess.engine;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.player.ai.MiniMax;
import com.chess.engine.player.ai.MoveStrategy;
import com.chess.engine.pieces.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCheckmate {

    @Test
    public void testFoolsMate() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrPlayer().makeMove(MoveFactory.createMove(board, BoardUtils.getCoordFromPos("f2"), BoardUtils.getCoordFromPos("f3")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("e7"),BoardUtils.getCoordFromPos("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t2.getUpdatedBoard(), BoardUtils.getCoordFromPos("g2"),BoardUtils.getCoordFromPos("g4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveStrategy strategy = new MiniMax(2);
        final Move aiMove = strategy.execute(t3.getUpdatedBoard());
        final Move bestMove = MoveFactory.createMove(t3.getUpdatedBoard(), BoardUtils.          getCoordFromPos("d8"),BoardUtils.getCoordFromPos("h4"));

        final MoveTransition t4 = t3.getUpdatedBoard().getCurrPlayer()
                .makeMove(bestMove);

        assertTrue(t4.getMoveStatus().isDone());
        assertTrue(t4.getUpdatedBoard().getCurrPlayer().isInCheckmate());
        assertEquals(bestMove, aiMove);
    }

    @Test
    public void testScholarsMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrPlayer().makeMove(MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e2"), BoardUtils.getCoordFromPos("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getUpdatedBoard().getCurrPlayer().makeMove(MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("a7"), BoardUtils.getCoordFromPos("a6")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t2.getUpdatedBoard(), BoardUtils.getCoordFromPos("d1"), BoardUtils.getCoordFromPos("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t3.getUpdatedBoard(), BoardUtils.getCoordFromPos("a6"), BoardUtils.getCoordFromPos("a5")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t4.getUpdatedBoard(), BoardUtils.getCoordFromPos("f1"),BoardUtils.getCoordFromPos("c4")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t5.getUpdatedBoard(), BoardUtils.getCoordFromPos("a5"), BoardUtils.getCoordFromPos("a4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t6.getUpdatedBoard(), BoardUtils.getCoordFromPos("f3"), BoardUtils.getCoordFromPos("f7")));

        assertTrue(t7.getMoveStatus().isDone());
        assertTrue(t7.getUpdatedBoard().getCurrPlayer().isInCheckmate());

    }

    @Test
    public void testLegalsMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordFromPos
                ("e2"), BoardUtils.getCoordFromPos("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getUpdatedBoard()
                .getCurrPlayer()
                .makeMove(MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("e7"), BoardUtils.getCoordFromPos("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t2.getUpdatedBoard(), BoardUtils.getCoordFromPos("f1"), BoardUtils.getCoordFromPos("c4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t3.getUpdatedBoard(), BoardUtils.getCoordFromPos("d7"), BoardUtils.getCoordFromPos("d6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getUpdatedBoard()
                .getCurrPlayer()
                .makeMove(MoveFactory.createMove(t4.getUpdatedBoard(), BoardUtils.getCoordFromPos("g1"), BoardUtils.getCoordFromPos("f3")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t5.getUpdatedBoard(), BoardUtils.getCoordFromPos("c8"), BoardUtils.getCoordFromPos("g4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t6.getUpdatedBoard(), BoardUtils.getCoordFromPos("b1"), BoardUtils.getCoordFromPos("c3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t7.getUpdatedBoard(), BoardUtils.getCoordFromPos("g7"), BoardUtils.getCoordFromPos("g6")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t8.getUpdatedBoard(), BoardUtils.getCoordFromPos("f3"), BoardUtils.getCoordFromPos("e5")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t9.getUpdatedBoard(), BoardUtils.getCoordFromPos("g4"), BoardUtils.getCoordFromPos("d1")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t10.getUpdatedBoard(), BoardUtils.getCoordFromPos("c4"), BoardUtils.getCoordFromPos("f7")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t11.getUpdatedBoard(), BoardUtils.getCoordFromPos("e8"), BoardUtils.getCoordFromPos("e7")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveTransition t13 = t12.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t12.getUpdatedBoard(), BoardUtils.getCoordFromPos("c3"), BoardUtils.getCoordFromPos("d5")));

        assertTrue(t13.getMoveStatus().isDone());
        assertTrue(t13.getUpdatedBoard().getCurrPlayer().isInCheckmate());

    }

    @Test
    public void testSevenMoveMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e2"),
                        BoardUtils.getCoordFromPos("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("e7"), BoardUtils.getCoordFromPos("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t2.getUpdatedBoard(), BoardUtils.getCoordFromPos("d2"), BoardUtils.getCoordFromPos("d4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t3.getUpdatedBoard(), BoardUtils.getCoordFromPos("d7"), BoardUtils.getCoordFromPos("d6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t4.getUpdatedBoard(), BoardUtils.getCoordFromPos("d4"), BoardUtils.getCoordFromPos("e5")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t5.getUpdatedBoard(), BoardUtils.getCoordFromPos("d8"), BoardUtils.getCoordFromPos("e7")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t6.getUpdatedBoard(), BoardUtils.getCoordFromPos("e5"), BoardUtils.getCoordFromPos("d6")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getUpdatedBoard()
                .getCurrPlayer().makeMove(MoveFactory.createMove(t7.getUpdatedBoard(), BoardUtils.getCoordFromPos("e7"), BoardUtils.getCoordFromPos("e4")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t8.getUpdatedBoard(), BoardUtils.getCoordFromPos("f1"), BoardUtils.getCoordFromPos("e2")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t9.getUpdatedBoard(), BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g2")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t10.getUpdatedBoard(), BoardUtils.getCoordFromPos("d6"), BoardUtils.getCoordFromPos("c7")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t11.getUpdatedBoard(), BoardUtils.getCoordFromPos("g2"), BoardUtils.getCoordFromPos("h1")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveTransition t13 = t12.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t12.getUpdatedBoard(), BoardUtils.getCoordFromPos("d1"),BoardUtils.getCoordFromPos("d8")));

        assertTrue(t13.getMoveStatus().isDone());
        assertTrue(t13.getUpdatedBoard().getCurrPlayer().isInCheckmate());

    }

    @Test
    public void testGrecoGame() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordFromPos
                ("d2"), BoardUtils.getCoordFromPos("d4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("g8"), BoardUtils.getCoordFromPos("f6")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t2.getUpdatedBoard(), BoardUtils.getCoordFromPos("b1"), BoardUtils.getCoordFromPos("d2")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t3.getUpdatedBoard(), BoardUtils.getCoordFromPos("e7"), BoardUtils.getCoordFromPos("e5")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t4.getUpdatedBoard(), BoardUtils.getCoordFromPos("d4"), BoardUtils.getCoordFromPos("e5")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t5.getUpdatedBoard(), BoardUtils.getCoordFromPos("f6"), BoardUtils.getCoordFromPos("g4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t6.getUpdatedBoard(), BoardUtils.getCoordFromPos("h2"), BoardUtils.getCoordFromPos("h3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t7.getUpdatedBoard(), BoardUtils.getCoordFromPos("g4"), BoardUtils.getCoordFromPos("e3")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t8.getUpdatedBoard(), BoardUtils.getCoordFromPos("f2"),BoardUtils.getCoordFromPos("e3")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t9.getUpdatedBoard(), BoardUtils.getCoordFromPos("d8"), BoardUtils.getCoordFromPos("h4")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t10.getUpdatedBoard(), BoardUtils.getCoordFromPos("g2"), BoardUtils.getCoordFromPos("g3")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t11.getUpdatedBoard(), BoardUtils.getCoordFromPos("h4"), BoardUtils.getCoordFromPos("g3")));

        assertTrue(t12.getMoveStatus().isDone());
        assertTrue(t12.getUpdatedBoard().getCurrPlayer().isInCheckmate());

    }

    @Test
    public void testOlympicGame() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e2"),
                        BoardUtils.getCoordFromPos("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("c7"), BoardUtils.getCoordFromPos("c6")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t2.getUpdatedBoard(), BoardUtils.getCoordFromPos("g1"), BoardUtils.getCoordFromPos("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t3.getUpdatedBoard(), BoardUtils.getCoordFromPos("d7"), BoardUtils.getCoordFromPos("d5")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t4.getUpdatedBoard(), BoardUtils.getCoordFromPos("b1"), BoardUtils.getCoordFromPos("c3")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t5.getUpdatedBoard(), BoardUtils.getCoordFromPos("d5"), BoardUtils.getCoordFromPos("e4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t6.getUpdatedBoard(), BoardUtils.getCoordFromPos("c3"),BoardUtils.getCoordFromPos("e4")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t7.getUpdatedBoard(), BoardUtils.getCoordFromPos("b8"), BoardUtils.getCoordFromPos("d7")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t8.getUpdatedBoard(), BoardUtils.getCoordFromPos("d1"), BoardUtils.getCoordFromPos("e2")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t9.getUpdatedBoard(), BoardUtils.getCoordFromPos("g8"), BoardUtils.getCoordFromPos("f6")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t10.getUpdatedBoard(), BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d6")));

        assertTrue(t11.getMoveStatus().isDone());
        assertTrue(t11.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }

    @Test
    public void testAnotherGame() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils
                        .getCoordFromPos("e2"), BoardUtils.getCoordFromPos("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("e7"), BoardUtils.getCoordFromPos("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t2.getUpdatedBoard(), BoardUtils.getCoordFromPos("g1"), BoardUtils.getCoordFromPos("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t3.getUpdatedBoard(), BoardUtils.getCoordFromPos("b8"), BoardUtils.getCoordFromPos("c6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t4.getUpdatedBoard(), BoardUtils.getCoordFromPos("f1"), BoardUtils.getCoordFromPos("c4")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t5.getUpdatedBoard(), BoardUtils.getCoordFromPos("c6"), BoardUtils.getCoordFromPos("d4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t6.getUpdatedBoard(), BoardUtils.getCoordFromPos("f3"), BoardUtils.getCoordFromPos("e5")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t7.getUpdatedBoard(), BoardUtils.getCoordFromPos("d8"), BoardUtils.getCoordFromPos("g5")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t8.getUpdatedBoard(), BoardUtils.getCoordFromPos("e5"), BoardUtils.getCoordFromPos("f7")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t9.getUpdatedBoard(), BoardUtils.getCoordFromPos("g5"), BoardUtils.getCoordFromPos("g2")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t10.getUpdatedBoard(), BoardUtils.getCoordFromPos("h1"), BoardUtils.getCoordFromPos("f1")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t11.getUpdatedBoard(), BoardUtils.getCoordFromPos("g2"), BoardUtils.getCoordFromPos("e4")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveTransition t13 = t12.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t12.getUpdatedBoard(), BoardUtils.getCoordFromPos("c4"), BoardUtils.getCoordFromPos("e2")));

        assertTrue(t13.getMoveStatus().isDone());

        final MoveTransition t14 = t13.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t13.getUpdatedBoard(), BoardUtils.getCoordFromPos("d4"), BoardUtils.getCoordFromPos("f3")));

        assertTrue(t14.getMoveStatus().isDone());
        assertTrue(t14.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }

    @Test
    public void testSmotheredMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils
                        .getCoordFromPos("e2"),BoardUtils.getCoordFromPos("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("e7"), BoardUtils.getCoordFromPos("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t2.getUpdatedBoard(), BoardUtils.getCoordFromPos("g1"), BoardUtils.getCoordFromPos("e2")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t3.getUpdatedBoard(), BoardUtils.getCoordFromPos("b8"), BoardUtils.getCoordFromPos("c6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t4.getUpdatedBoard(), BoardUtils.getCoordFromPos("b1"), BoardUtils.getCoordFromPos("c3")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t5.getUpdatedBoard(), BoardUtils.getCoordFromPos("c6"), BoardUtils.getCoordFromPos("d4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t6.getUpdatedBoard(), BoardUtils.getCoordFromPos("g2"), BoardUtils.getCoordFromPos("g3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t7.getUpdatedBoard(), BoardUtils.getCoordFromPos("d4"), BoardUtils.getCoordFromPos("f3")));

        assertTrue(t8.getMoveStatus().isDone());
        assertTrue(t8.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }

    @Test
    public void testHippopotamusMate() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils
                        .getCoordFromPos("e2"), BoardUtils.getCoordFromPos("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("e7"), BoardUtils.getCoordFromPos("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t2.getUpdatedBoard(), BoardUtils.getCoordFromPos("g1"),BoardUtils.getCoordFromPos("e2")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t3.getUpdatedBoard(), BoardUtils.getCoordFromPos("d8"), BoardUtils.getCoordFromPos("h4")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t4.getUpdatedBoard(), BoardUtils.getCoordFromPos("b1"), BoardUtils.getCoordFromPos("c3")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t5.getUpdatedBoard(), BoardUtils.getCoordFromPos("b8"), BoardUtils.getCoordFromPos("c6")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t6.getUpdatedBoard(), BoardUtils.getCoordFromPos("g2"), BoardUtils.getCoordFromPos("g3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t7.getUpdatedBoard(), BoardUtils.getCoordFromPos("h4"), BoardUtils.getCoordFromPos("g5")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t8.getUpdatedBoard(), BoardUtils.getCoordFromPos("d2"), BoardUtils.getCoordFromPos("d4")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t9.getUpdatedBoard(), BoardUtils.getCoordFromPos("c6"), BoardUtils.getCoordFromPos("d4")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t10.getUpdatedBoard(), BoardUtils.getCoordFromPos("c1"),BoardUtils.getCoordFromPos("g5")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t11.getUpdatedBoard(), BoardUtils.getCoordFromPos("d4"), BoardUtils.getCoordFromPos("f3")));

        assertTrue(t12.getMoveStatus().isDone());
        assertTrue(t12.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }

    @Test
    public void testBlackburneShillingMate() {

        final Board board = Board.createStandardBoard();

        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils
                        .getCoordFromPos("e2"), BoardUtils.getCoordFromPos("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("e7"), BoardUtils.getCoordFromPos("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t2.getUpdatedBoard(), BoardUtils.getCoordFromPos("g1"), BoardUtils.getCoordFromPos("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t3.getUpdatedBoard(), BoardUtils.getCoordFromPos("b8"), BoardUtils.getCoordFromPos("c6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t4.getUpdatedBoard(), BoardUtils.getCoordFromPos("f1"), BoardUtils.getCoordFromPos("c4")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t5.getUpdatedBoard(), BoardUtils.getCoordFromPos("c6"), BoardUtils.getCoordFromPos("d4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t6.getUpdatedBoard(), BoardUtils.getCoordFromPos("f3"), BoardUtils.getCoordFromPos("e5")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t7.getUpdatedBoard(), BoardUtils.getCoordFromPos("d8"), BoardUtils.getCoordFromPos("g5")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t8.getUpdatedBoard(), BoardUtils.getCoordFromPos("e5"), BoardUtils.getCoordFromPos("f7")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t9.getUpdatedBoard(), BoardUtils.getCoordFromPos("g5"), BoardUtils.getCoordFromPos("g2")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t10.getUpdatedBoard(), BoardUtils.getCoordFromPos("h1"), BoardUtils.getCoordFromPos("f1")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t11.getUpdatedBoard(), BoardUtils.getCoordFromPos("g2"), BoardUtils.getCoordFromPos("e4")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveTransition t13 = t12.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t12.getUpdatedBoard(), BoardUtils.getCoordFromPos("c4"), BoardUtils.getCoordFromPos("e2")));

        assertTrue(t13.getMoveStatus().isDone());

        final MoveTransition t14 = t13.getUpdatedBoard().getCurrPlayer()
                .makeMove(MoveFactory.createMove(t13.getUpdatedBoard(), BoardUtils.getCoordFromPos("d4"), BoardUtils.getCoordFromPos("f3")));

        assertTrue(t14.getMoveStatus().isDone());
        assertTrue(t14.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }

    @Test
    public void testAnastasiaMate() {
        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 5));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new King(Alliance.BLACK, 15, false, true));
        // White Layout
        builder.setPiece(new Knight(Alliance.WHITE, 12));
        builder.setPiece(new Rook(Alliance.WHITE, 27));
        builder.setPiece(new Pawn(Alliance.WHITE, 41));
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new King(Alliance.WHITE, 62, false, true));
        // Set the current player
        builder.setCurrPlayerAlliance(Alliance.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils
                        .getCoordFromPos("d5"), BoardUtils.getCoordFromPos("h5")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }

    @Test
    public void testTwoBishopMate() {

        final Builder builder = new Builder();

        builder.setPiece(new King(Alliance.BLACK, 7, false, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
        builder.setPiece(new Pawn(Alliance.BLACK, 17));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 40));
        builder.setPiece(new Bishop(Alliance.WHITE, 48));
        builder.setPiece(new King(Alliance.WHITE, 53, false, true));
        // Set the current player
        builder.setCurrPlayerAlliance(Alliance.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils
                        .getCoordFromPos("a3"), BoardUtils.getCoordFromPos("b2")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }

    @Test
    public void testQueenRookMate() {

        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 5, false, true));
        // White Layout
        builder.setPiece(new Rook(Alliance.WHITE, 9));
        builder.setPiece(new Queen(Alliance.WHITE, 16));
        builder.setPiece(new King(Alliance.WHITE, 59, false, true));
        // Set the current player
        builder.setCurrPlayerAlliance(Alliance.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils
                        .getCoordFromPos("a6"),BoardUtils.getCoordFromPos("a8")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }

    @Test
    public void testQueenKnightMate() {

        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, true));
        // White Layout
        builder.setPiece(new Queen(Alliance.WHITE, 15));
        builder.setPiece(new Knight(Alliance.WHITE, 29));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new King(Alliance.WHITE, 60, false, true));
        // Set the current player
        builder.setCurrPlayerAlliance(Alliance.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils
                        .getCoordFromPos("h7"), BoardUtils.getCoordFromPos("e7")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }

    @Test
    public void testBackRankMate() {

        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, true));
        builder.setPiece(new Rook(Alliance.BLACK, 18));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new King(Alliance.WHITE, 62, false, true));
        // Set the current player
        builder.setCurrPlayerAlliance(Alliance.BLACK);

        final Board board = builder.build();

        final MoveTransition t1 = board.getCurrPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils
                        .getCoordFromPos("c6"), BoardUtils.getCoordFromPos("c1")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getUpdatedBoard().getCurrPlayer().isInCheckmate());
    }
}