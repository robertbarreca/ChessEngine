package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Move.QueenSideCastleMove;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Piece.PieceType;
import com.chess.engine.pieces.Rook;

public class BlackPlayer extends Player{

    /**
     * Constructor that creates a black player
     * @param board the board used to play the game
     * @param blackMoves All legal moves that white can make
     * @param blackMoves All legal moves that black can make
     */
    public BlackPlayer
        (final Board board, final Collection<Move> whiteMoves, final Collection<Move> blackMoves) {
        super(board, blackMoves, whiteMoves);
    }

    /**
     * Gets all the active pieces for the player
     * @return All black pieces on the board
     */
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    /**
     * Gets the alliance of the player
     * @return the black alliance
     */
    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    /**
     * Says whether a king side castle is capable or not
     * @return true if a king side castle is capable and false otherwise
     */
    @Override
    public boolean isKingSideCastleCapable() {
        Piece kingSidePiece = this.board.getTile(7).getPiece();
        if (kingSidePiece != null && 
            kingSidePiece.getPieceType() == PieceType.ROOK && 
            !kingSidePiece.hasMoved() && 
            kingSidePiece.getAlliance().isBlack()){
                return !this.king.hasMoved();
            }
            return false;
    }

    /**
     * Says whether a queen sied castle is capable or not
     * @return true if a queen side castle is capable and false otherwise
     */
    @Override
    public boolean isQueenSideCastleCapable() {
        Piece queenSidePiece = this.board.getTile(0).getPiece();
        if (queenSidePiece != null && 
            queenSidePiece.getPieceType() == PieceType.ROOK && 
            !queenSidePiece.hasMoved() && 
            queenSidePiece.getAlliance().isBlack()){
                return !this.king.hasMoved();
            }
            return false;
    }

    /**
     * get the opposing player
     * @return the white player
     */
    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }

    /**
     * Finds all the castle moves the black player can make
     * @param legalMoves the moves that black can make
     * @param opponentMoves the moves that white can make
     * @return all the castle moves the black player can make
     */
    @Override
    public Collection<Move> calcCastleMoves(final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
        final List<Move> castleMoves = new ArrayList<>();
        // king hasn't moved nor is in check
        if (!this.king.hasMoved() && !this.isInCheck()) {
            // king side castle
            // check no pieces are in between
            if (!this.board.getTile(5).isOccupied() && !this.board.getTile(6).isOccupied()) {
                final Tile rookTile = this.board.getTile(7);
                // check piece is rook and piece hasn't moved
                if (rookTile.isOccupied() &&
                        rookTile.getPiece().getPieceType().isRook() &&
                        !rookTile.getPiece().hasMoved()) {
                    // check not castling thru check
                    if (calculateAttacksOnTile(5, opponentMoves).isEmpty() &&
                            calculateAttacksOnTile(6, opponentMoves).isEmpty()) {
                        castleMoves.add(new KingSideCastleMove(this.board, this.king, 6, (Rook) rookTile.getPiece(), rookTile.getCoord(), 5));
                    }
                }
            }

            // queen side castle
            // check no pieces are in between
            if (!this.board.getTile(3).isOccupied() &&
                    !this.board.getTile(2).isOccupied() &&
                    !this.board.getTile(1).isOccupied()) {
                final Tile rookTile = this.board.getTile(0);
                // check piece is rook and piece hasn't moved
                if (rookTile.isOccupied() &&
                        rookTile.getPiece().getPieceType().isRook() &&
                        !rookTile.getPiece().hasMoved()) {
                    // check not castling thru check
                    if (calculateAttacksOnTile(3, opponentMoves).isEmpty() &&
                            calculateAttacksOnTile(2, opponentMoves).isEmpty()) {
                        castleMoves.add(new QueenSideCastleMove(this.board, this.king, 2, (Rook) rookTile.getPiece(), rookTile.getCoord(), 3));
                    }
                }
            }
        }
        return castleMoves;
    }

        
    
}
