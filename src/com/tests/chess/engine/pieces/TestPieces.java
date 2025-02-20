package com.tests.chess.engine.pieces;


import static org.junit.Assert.*;

import org.junit.Test;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
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
        Board board = builder.build();
        assertEquals(2, pawn.calcLegalMoves(board).size());
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
        Board board = builder.build();
        assertEquals(4, attackingPawn.calcLegalMoves(board).size());

        // can attack only left piece
        builder.setPiece(new Pawn(Alliance.WHITE, 43));
        board = builder.build();

        assertEquals(3, attackingPawn.calcLegalMoves(board).size());

        // can attack only right piece
        builder.setPiece(new Pawn(Alliance.WHITE, 41));
        builder.setPiece(new Pawn(Alliance.BLACK, 43));
        board = builder.build();
        assertEquals(3, attackingPawn.calcLegalMoves(board).size());
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
        Board board = builder.build();
        assertEquals(3, attackingPawn.calcLegalMoves(board).size());

        // can attack only left piece
        builder.setPiece(new Pawn(Alliance.WHITE, 35));
        board = builder.build();
        assertEquals(2, attackingPawn.calcLegalMoves(board).size());

        // can attack only right piece
        builder.setPiece(new Pawn(Alliance.WHITE, 33));
        builder.setPiece(new Pawn(Alliance.BLACK, 35));
        board = builder.build();
        assertEquals(2, attackingPawn.calcLegalMoves(board).size());
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
        Board board = builder.build();
        assertEquals(1, whitePawn.calcLegalMoves(board).size());
        assertEquals(1, blackPawn.calcLegalMoves(board).size());
        
        
        // white pawn column A and black pawn column H
        whitePawn = new Pawn(Alliance.WHITE, 32);
        blackPawn = new Pawn(Alliance.BLACK, 23);
        builder.setPiece(whitePawn);
        builder.setPiece(blackPawn);
        board = builder.build();
        assertEquals(1, whitePawn.calcLegalMoves(board).size());
        assertEquals(1, blackPawn.calcLegalMoves(board).size());
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
        Board board = builder.build();
        assertEquals(1, startingPawn.calcLegalMoves(board).size());
        
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
    public void testMiddleKnightEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        Knight knight = new Knight(Alliance.WHITE, 36);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setPiece(knight);
        final Board board = builder.build();
        assertEquals(8, knight.calcLegalMoves(board).size());
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

        Board board = builder.build();

        assertEquals(4, knightA.calcLegalMoves(board).size());
        assertEquals(6, knightB.calcLegalMoves(board).size());
        assertEquals(6, knightG.calcLegalMoves(board).size());
        assertEquals(4, knightH.calcLegalMoves(board).size());
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
        final Board board = builder.build();
        assertEquals(7, knight.calcLegalMoves(board).size());
    }

    @Test
    public void testMiddleBishopEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        Bishop bishop = new Bishop(Alliance.WHITE, 36);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setPiece(bishop);
        Board board = builder.build();
        assertEquals(13, bishop.calcLegalMoves(board).size());
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
        Board board = builder.build();
        assertEquals(9, bishop.calcLegalMoves(board).size());
    }

    @Test
    public void testMiddleRookEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        Rook rook = new Rook(Alliance.WHITE, 36);
        builder.setPiece(rook);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        Board board = builder.build();
        assertEquals(14, rook.calcLegalMoves(board).size());
    }

    @Test
    public void testRookPieceBlocking() {
        final Board.Builder builder = new Board.Builder();
        Rook rook = new Rook(Alliance.WHITE, 36);
        builder.setPiece(rook);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        // blocking piece
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        // attacking piece
        builder.setPiece(new Pawn(Alliance.WHITE, 33));

        Board board = builder.build();
        assertEquals(11, rook.calcLegalMoves(board).size());
    }

    @Test
    public void testMiddleQueenEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        Queen queen = new Queen(Alliance.WHITE, 36);
        builder.setPiece(queen);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        final Board board = builder.build();
        assertEquals(27, queen.calcLegalMoves(board).size());
    }

    @Test
    public void testQueenPieceBlocking() {
        final Board.Builder builder = new Board.Builder();
        Queen queen = new Queen(Alliance.WHITE, 36);
        builder.setPiece(queen);
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.BLACK, 3));
        // blocking piece
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        // attacking piece
        builder.setPiece(new Pawn(Alliance.WHITE, 33));
        final Board board = builder.build();
        assertEquals(24, queen.calcLegalMoves(board).size());
    }

    @Test
    public void testMiddleKingEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        King whiteKing = new King(Alliance.WHITE, 36);
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setPiece(whiteKing);
        final Board board = builder.build();
        assertEquals(8, whiteKing.calcLegalMoves(board).size());
    }

    @Test
    public void testKingWrappingEmptyBoard() {
        final Board.Builder  builder= new Board.Builder();
        King kingA = new King(Alliance.WHITE, 32);
        King kingH = new King(Alliance.BLACK, 39);
        builder.setPiece(kingA);
        builder.setPiece(kingH);
        final Board board = builder.build();
        assertEquals(5, kingA.calcLegalMoves(board).size());
        assertEquals(5, kingH.calcLegalMoves(board).size());
    }

    @Test
    public void testKingPieceBlocking() {
        final Board.Builder builder = new Board.Builder();
        King king = new King(Alliance.WHITE, 36);
        builder.setPiece(new King(Alliance.BLACK, 3));
        builder.setPiece(king);
        // blocking piece
        builder.setPiece(new Pawn(Alliance.WHITE, 35));
        // attacking piece
        builder.setPiece(new Pawn(Alliance.BLACK, 37));
        final Board board = builder.build();
        assertEquals(7, king.calcLegalMoves(board).size());
    }
}
