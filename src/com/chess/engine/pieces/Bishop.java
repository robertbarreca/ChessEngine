package com.chess.engine.pieces;

import java.util.*;
import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.chess.engine.board.MoveUtils;
import com.chess.engine.board.Tile;

/**
 * This class represents a single bishop on the chessboard
 */
public class Bishop extends Piece{
    private static final int[] CANDIDATE_MOVE_OFFSETS = { -9, -7, 7, 9 };

    /**
     * Constructor that sets the position and the alliance of the bishop based on the passed params. It also sets the hasMoved field to false
     * @param position the position the bishop is in 
     * @param alliance the team that the bishop is aligned with
     */
    public Bishop(final Alliance alliance, final int position) {
        super(PieceType.BISHOP, alliance, position, false);
    }

    /**
     * Constructor that sets the position, alliance, and has moved fields of the bishop based on the passed params.
     * @param position the position the bishop is in 
     * @param alliance the team that the bishop is aligned with
     */
    public Bishop(final Alliance alliance, final int position, final boolean hasMoved) {
        super(PieceType.BISHOP, alliance, position, hasMoved);
    }

    /**
     * Calculates all legal moves that a bishop can make
     * @param board the current state of the board
     * @return a list of all legal moves a bishop can make
     */
    @Override
    public Collection<Move> calcLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int offset : CANDIDATE_MOVE_OFFSETS) {
            int destCoord = this.position;
            // check edge cases
            while (BoardUtils.isValidTileCoord(destCoord)) {
                if (isColumnAExclusion(destCoord, offset) || isColumnHExclusion(destCoord, offset)) {
                    break;
                }
                destCoord += offset;
                // valid tile
                if (BoardUtils.isValidTileCoord(destCoord)) {
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
                        // can't go any farther bc piece is blocking
                        break;
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
     * Converts the bishop to a string
     * @return the string representation of the bishop
     */
    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
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
        return BoardUtils.COLUMN_A[currPos] && (candidateOffset == -9 || candidateOffset == 7);
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
        return BoardUtils.COLUMN_H[currPos] && (candidateOffset == -7 || candidateOffset == 9);
    }

    /**
     * Creates a new bishop based on the move
     * @param move the move that was made
     * @return the new bishop created from the move
     */
    @Override
    public Bishop movePiece(Move move) {
        return new Bishop(move.getMovedPiece().getAlliance(), move.getDestCoord());
    }
}
