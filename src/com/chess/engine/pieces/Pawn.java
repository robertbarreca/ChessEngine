package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.PassiveMove;

/**
 * This class represents a single pawn on a chessboard
 */
public class Pawn extends Piece{
    private static final int[] CANDIDATE_MOVE_OFFSETS = { 7, 8, 9, 16 };

    /**
     * Constructor that sets the position and the alliance of the pawn
     * @param position the position the pawn is in 
     * @param alliance the team that the pawn is aligned with
     */
    public Pawn(final Alliance alliance, final int position) {
        super(alliance, position);
    }

    /**
     * Calculates all legal moves that a pawn can make
     * @param board the current state of the board
     * @return a list of all legal moves a pawn can make
     */
    @Override
    public Collection<Move> calcLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int offset : CANDIDATE_MOVE_OFFSETS) {
            int destCoord = this.position + (offset * this.alliance.getPawnDirection());
            // valid tile coord
            if (!BoardUtils.isValidTileCoord(destCoord)) {
                continue;
            }
            // regular pawn move if tile is unocuppied
            if (offset == 8 && !board.getTile(destCoord).isOccupied()) {
                legalMoves.add(new PassiveMove(board, this, destCoord));
            }
            // pawn jump iff pawn hasn't moved
            else if (offset == 16 && !this.hasMoved &&
                    ((BoardUtils.ROW_2[this.position] && this.alliance.isBlack()) ||
                    (BoardUtils.ROW_7[this.position] && this.alliance.isWhite()))) {
                final int behindDestCoord = this.position + (8 * this.alliance.getPawnDirection());
                // two tiles in front aren't occupied
                if (!board.getTile(behindDestCoord).isOccupied() &&
                        !board.getTile(destCoord).isOccupied()) {
                    legalMoves.add(new PassiveMove(board, this, destCoord));
                }
            }
            // atacking move on diagonal
            else if (offset == 7 &&
                    !((BoardUtils.COLUMN_H[this.position] && this.alliance.isWhite()) ||
                            (BoardUtils.COLUMN_A[this.position] && this.alliance.isBlack()))) {
                // tile must be occupied
                if (board.getTile(destCoord).isOccupied()) {
                    // check if piece is capturable
                    Piece pieceOnTile = board.getTile(destCoord).getPiece();
                    if (this.alliance != pieceOnTile.getAlliance()) {
                        legalMoves.add(new AttackMove(board, this, destCoord, pieceOnTile));
                    }
                }
            }
            // atacking move on diagonal
            else if (offset == 9 &&
                    !((BoardUtils.COLUMN_A[this.position] && this.alliance.isWhite()) ||
                            (BoardUtils.COLUMN_H[this.position] && this.alliance.isBlack()))) {
                // tile must be occupied
                if (board.getTile(destCoord).isOccupied()) {
                    // check if piece is capturable
                    Piece pieceOnTile = board.getTile(destCoord).getPiece();
                    if (this.alliance != pieceOnTile.getAlliance()) {
                        legalMoves.add(new AttackMove(board, this, destCoord, pieceOnTile));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }
    
    /**
     * Converts the pawn to a string
     * @return the string representation of the pawn
     */
    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
    
}
