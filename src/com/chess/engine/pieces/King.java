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
import com.chess.engine.player.Player;
import com.chess.engine.board.Tile;

/**
 * This class represents a single king on the chessboard
 */
public class King extends Piece {
    private final boolean hasCastled;
    private static final int[] CANDIDATE_MOVE_OFFSETS = { -9, -8, -7, -1, 1, 7, 8, 9 };

    /**
     * Constructor that sets the position and the alliance of the king based on the passed params. It also sets the hasMoved field to false
     * @param alliance the team that the king is aligned with
     * @param position the position the king is in 
     * 
     */
    public King(final Alliance alliance, final int position) {
        super(PieceType.KING, alliance, position, false);
        this.hasCastled = false;
    }

    /**
     * Constructor that sets the position, alliance, and has moved fields of the king based on the passed params.
     * @param position the position the king is in 
     * @param alliance the team that the king is aligned with
     * @param hasMoved says whether the king has moved or not
     * @param hasCastled says whether the king has castled or not
     */
    public King(final Alliance alliance, final int position, final boolean hasMoved, final boolean hasCastled) {
        super(PieceType.KING, alliance, position, hasMoved);
        this.hasCastled = hasCastled;
    }

    /**
     * Calculates all legal moves that a king can make
     * @param board the current state of the board
     * @return a list of all legal moves a king can make
     */
    @Override
    public Collection<Move> calcLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int offset : CANDIDATE_MOVE_OFFSETS) {

            // edge cases
            if (!BoardUtils.isValidTileCoord(this.position) || isColumnAExclusion(this.position, offset)
                    || isColumnHExclusion(this.position, offset)) {
                continue;
            }
            final int destCoord = this.position + offset;
            // is valid tile
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
                }
            }
        }
        // add castle moves
        if (board.getWhitePlayer() != null && this.alliance.isWhite()) {
            Player whitePlayer = board.getWhitePlayer();
            legalMoves.addAll(whitePlayer.calcCastleMoves(whitePlayer.getLegalMoves(),
                    whitePlayer.getOpponent().getLegalMoves()));
        }
        else if (board.getBlackPlayer() != null && this.alliance.isBlack()) {
            Player blackPlayer = board.getBlackPlayer();
            legalMoves.addAll(blackPlayer.calcCastleMoves(blackPlayer.getLegalMoves(),
                    blackPlayer.getOpponent().getLegalMoves()));
        }
        return Collections.unmodifiableList(legalMoves);
    }

    /**
     * Converts the king to a string
     * @return the string representation of the king
     */
    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    /**
     * Says whether the king has castled or not
     * @return true if the king has castled and false otherwise
     */
    public boolean hasCastled() {
        return this.hasCastled;
    }
    
    /**
     * Checks if the king is on column A and if the given move offset 
     * would cause it to wrap around to the wrong position
     *
     * @param currPos The current position of the king on the board.
     * @param candidateOffset The move offset being considered.
     * @return true if the move is invalid due to the king being in column A, false otherwise.
     */
    private static boolean isColumnAExclusion(final int currPos, final int candidateOffset) {
        return BoardUtils.COLUMN_A[currPos] && (candidateOffset == -9 || candidateOffset == -1
                || candidateOffset == 7);
    }
    
    /**
     * Checks if the king is on column H and if the given move offset 
     * would cause it to wrap around to the wrong position
     *
     * @param currPos The current position of the king on the board.
     * @param candidateOffset The move offset being considered.
     * @return true if the move is invalid due to the king being in column B, false otherwise.
     */
    private static boolean isColumnHExclusion(final int currPos, final int candidateOffset) {
        return BoardUtils.COLUMN_H[currPos] && (candidateOffset == -7 || candidateOffset == 1
                || candidateOffset == 9);
    }
    
    /**
     * Creates a new king based on the move
     * @param move the move that was made
     * @return the new king created from the move
     */
    @Override
    public King movePiece(Move move) {
        return new King(move.getMovedPiece().getAlliance(), move.getDestCoord(), true, move.isCastlingMove() || this.hasCastled);
    }
    
}
