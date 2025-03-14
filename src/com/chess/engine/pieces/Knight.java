package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import com.chess.engine.Alliance;
import com.chess.engine.board.*;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.PassiveMove;

/**
 * This class represents a single knight on a chessboard
 */
public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_OFFSETS = { -17, -15, -10, -6, 6, 10, 15, 17 };

    /**
     * Constructor that sets the position and the alliance of the knight based on the passed params. It also sets the hasMoved field to false
     * @param position the position the knight is in 
     * @param alliance the team that the knight is aligned with
     */
    public Knight(final Alliance alliance, final int position) {
        super(PieceType.KNIGHT, alliance, position, false);
    }

    /**
     * Constructor that sets the position, alliance, and has moved fields of the knight based on the passed params.
     * @param position the position the knight is in 
     * @param alliance the team that the knight is aligned with
     */
    public Knight(final Alliance alliance, final int position, final boolean hasMoved) {
        super(PieceType.KNIGHT, alliance, position, hasMoved);
    }

    /**
     * Calculates all legal moves that a knight can make
     * @param board the current state of the board
     * @return a list of all legal moves a knight can make
     */
    @Override
    public Collection<Move> calcLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        // find all possible destinations
        for (final int offset : CANDIDATE_MOVE_OFFSETS) {
            int destCoord = this.position + offset;
            // tile coordinate is valid
            if (BoardUtils.isValidTileCoord(destCoord)) {
                // if move wraps don't add to legal moves
                if (isColumnAExclusion(this.position, offset) ||
                        isColumnBExclusion(this.position, offset) ||
                        isColumnGExclusion(this.position, offset) ||
                        isColumnHExclusion(this.position, offset)) {
                    continue;
                }
                final Tile destTile = board.getTile(destCoord);
                // tile is unoccupied
                if (!destTile.isOccupied()) {
                    legalMoves.add(new PassiveMove(board, this, destCoord));
                }
                // tile is occupied 
                else {
                    // check if piece is capturable
                    final Piece pieceAtDest = destTile.getPiece();
                    final Alliance allianceAtDest = pieceAtDest.getAlliance();
                    // enemy piece on destination tile
                    if (this.alliance != allianceAtDest) {
                        legalMoves.add(new AttackMove(board, this, destCoord, pieceAtDest));
                    }
                }
            }
        }

        // filter out moves that put king in check
        if (board.getWhitePlayer() != null && this.alliance.isWhite()) {
            return MoveUtils.pruneIllegalMoves(legalMoves, board.getWhitePlayer());
        }
        else if (board.getBlackPlayer() != null && this.alliance.isBlack()) {
            return MoveUtils.pruneIllegalMoves(legalMoves, board.getBlackPlayer());
        }
        
        return Collections.unmodifiableList(legalMoves);
    }

    /**
     * Converts the knight to a string
     * @return the string representation of the knight
     */
    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

    /**
     * Checks if the knight is on column A and if the given move offset 
     * would cause it to wrap around to the wrong position
     *
     * @param currPos The current position of the knight on the board.
     * @param candidateOffset The move offset being considered.
     * @return true if the move is invalid due to the knight being in column A, false otherwise.
     */
    private static boolean isColumnAExclusion(final int currPos, final int candidateOffset) {
        return BoardUtils.COLUMN_A[currPos] && (candidateOffset == -17 || candidateOffset == -10
                || candidateOffset == 6 || candidateOffset == 15);
    }
    
    /**
     * Checks if the knight is on column B and if the given move offset 
     * would cause it to wrap around to the wrong position
     *
     * @param currPos The current position of the knight on the board.
     * @param candidateOffset The move offset being considered.
     * @return true if the move is invalid due to the knight being in column B, false otherwise.
     */
    private static boolean isColumnBExclusion(final int currPos, final int candidateOffset) {
        return BoardUtils.COLUMN_B[currPos] && (candidateOffset == -10 || candidateOffset == 6);
    }

    /**
     * Checks if the knight is on column G and if the given move offset 
     * would cause it to wrap around to the wrong position.
     *
     * @param currPos The current position of the knight on the board.
     * @param candidateOffset The move offset being considered.
     * @return true if the move is invalid due to the knight being in column G, false otherwise.
     */
    private static boolean isColumnGExclusion(final int currPos, final int candidateOffset) {
        return BoardUtils.COLUMN_G[currPos] && (candidateOffset == -6 || candidateOffset == 10);
    }

    /**
     * Checks if the knight is on column H and if the given move offset 
     * would cause it to wrap around to the wrong position
     *
     * @param currPos The current position of the knight on the board.
     * @param candidateOffset The move offset being considered.
     * @return true if the move is invalid due to the knight being in column H, false otherwise.
     */
    private static boolean isColumnHExclusion(final int currPos, final int candidateOffset) {
        return BoardUtils.COLUMN_H[currPos] && (candidateOffset == -15 || candidateOffset == -6
                || candidateOffset == 10 || candidateOffset == 17);
    }

    /**
     * Creates a new knight based on the move
     * @param move the move that was made
     * @return the new knight created from the move
     */
    @Override
    public Knight movePiece(Move move) {
        return new Knight(move.getMovedPiece().getAlliance(), move.getDestCoord());
    }
}


