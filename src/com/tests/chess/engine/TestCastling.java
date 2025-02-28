package com.tests.chess.engine;

import org.junit.Test;
import static org.junit.Assert.*;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.MoveTransition;

public class TestCastling {
    // Test white king side
    @Test
    public void testWhiteKingSideCastle() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        King whiteKing = new King(Alliance.WHITE, 60);
        builder.setPiece(whiteKing);
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
        assertTrue(whiteKing.calcLegalMoves(board).contains(castleMove));
        assertTrue(t.getMoveStatus().isDone());
        assertTrue(t.getUpdatedBoard().getWhitePlayer().hasCastled());
    }
    // test white queen side
    @Test
    public void testWhiteQueenSideCastle() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        King whiteKing = new King(Alliance.WHITE, 60);
        builder.setPiece(whiteKing);
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
        assertTrue(whiteKing.calcLegalMoves(board).contains(castleMove));
        assertTrue(t.getMoveStatus().isDone());
        assertTrue(t.getUpdatedBoard().getWhitePlayer().hasCastled());
    }

    // test white when king moved
    @Test 
    public void testWhiteCastleKingMoved() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        King whiteKing = new King(Alliance.WHITE, 60, true, false);
        builder.setPiece(whiteKing);
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        // test king side
        final Move kingSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"),
                BoardUtils.getCoordFromPos("g1"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(kingSideCastleMove);
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(kingSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(kingSideCastleMove));
        assertFalse(t1.getMoveStatus().isDone());
        assertFalse(t1.getUpdatedBoard().getWhitePlayer().hasCastled());

        // test queen side
        final Move queenSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"),
                BoardUtils.getCoordFromPos("a1"));
        MoveTransition t2 = board.getCurrPlayer().makeMove(queenSideCastleMove);
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(queenSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(queenSideCastleMove));
        assertFalse(t2.getMoveStatus().isDone());
        assertFalse(t2.getUpdatedBoard().getWhitePlayer().hasCastled());
    }
    
    // test white when rook moved
    @Test 
    public void testWhiteCastleRookMoved() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        King whiteKing = new King(Alliance.WHITE, 60);
        builder.setPiece(whiteKing);
        builder.setPiece(new Rook(Alliance.WHITE, 56, true));
        builder.setPiece(new Rook(Alliance.WHITE, 63, true));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        // test king side
        final Move kingSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"),
                BoardUtils.getCoordFromPos("g1"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(kingSideCastleMove);
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(kingSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(kingSideCastleMove));
        assertFalse(t1.getMoveStatus().isDone());
        assertFalse(t1.getUpdatedBoard().getWhitePlayer().hasCastled());

        // test queen side
        final Move queenSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"),
                BoardUtils.getCoordFromPos("a1"));
        MoveTransition t2 = board.getCurrPlayer().makeMove(queenSideCastleMove);
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(queenSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(queenSideCastleMove));
        assertFalse(t2.getMoveStatus().isDone());
        assertFalse(t2.getUpdatedBoard().getWhitePlayer().hasCastled());
    }
    
    // test white castling into check
    @Test
    public void testWhiteCastlingIntoCheck() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        King whiteKing = new King(Alliance.WHITE, 60);
        builder.setPiece(whiteKing);
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Rook(Alliance.BLACK, 2));
        builder.setPiece(new Rook(Alliance.BLACK, 6));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        final Move kingSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"),
                BoardUtils.getCoordFromPos("g1"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(kingSideCastleMove);
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(kingSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(kingSideCastleMove));
        assertFalse(t1.getMoveStatus().isDone());
        assertFalse(t1.getUpdatedBoard().getWhitePlayer().hasCastled());

        final Move queenSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"),
                BoardUtils.getCoordFromPos("g1"));
        final MoveTransition t2 = board.getCurrPlayer().makeMove(queenSideCastleMove);
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(queenSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(queenSideCastleMove));
        assertFalse(t2.getMoveStatus().isDone());
        assertFalse(t2.getUpdatedBoard().getWhitePlayer().hasCastled());
    }
    
    // test white castling thru check
    @Test
    public void testWhiteCastlingThruCheck() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        King whiteKing = new King(Alliance.WHITE, 60);
        builder.setPiece(whiteKing);
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Rook(Alliance.BLACK, 3));
        builder.setPiece(new Rook(Alliance.BLACK, 5));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        final Move kingSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"),
                BoardUtils.getCoordFromPos("g1"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(kingSideCastleMove);
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(kingSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(kingSideCastleMove));
        assertFalse(t1.getMoveStatus().isDone());
        assertFalse(t1.getUpdatedBoard().getWhitePlayer().hasCastled());

        final Move queenSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"),
                BoardUtils.getCoordFromPos("g1"));
        final MoveTransition t2 = board.getCurrPlayer().makeMove(queenSideCastleMove);
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(queenSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(queenSideCastleMove));
        assertFalse(t2.getMoveStatus().isDone());
        assertFalse(t2.getUpdatedBoard().getWhitePlayer().hasCastled());
    }
    
    // test white castling thru check
    @Test
    public void testWhiteCastlingOutOfCheck() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        King whiteKing = new King(Alliance.WHITE, 60);
        builder.setPiece(whiteKing);
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Rook(Alliance.BLACK, 12));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        final Move kingSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"), BoardUtils.getCoordFromPos("g1"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(kingSideCastleMove);
        assertTrue(board.getWhitePlayer().isInCheck());
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(kingSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(kingSideCastleMove));
        assertFalse(t1.getMoveStatus().isDone());
        assertFalse(t1.getUpdatedBoard().getWhitePlayer().hasCastled());

        final Move queenSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"), BoardUtils.getCoordFromPos("g1"));
        final MoveTransition t2 = board.getCurrPlayer().makeMove(queenSideCastleMove);
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(queenSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(queenSideCastleMove));
        assertFalse(t2.getMoveStatus().isDone());
        assertFalse(t2.getUpdatedBoard().getWhitePlayer().hasCastled());
    }

    // test white castling with pieces blocking
    @Test
    public void testWhiteCastlingPieceBlocking() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        King whiteKing = new King(Alliance.WHITE, 60);
        builder.setPiece(whiteKing);
        // blocking pieces
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        // rooks
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // 
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        final Board board = builder.build();

        // king side
        final Move kingSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"), BoardUtils.getCoordFromPos("g1"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(kingSideCastleMove);
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(kingSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(kingSideCastleMove));
        assertFalse(t1.getMoveStatus().isDone());
        assertFalse(t1.getUpdatedBoard().getWhitePlayer().hasCastled());

        // queen side
        final Move queenSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e1"), BoardUtils.getCoordFromPos("g1"));
        final MoveTransition t2 = board.getCurrPlayer().makeMove(queenSideCastleMove);
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(queenSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(queenSideCastleMove));
        assertFalse(t2.getMoveStatus().isDone());
        assertFalse(t2.getUpdatedBoard().getWhitePlayer().hasCastled());
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
        King blackKing = new King(Alliance.BLACK, 4);
        builder.setPiece(blackKing);
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.BLACK);
        final Board board = builder.build();

        final Move castleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"),
                BoardUtils.getCoordFromPos("g8"));
        final MoveTransition t = board.getCurrPlayer().makeMove(castleMove);
        assertTrue(board.getBlackPlayer().getLegalMoves().contains(castleMove));
        assertTrue(blackKing.calcLegalMoves(board).contains(castleMove));
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
        King blackKing = new King(Alliance.BLACK, 4);
        builder.setPiece(blackKing);
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.BLACK);
        final Board board = builder.build();

        final Move castleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"),
                BoardUtils.getCoordFromPos("c8"));
        final MoveTransition t = board.getCurrPlayer().makeMove(castleMove);
        assertTrue(board.getBlackPlayer().getLegalMoves().contains(castleMove));
        assertTrue(blackKing.calcLegalMoves(board).contains(castleMove));
        assertTrue(t.getMoveStatus().isDone());
        assertTrue(t.getUpdatedBoard().getBlackPlayer().hasCastled());
    }

    // test black when king moved
    @Test 
    public void testBlackCastleKingMoved() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        King whiteKing = new King(Alliance.WHITE, 60);
        builder.setPiece(whiteKing);
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 4, true, false));
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.BLACK);
        final Board board = builder.build();

        // test king side
        final Move kingSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"), BoardUtils.getCoordFromPos("g8"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(kingSideCastleMove);
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(kingSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(kingSideCastleMove));
        assertFalse(t1.getMoveStatus().isDone());
        assertFalse(t1.getUpdatedBoard().getBlackPlayer().hasCastled());

        // test queen side
        final Move queenSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"), BoardUtils.getCoordFromPos("a8"));
        MoveTransition t2 = board.getCurrPlayer().makeMove(queenSideCastleMove);
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(queenSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(queenSideCastleMove));
        assertFalse(t2.getMoveStatus().isDone());
        assertFalse(t2.getUpdatedBoard().getBlackPlayer().hasCastled());
    }

    // test black when rook moved
    @Test 
    public void testBlackCastleRookMoved() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        King whiteKing = new King(Alliance.WHITE, 60);
        builder.setPiece(whiteKing);
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // black setup
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Rook(Alliance.BLACK, 0, true));
        builder.setPiece(new Rook(Alliance.BLACK, 7, true));
        builder.setCurrPlayerAlliance(Alliance.BLACK);
        final Board board = builder.build();

        // test king side
        final Move kingSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"),
                BoardUtils.getCoordFromPos("g8"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(kingSideCastleMove);
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(kingSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(kingSideCastleMove));
        assertFalse(t1.getMoveStatus().isDone());
        assertFalse(t1.getUpdatedBoard().getBlackPlayer().hasCastled());

        // test queen side
        final Move queenSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"),
                BoardUtils.getCoordFromPos("a8"));
        MoveTransition t2 = board.getCurrPlayer().makeMove(queenSideCastleMove);
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(queenSideCastleMove));
        assertFalse(whiteKing.calcLegalMoves(board).contains(queenSideCastleMove));
        assertFalse(t2.getMoveStatus().isDone());
        assertFalse(t2.getUpdatedBoard().getBlackPlayer().hasCastled());
    }
    
    // test black castling into check
    @Test
    public void testBlackCastlingIntoCheck() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Rook(Alliance.WHITE, 58));
        builder.setPiece(new Rook(Alliance.WHITE, 62));
        // black setup
        King blackKing = new King(Alliance.BLACK, 4);
        builder.setPiece(blackKing);
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.BLACK);
        final Board board = builder.build();

        // king side
        final Move kingSideCastleMoves = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"), BoardUtils.getCoordFromPos("g8"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(kingSideCastleMoves);
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(kingSideCastleMoves));
        assertFalse(blackKing.calcLegalMoves(board).contains(kingSideCastleMoves));
        assertFalse(t1.getMoveStatus().isDone());
        assertFalse(t1.getUpdatedBoard().getBlackPlayer().hasCastled());
        
        // queen side
        final Move queenSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"), BoardUtils.getCoordFromPos("c8"));
        final MoveTransition t2 = board.getCurrPlayer().makeMove(queenSideCastleMove);
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(queenSideCastleMove));
        assertFalse(blackKing.calcLegalMoves(board).contains(queenSideCastleMove));
        assertFalse(t2.getMoveStatus().isDone());
        assertFalse(t2.getUpdatedBoard().getBlackPlayer().hasCastled());
    }

    // test black castling thru check
    @Test
    public void testBlackCastlingThruCheck() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Rook(Alliance.WHITE, 59));
        builder.setPiece(new Rook(Alliance.WHITE, 61));
        // black setup
        King blackKing = new King(Alliance.BLACK, 4);
        builder.setPiece(blackKing);
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.BLACK);
        final Board board = builder.build();

        // king side
        final Move kingSideCastleMoves = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"),
                BoardUtils.getCoordFromPos("g8"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(kingSideCastleMoves);
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(kingSideCastleMoves));
        assertFalse(blackKing.calcLegalMoves(board).contains(kingSideCastleMoves));
        assertFalse(t1.getMoveStatus().isDone());
        assertFalse(t1.getUpdatedBoard().getBlackPlayer().hasCastled());

        // queen side
        final Move queenSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"),
                BoardUtils.getCoordFromPos("c8"));
        final MoveTransition t2 = board.getCurrPlayer().makeMove(queenSideCastleMove);
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(queenSideCastleMove));
        assertFalse(blackKing.calcLegalMoves(board).contains(queenSideCastleMove));
        assertFalse(t2.getMoveStatus().isDone());
        assertFalse(t2.getUpdatedBoard().getBlackPlayer().hasCastled());
    }
    
    // test black castling out of check
    @Test
    public void testBlackCastlingOutOfCheck() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Rook(Alliance.WHITE, 52));
        // black setup
        King blackKing = new King(Alliance.BLACK, 4);
        builder.setPiece(blackKing);
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.BLACK);
        final Board board = builder.build();

        // king side
        final Move kingSideCastleMoves = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"), BoardUtils.getCoordFromPos("g8"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(kingSideCastleMoves);
        assertTrue(board.getBlackPlayer().isInCheck());
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(kingSideCastleMoves));
        assertFalse(blackKing.calcLegalMoves(board).contains(kingSideCastleMoves));
        assertFalse(t1.getMoveStatus().isDone());
        assertFalse(t1.getUpdatedBoard().getBlackPlayer().hasCastled());

        // queen side
        final Move queenSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"), BoardUtils.getCoordFromPos("c8"));
        final MoveTransition t2 = board.getCurrPlayer().makeMove(queenSideCastleMove);
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(queenSideCastleMove));
        assertFalse(blackKing.calcLegalMoves(board).contains(queenSideCastleMove));
        assertFalse(t2.getMoveStatus().isDone());
        assertFalse(t2.getUpdatedBoard().getBlackPlayer().hasCastled());
    }
    
    // test black castling with pieces blocking
    @Test
    public void testBlackCastlingPieceBlocking() {
        final Board.Builder builder = new Board.Builder();
        // white setup
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // black setup
        King blackKing = new King(Alliance.BLACK, 4);
        builder.setPiece(blackKing);
        // blocking pieces
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        // rooks
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setCurrPlayerAlliance(Alliance.BLACK);
        final Board board = builder.build();

        // king side
        final Move kingSideCastleMoves = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"), BoardUtils.getCoordFromPos("g8"));
        final MoveTransition t1 = board.getCurrPlayer().makeMove(kingSideCastleMoves);
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(kingSideCastleMoves));
        assertFalse(blackKing.calcLegalMoves(board).contains(kingSideCastleMoves));
        assertFalse(t1.getMoveStatus().isDone());
        assertFalse(t1.getUpdatedBoard().getBlackPlayer().hasCastled());

        // queen side
        final Move queenSideCastleMove = MoveFactory.createMove(board, BoardUtils.getCoordFromPos("e8"), BoardUtils.getCoordFromPos("c8"));
        final MoveTransition t2 = board.getCurrPlayer().makeMove(queenSideCastleMove);
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(queenSideCastleMove));
        assertFalse(blackKing.calcLegalMoves(board).contains(queenSideCastleMove));
        assertFalse(t2.getMoveStatus().isDone());
        assertFalse(t2.getUpdatedBoard().getBlackPlayer().hasCastled());
    }
}
