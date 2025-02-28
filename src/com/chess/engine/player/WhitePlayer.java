package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Move.QueenSideCastleMove;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public class WhitePlayer extends Player{

    /**
     * Constructor that creates a black player
     * @param board the board used to play the game
     * @param whiteMoves All legal moves that white can make
     * @param blackMoves All legal moves that black can make
     */
    public WhitePlayer (final Board board, final Collection<Move> whiteMoves, final Collection<Move> blackMoves) {
        super(board, whiteMoves, blackMoves); 
    }

    /**
     * Gets all the active pieces for the player
     * @return All white pieces on the board
     */
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    /**
     * Gets the alliance of the player
     * @return the white alliance
     */
    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    /**
     * get the opposing player
     * @return the white player
     */
    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }

    /**
     * Finds all the castle moves the white player can make
     * @param legalMoves the moves that white can make
     * @param opponentMoves the moves that black can make
     * @return all the castle moves the white player can make
     */
    @Override
    public Collection<Move> calcCastleMoves(final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
        final List<Move> castleMoves = new ArrayList<>();
        // king hasn't moved nor is in check nor has castled
        if (!this.hasCastled() && !this.king.hasMoved() && !this.isInCheck()) {
            // king side castle
            // check no pieces are in between
            if (!this.board.getTile(61).isOccupied() && !this.board.getTile(62).isOccupied()) {
                final Tile rookTile = this.board.getTile(63);
                // check piece is rook and piece hasn't moved
                if (rookTile.isOccupied() &&
                    rookTile.getPiece().getPieceType().isRook() &&
                    !rookTile.getPiece().hasMoved()) {
                    // check not castling thru check
                    if (calculateAttacksOnTile(61, opponentMoves).isEmpty() &&
                        calculateAttacksOnTile(62, opponentMoves).isEmpty()) {
                        castleMoves.add(new KingSideCastleMove(this.board, this.king, 62, (Rook) rookTile.getPiece(), rookTile.getCoord(), 61));
                    }
                }
            }
            
            // queen side castle
            // check no pieces are in between
            if (!this.board.getTile(59).isOccupied() &&
                !this.board.getTile(58).isOccupied() &&
                !this.board.getTile(57).isOccupied()) {
                final Tile rookTile = this.board.getTile(56);
                // check piece is rook and piece hasn't moved
                if (rookTile.isOccupied() &&
                    rookTile.getPiece().getPieceType().isRook() &&
                    !rookTile.getPiece().hasMoved()) {
                    // check not castling thru check
                    if (calculateAttacksOnTile(59, opponentMoves).isEmpty() &&
                        calculateAttacksOnTile(58, opponentMoves).isEmpty()) {
                        castleMoves.add(new QueenSideCastleMove(this.board, this.king, 58, (Rook) rookTile.getPiece(), rookTile.getCoord(), 59));
                    }
                }
            }
        }

        return Collections.unmodifiableList(castleMoves);
    }
    
}
