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
import com.chess.engine.board.Tile;

/**
 * This class represents a single rook on a chessboard
 */
public class Rook extends Piece{

    private static final int[] CANDIDATE_MOVE_OFFSETS = { -8, -1, 1, 8 };


    /**
     * Constructor that sets the position and the alliance of the rook
     * @param position the position the rook is in 
     * @param alliance the team that the rook is aligned with
     */
    public Rook(final Alliance alliance, final int position) {
        super(PieceType.ROOK, alliance, position);
    }

    /**
     * Calculates all legal moves that a rook can make
     * @param board the current state of the board
     * @return a list of all legal moves a rook can make
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
        return Collections.unmodifiableList(legalMoves);
    }
    
    /**
     * Converts the rook to a string
     * @return the string representation of the rook
     */
    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }

    /**
     * Checks if the rook is on column A and if the given move offset 
     * would cause it to wrap around to the wrong position
     *
     * @param currPos The current position of the rook on the board.
     * @param candidateOffset The move offset being considered.
     * @return true if the move is invalid due to the rook being in column A, false otherwise.
     */
    private static boolean isColumnAExclusion(final int currPos, final int candidateOffset) {
        return BoardUtils.COLUMN_A[currPos] && candidateOffset == -1;
    }

    /**
     * Checks if the rook is on column H and if the given move offset 
     * would cause it to wrap around to the wrong position
     *
     * @param currPos The current position of the rook on the board.
     * @param candidateOffset The move offset being considered.
     * @return true if the move is invalid due to the rook being in column H, false otherwise.
     */
    private static boolean isColumnHExclusion(final int currPos, final int candidateOffset) {
        return BoardUtils.COLUMN_H[currPos] && candidateOffset == 1;
    }
    
    /**
     * Creates a new rook based on the move
     * @param move the move that was made
     * @return the new rook created from the move
     */
    @Override
    public Rook movePiece(Move move) {
        return new Rook(move.getMovedPiece().getAlliance(), move.getDestCoord());
    }
}
