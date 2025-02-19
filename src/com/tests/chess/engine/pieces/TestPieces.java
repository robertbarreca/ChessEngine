package com.tests.chess.engine.pieces;


import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Queen;

public class TestPieces {

    @Test
    public void testKnightMiddleOnEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        Knight knight = new Knight(Alliance.WHITE, 36);
        builder.setPiece(knight);
        final Board board = builder.build();
        final Collection<Move> legalMoves = knight.calcLegalMoves(board);
        assertEquals(8, legalMoves.size());

    }
    
    @Test
    public void testKnightStartingPositions() {
        final Board.Builder builder = new Board.Builder();

        Knight blackKnight0 = new Knight(Alliance.BLACK, 1);
        Knight blackKnight1 = new Knight(Alliance.BLACK, 6);
        Knight whiteKnight0 = new Knight(Alliance.WHITE, 62);
        Knight whiteKnight1 = new Knight(Alliance.WHITE, 57);

        builder.setPiece(blackKnight0);
        builder.setPiece(blackKnight1);
        builder.setPiece(whiteKnight0);
        builder.setPiece(whiteKnight1);
        final Board board = builder.build();

        final Collection<Move> blackKnight0LegalMoves = blackKnight0.calcLegalMoves(board);
        final Collection<Move> blackKnight1LegalMoves = blackKnight1.calcLegalMoves(board);
        final Collection<Move> whiteKnight0LegalMoves = whiteKnight0.calcLegalMoves(board);
        final Collection<Move> whiteKnight1LegalMoves = whiteKnight1.calcLegalMoves(board);

        assertEquals(3, blackKnight0LegalMoves.size());
        assertEquals(3, blackKnight1LegalMoves.size());
        assertEquals(3, whiteKnight0LegalMoves.size());
        assertEquals(3, whiteKnight1LegalMoves.size());
    }
    
    @Test
    public void testStartingPawns() {
        final Board.Builder builder = new Board.Builder();

        final Pawn[] blackPawns = new Pawn[8];
        final Pawn[] whitePawns = new Pawn[8];

        // populate starting pawns
        for (int i = 0; i < 8; i++) {
            Pawn blackPawn = new Pawn(Alliance.BLACK, i + 8);
            Pawn whitePawn = new Pawn(Alliance.WHITE, i + 48);

            blackPawns[i] = blackPawn;
            whitePawns[i] = whitePawn;

            builder.setPiece(blackPawn);
            builder.setPiece(whitePawn);
        }
        Board board = builder.build();
        for (int i = 0; i < 8; i++) {
            Collection<Move> blackLegalMoves = blackPawns[i].calcLegalMoves(board);
            Collection<Move> whiteLegalMoves = whitePawns[i].calcLegalMoves(board);
            assertEquals(2, blackLegalMoves.size());
            assertEquals(2, whiteLegalMoves.size());
        }
    }
    // @Test
    // public void testMiddleQueenOnEmptyBoard() {
    //     final Board.Builder builder = new Board.Builder();
    //     // Black Layout
    //     builder.setPiece(new King(Alliance.BLACK, 4));
    //     // White Layout
    //     builder.setPiece(new Queen(Alliance.WHITE, 36));
    //     builder.setPiece(new King(Alliance.WHITE, 60));
    //     // Set the current player
    //     builder.setCurrPlayer(Alliance.WHITE);
    //     final Board board = builder.build();
    //     final Collection<Move> whiteLegals = board.calcMoves(board.getWhitePieces());
    //     final Collection<Move> blackLegals = board.calcMoves(board.getBlackPieces());
    //     assertEquals(31, whiteLegals.size());
    //     assertEquals(5, blackLegals.size());
    // }
}
