package com.tests.chess.engine;


import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveStatus;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;

public class TestPieces {

    @Test
    public void testStartingPawn() {
        final Board.Builder builder = new Board.Builder();
        Pawn pawn = new Pawn(Alliance.WHITE, 48);
        builder.setPiece(pawn);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        Board board = builder.build();


        final Collection<Move> pawnLegalMoves = pawn.calcLegalMoves(board);
        assertEquals(2, pawnLegalMoves.size());
        assertTrue(pawnLegalMoves.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordFromPos("a2"), BoardUtils.getCoordFromPos("a3"))));
        assertTrue(pawnLegalMoves.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordFromPos("a2"), BoardUtils.getCoordFromPos("a4"))));
    }

    @Test
    public void testStartingPawnAttacking() {
        // can attack two pieces
        final Board.Builder builder = new Board.Builder();
        Pawn attackingPawn = new Pawn(Alliance.WHITE, 50);
        builder.setPiece(attackingPawn);
        builder.setPiece(new Pawn(Alliance.BLACK, 41));
        builder.setPiece(new Pawn(Alliance.BLACK, 43));
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        Board board = builder.build();
        Collection<Move> pawnLegalMoves = attackingPawn.calcLegalMoves(board);
        assertEquals(4, pawnLegalMoves.size());
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c2"), BoardUtils.getCoordFromPos("c3"))));
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c2"), BoardUtils.getCoordFromPos("c4"))));
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c2"), BoardUtils.getCoordFromPos("b3"))));
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c2"), BoardUtils.getCoordFromPos("d3"))));

        // can attack only left piece
        builder.setPiece(new Pawn(Alliance.WHITE, 43));
        board = builder.build();
        pawnLegalMoves = attackingPawn.calcLegalMoves(board);
        assertEquals(3, pawnLegalMoves.size());
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c2"), BoardUtils.getCoordFromPos("c3"))));
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c2"), BoardUtils.getCoordFromPos("c4"))));
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c2"), BoardUtils.getCoordFromPos("b3"))));

        // can attack only right piece
        builder.setPiece(new Pawn(Alliance.WHITE, 41));
        builder.setPiece(new Pawn(Alliance.BLACK, 43));
        board = builder.build();
        pawnLegalMoves = attackingPawn.calcLegalMoves(board);
        assertEquals(3, pawnLegalMoves.size());
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c2"), BoardUtils.getCoordFromPos("c3"))));
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c2"), BoardUtils.getCoordFromPos("c4"))));
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c2"), BoardUtils.getCoordFromPos("d3"))));
    }

    @Test
    public void testMovedPawnAttacking() {
        // can attack two pieces
        final Board.Builder builder = new Board.Builder();
        Pawn attackingPawn = new Pawn(Alliance.WHITE, 42);
        builder.setPiece(attackingPawn);
        builder.setPiece(new Pawn(Alliance.BLACK, 33));
        builder.setPiece(new Pawn(Alliance.BLACK, 35));
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        Board board = builder.build();
        Collection<Move> pawnLegalMoves = attackingPawn.calcLegalMoves(board);
        assertEquals(3, pawnLegalMoves.size());
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c3"), BoardUtils.getCoordFromPos("c4"))));
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c3"), BoardUtils.getCoordFromPos("b4"))));
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c3"), BoardUtils.getCoordFromPos("d4"))));

        // can attack only left piece
        builder.setPiece(new Pawn(Alliance.WHITE, 35));
        board = builder.build();
        pawnLegalMoves = attackingPawn.calcLegalMoves(board);
        assertEquals(2, pawnLegalMoves.size());
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c3"), BoardUtils.getCoordFromPos("c4"))));
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c3"), BoardUtils.getCoordFromPos("b4"))));

        // can attack only right piece
        builder.setPiece(new Pawn(Alliance.WHITE, 33));
        builder.setPiece(new Pawn(Alliance.BLACK, 35));
        board = builder.build();
        pawnLegalMoves = attackingPawn.calcLegalMoves(board);
        assertEquals(2, pawnLegalMoves.size());
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c3"), BoardUtils.getCoordFromPos("c4"))));
        assertTrue(pawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c3"), BoardUtils.getCoordFromPos("d4"))));
    }

    @Test
    public void testAttackingPawnWrapping() {
        // white pawn column H and black pawn column A
        final Board.Builder builder = new Board.Builder();
        Pawn whitePawn = new Pawn(Alliance.WHITE, 47);
        Pawn blackPawn = new Pawn(Alliance.BLACK, 40);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setPiece(whitePawn);
        builder.setPiece(blackPawn);
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        Board board = builder.build();
        Collection<Move> whitePawnLegalMoves = whitePawn.calcLegalMoves(board);
        Collection<Move> blackPawnLegalMoves = blackPawn.calcLegalMoves(board);
        assertEquals(1, whitePawnLegalMoves.size());
        assertTrue(whitePawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("h3"), BoardUtils.getCoordFromPos("h4"))));
        assertEquals(1, blackPawnLegalMoves.size());
        assertTrue(blackPawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("a3"), BoardUtils.getCoordFromPos("a2"))));
        
        // white pawn column A and black pawn column H
        whitePawn = new Pawn(Alliance.WHITE, 32);
        blackPawn = new Pawn(Alliance.BLACK, 23);
        builder.setPiece(whitePawn);
        builder.setPiece(blackPawn);
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        board = builder.build();
        whitePawnLegalMoves = whitePawn.calcLegalMoves(board);
        blackPawnLegalMoves = blackPawn.calcLegalMoves(board);
        assertEquals(1, whitePawnLegalMoves.size());
        assertTrue(whitePawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("a4"), BoardUtils.getCoordFromPos("a5"))));
        assertEquals(1, blackPawnLegalMoves.size());
        assertTrue(blackPawnLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("h6"), BoardUtils.getCoordFromPos("h5"))));
    }

    @Test
    public void testPawnPieceBlocking() {
            // starting position blocking one tile
            final Board.Builder builder = new Board.Builder();
            Pawn startingPawn = new Pawn(Alliance.WHITE, 50);
            builder.setPiece(startingPawn);
            builder.setPiece(new Pawn(Alliance.WHITE, 34));
            builder.setPiece(new King(Alliance.WHITE, 59));
            builder.setPiece(new King(Alliance.BLACK, 3));
            builder.setCurrPlayerAlliance(Alliance.WHITE);
            Board board = builder.build();
            Collection<Move> pawnLegalMoves = startingPawn.calcLegalMoves(board);
            assertEquals(1, pawnLegalMoves.size());
            assertTrue(pawnLegalMoves.contains(
                            MoveFactory.createMove(board, BoardUtils.getCoordFromPos("c2"),
                                            BoardUtils.getCoordFromPos("c3"))));

            // starting position blocking two tiles
            builder.setPiece(new Pawn(Alliance.BLACK, 42));
            board = builder.build();
            assertEquals(0, startingPawn.calcLegalMoves(board).size());

            // non-starting position blocking one tile
            Pawn movedPawn = new Pawn(Alliance.BLACK, 22);
            builder.setPiece(movedPawn);
            builder.setPiece(new Pawn(Alliance.WHITE, 30));
            board = builder.build();
            assertEquals(0, movedPawn.calcLegalMoves(board).size());
    }
    
    @Test
    public void testWhiteEnPassant() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 28));
        builder.setPiece(new King(Alliance.WHITE, 60));
        // Set the current player
        builder.setCurrPlayerAlliance(Alliance.BLACK);
        final Board board = builder.build();
        // pawn jump
        final Move m1 = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("d7"),
                        BoardUtils.getCoordFromPos("d5"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(m1);
        assertTrue(t1.getMoveStatus().isDone());
        // en passant capture
        final Move m2 = Move.MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("e5"),
                        BoardUtils.getCoordFromPos("d6"));
        final MoveTransition t2 = t1.getUpdatedBoard().getCurrPlayer().makeMove(m2);
        assertTrue(t2.getMoveStatus().isDone());
    }
    
    @Test
    public void testBlackEnPassant() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Pawn(Alliance.BLACK, 35));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60));
        // Set the current player
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();
        // pawn jump
        final Move m1 = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e2"),
                        BoardUtils.getCoordFromPos("e4"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(m1);
        assertTrue(t1.getMoveStatus().isDone());
        // en passant capture
        final Move m2 = Move.MoveFactory.createMove(t1.getUpdatedBoard(), BoardUtils.getCoordFromPos("d4"), BoardUtils.getCoordFromPos("e3"));
        final MoveTransition t2 = t1.getUpdatedBoard().getCurrPlayer().makeMove(m2);
        assertTrue(t2.getMoveStatus().isDone());
    }

    @Test
    public void testWhitePawnPromotion() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 22));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 15));
        builder.setPiece(new King(Alliance.WHITE, 52));
        // Set the current player
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordFromPos(
                        "h7"), BoardUtils.getCoordFromPos("h8"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(m1);
        assertTrue(t1.getMoveStatus().isDone());
        // check pawn is now a queen
        assertTrue(t1.getUpdatedBoard().getWhitePieces().contains(new Queen(Alliance.WHITE, 7)));
    }
    
    @Test
    public void testBlackPawnPromotion() {
            final Board.Builder builder = new Board.Builder();
            // Black Layout
            builder.setPiece(new King(Alliance.BLACK, 22));
            builder.setPiece(new Pawn(Alliance.BLACK, 48));
            // White Layout
            builder.setPiece(new King(Alliance.WHITE, 52));
            // Set the current player
            builder.setCurrPlayerAlliance(Alliance.BLACK);
            final Board board = builder.build();
            final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordFromPos(
                            "a2"), BoardUtils.getCoordFromPos("a1"));
            final MoveTransition t1 = board.getCurrPlayer().makeMove(m1);
            assertTrue(t1.getMoveStatus().isDone());
            // check pawn is now a queen
            assertTrue(t1.getUpdatedBoard().getBlackPieces().contains(new Queen(Alliance.BLACK, 56)));
    }
    
    @Test
    public void testMiddleKnightEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        Knight knight = new Knight(Alliance.WHITE, 36);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setPiece(knight);
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();
        Collection<Move> knightLegalMoves = knight.calcLegalMoves(board);
        assertEquals(8, knightLegalMoves.size());
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d6"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f6"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("c5"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g5"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("c3"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g3"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d2"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f2"))));
    }

    @Test
    public void testKnightWrappingEmptyBoard() {
        final Board.Builder builder = new Board.Builder();

        Knight knightA = new Knight(Alliance.WHITE, 24);
        Knight knightB = new Knight(Alliance.WHITE, 25);
        Knight knightG = new Knight(Alliance.WHITE, 30);
        Knight knightH = new Knight(Alliance.WHITE, 31);

        builder.setPiece(knightA);
        builder.setPiece(knightB);
        builder.setPiece(knightG);
        builder.setPiece(knightH);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));

        builder.setCurrPlayerAlliance(Alliance.WHITE);
        Board board = builder.build();

        final Collection<Move> knightALegalMoves = knightA.calcLegalMoves(board);
        final Collection<Move> knightBLegalMoves = knightB.calcLegalMoves(board);
        final Collection<Move> knightGLegalMoves = knightG.calcLegalMoves(board);
        final Collection<Move> knightHLegalMoves = knightH.calcLegalMoves(board);


        // test column A exception
        assertEquals(4, knightALegalMoves.size());
        assertTrue(knightALegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("a5"), BoardUtils.getCoordFromPos("b7"))));
        assertTrue(knightALegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("a5"), BoardUtils.getCoordFromPos("c6"))));
        assertTrue(knightALegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("a5"), BoardUtils.getCoordFromPos("c4"))));
        assertTrue(knightALegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("a5"), BoardUtils.getCoordFromPos("b3"))));
        
        // test column B exception
        assertEquals(6, knightBLegalMoves.size());
        assertTrue(knightBLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("b5"), BoardUtils.getCoordFromPos("a7"))));
        assertTrue(knightBLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("b5"), BoardUtils.getCoordFromPos("c7"))));
        assertTrue(knightBLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("b5"), BoardUtils.getCoordFromPos("d6"))));
        assertTrue(knightBLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("b5"), BoardUtils.getCoordFromPos("d4"))));
        assertTrue(knightBLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("b5"), BoardUtils.getCoordFromPos("a3"))));
        assertTrue(knightBLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("b5"), BoardUtils.getCoordFromPos("c3"))));
                
        // test column G exception
        assertEquals(6, knightGLegalMoves.size());
        assertTrue(knightGLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("g5"), BoardUtils.getCoordFromPos("f7"))));
        assertTrue(knightGLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("g5"), BoardUtils.getCoordFromPos("h7"))));
        assertTrue(knightGLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("g5"), BoardUtils.getCoordFromPos("e6"))));
        assertTrue(knightGLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("g5"), BoardUtils.getCoordFromPos("e4"))));
        assertTrue(knightGLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("g5"), BoardUtils.getCoordFromPos("f3"))));
        assertTrue(knightGLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("g5"), BoardUtils.getCoordFromPos("h3"))));
                
        // test column H exception
        assertEquals(4, knightHLegalMoves.size());
        assertTrue(knightHLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("h5"), BoardUtils.getCoordFromPos("g7"))));
        assertTrue(knightHLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("h5"), BoardUtils.getCoordFromPos("f6"))));
        assertTrue(knightHLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("h5"), BoardUtils.getCoordFromPos("f4"))));
        assertTrue(knightHLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("h5"), BoardUtils.getCoordFromPos("g3"))));
    }

    @Test
    public void testKnightPieceBlocking() {
        final Board.Builder builder = new Board.Builder();
        Knight knight = new Knight(Alliance.WHITE, 36);
        builder.setPiece(knight);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        // blocking piece
        builder.setPiece(new Pawn(Alliance.WHITE, 21));
        // attacking piece
        builder.setPiece(new Pawn(Alliance.BLACK, 19));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> knightLegalMoves = knight.calcLegalMoves(board);
        assertEquals(7, knightLegalMoves.size());
        // check all legal moves
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d6"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("c5"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g5"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("c3"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g3"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d2"))));
        assertTrue(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f2"))));
        
        
        // check blocking move not included
        assertFalse(knightLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f6"))));
    }

    @Test
    public void testMiddleBishopEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        Bishop bishop = new Bishop(Alliance.WHITE, 36);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setPiece(bishop);
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        Board board = builder.build();
        final Collection<Move> bishopLegalMoves = bishop.calcLegalMoves(board);
        assertEquals(13, bishopLegalMoves.size());
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d5"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("c6"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("b7"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("a8"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f3"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g2"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("h1"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("b1"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("c2"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d3"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f5"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g6"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("h7"))));
        
    }

    @Test
    public void testBishopPieceBlocking() {
        final Board.Builder builder = new Board.Builder();
        Bishop bishop = new Bishop(Alliance.WHITE, 36);
        builder.setPiece(bishop);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        // blocking piece
        builder.setPiece(new Pawn(Alliance.WHITE, 18));
        // attacking piece
        builder.setPiece(new Pawn(Alliance.BLACK, 22));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        Board board = builder.build();
        final Collection<Move> bishopLegalMoves = bishop.calcLegalMoves(board);
        assertEquals(9, bishopLegalMoves.size());
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d5"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f3"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g2"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("h1"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("b1"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("c2"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d3"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f5"))));
        assertTrue(bishopLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g6"))));
    }

    @Test
    public void testMiddleRookEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        Rook rook = new Rook(Alliance.WHITE, 36);
        builder.setPiece(rook);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        Board board = builder.build();
        final Collection<Move> rookLegalMoves = rook.calcLegalMoves(board);
        assertEquals(14, rookLegalMoves.size());
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e1"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e2"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e3"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e5"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e6"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e7"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e8"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("a4"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("b4"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("c4"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d4"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f4"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g4"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("h4"))));
                
    }

    @Test
    public void testRookPieceBlocking() {
        final Board.Builder builder = new Board.Builder();
        Rook rook = new Rook(Alliance.WHITE, 36);
        builder.setPiece(rook);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        // attacking piece
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        // blocking piece
        builder.setPiece(new Pawn(Alliance.WHITE, 33));

        builder.setCurrPlayerAlliance(Alliance.WHITE);
        Board board = builder.build();
        final Collection<Move> rookLegalMoves = rook.calcLegalMoves(board);
        assertEquals(11, rookLegalMoves.size());
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e1"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e2"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e3"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e5"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e6"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e7"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("c4"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d4"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f4"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g4"))));
        assertTrue(rookLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("h4"))));
    }

    @Test
    public void testMiddleQueenEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        Queen queen = new Queen(Alliance.WHITE, 36);
        builder.setPiece(queen);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        Board board = builder.build();
        final Collection<Move> queenLegalMoves = queen.calcLegalMoves(board);
        assertEquals(27, queenLegalMoves.size());
        // check up down left right
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e1"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e2"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e3"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e5"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e6"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e7"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e8"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("a4"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("b4"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("c4"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d4"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f4"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g4"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("h4"))));
        
        // check diagonals
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d5"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("c6"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("b7"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("a8"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f3"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g2"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("h1"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("b1"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("c2"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d3"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f5"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("g6"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("h7"))));
    }

    @Test
    public void testQueenPieceBlocking() {
        final Board.Builder builder = new Board.Builder();
        Queen queen = new Queen(Alliance.WHITE, 36);
        builder.setPiece(queen);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        // blocking pieces
        builder.setPiece(new Pawn(Alliance.WHITE, 27));
        builder.setPiece(new Pawn(Alliance.WHITE, 28));
        builder.setPiece(new Pawn(Alliance.WHITE, 29));
        builder.setPiece(new Pawn(Alliance.WHITE, 43));
        builder.setPiece(new Pawn(Alliance.WHITE, 44));
        builder.setPiece(new Pawn(Alliance.WHITE, 45));
        // attacking piece
        builder.setPiece(new Pawn(Alliance.BLACK, 35));
        builder.setPiece(new Pawn(Alliance.BLACK, 37));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> queenLegalMoves = queen.calcLegalMoves(board);
        assertEquals(2, queenLegalMoves.size());
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d4"))));
        assertTrue(queenLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f4"))));
    }

    @Test
    public void testMiddleKingEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        King whiteKing = new King(Alliance.WHITE, 36);
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setPiece(whiteKing);
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> kingLegalMoves = whiteKing.calcLegalMoves(board);
        assertEquals(8, whiteKing.calcLegalMoves(board).size());
        assertTrue(kingLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d5"))));
        assertTrue(kingLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e5"))));
        assertTrue(kingLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f5"))));
        assertTrue(kingLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d4"))));
        assertTrue(kingLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f4"))));
        assertTrue(kingLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d3"))));
        assertTrue(kingLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("e3"))));
        assertTrue(kingLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f3"))));
    }

    @Test
    public void testKingWrappingEmptyBoard() {
        final Board.Builder  builder= new Board.Builder();
        King kingA = new King(Alliance.WHITE, 32);
        King kingH = new King(Alliance.BLACK, 39);
        builder.setPiece(kingA);
        builder.setPiece(kingH);
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> kingALegalMoves = kingA.calcLegalMoves(board);
        final Collection<Move> kingHLegalMoves = kingH.calcLegalMoves(board);
        // check king A
        assertEquals(5, kingALegalMoves.size());
        assertTrue(kingALegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("a4"), BoardUtils.getCoordFromPos("a5"))));
        assertTrue(kingALegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("a4"), BoardUtils.getCoordFromPos("b5"))));
        assertTrue(kingALegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("a4"), BoardUtils.getCoordFromPos("b4"))));
        assertTrue(kingALegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("a4"), BoardUtils.getCoordFromPos("a3"))));
        assertTrue(kingALegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("a4"), BoardUtils.getCoordFromPos("b3"))));

        // check king H
        assertEquals(5, kingHLegalMoves.size());
        assertTrue(kingHLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("h4"), BoardUtils.getCoordFromPos("g5"))));
        assertTrue(kingHLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("h4"), BoardUtils.getCoordFromPos("h5"))));
        assertTrue(kingHLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("h4"), BoardUtils.getCoordFromPos("g4"))));
        assertTrue(kingHLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("h4"), BoardUtils.getCoordFromPos("g4"))));
        assertTrue(kingHLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("h4"), BoardUtils.getCoordFromPos("h3"))));
    }

    @Test
    public void testKingPieceBlocking() {
        final Board.Builder builder = new Board.Builder();
        King king = new King(Alliance.WHITE, 36);
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setPiece(king);
        // blocking pieces
        builder.setPiece(new Pawn(Alliance.WHITE, 27));
        builder.setPiece(new Pawn(Alliance.WHITE, 28));
        builder.setPiece(new Pawn(Alliance.WHITE, 29));
        builder.setPiece(new Pawn(Alliance.WHITE, 43));
        builder.setPiece(new Pawn(Alliance.WHITE, 44));
        builder.setPiece(new Pawn(Alliance.WHITE, 45));
        // attacking piece
        builder.setPiece(new Pawn(Alliance.BLACK, 35));
        builder.setPiece(new Pawn(Alliance.BLACK, 37));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> kingLegalMoves = king.calcLegalMoves(board);
        assertEquals(2, kingLegalMoves.size());
        assertTrue(kingLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("d4"))));
        assertTrue(kingLegalMoves.contains(
                MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e4"), BoardUtils.getCoordFromPos("f4"))));
    }

    @Test
    public void testMoveIntoCheck() {
        final Board.Builder builder = new Board.Builder();
        // White setup
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new Rook(Alliance.WHITE, 35));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 0));
        builder.setPiece(new Queen(Alliance.BLACK, 11));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        final Move invalidMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("d4"),
                        BoardUtils.getCoordFromPos("a4"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(invalidMove);
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, t1.getMoveStatus());
        assertFalse(t1.getMoveStatus().isDone());
    }

    @Test
    public void testNotMovingOutOfCheck() {
        final Board.Builder builder = new Board.Builder();
        // White setup
        builder.setPiece(new King(Alliance.WHITE, 35));
        builder.setPiece(new Rook(Alliance.WHITE, 59));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 0));
        builder.setPiece(new Queen(Alliance.BLACK, 11));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        final Move invalidMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("d1"),
                        BoardUtils.getCoordFromPos("a1"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(invalidMove);
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, t1.getMoveStatus());
        assertFalse(t1.getMoveStatus().isDone());
    }
    
    @Test
    public void testHashCode() {
        final Board board = Board.createStandardBoard();
        final Set<Piece> pieceSet = new HashSet<>(board.getAllPieces());
        final Set<Piece> whitePieceSet = new HashSet<>(board.getWhitePieces());
        final Set<Piece> blackPieceSet = new HashSet<>(board.getBlackPieces());
        assertEquals(32, pieceSet.size());
        assertEquals(16, whitePieceSet.size());
        assertEquals(16, blackPieceSet.size());
    }
}