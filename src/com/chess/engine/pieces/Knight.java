package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

/**
 * This class represents a single knight on a chessboard
 */
public class Knight extends Piece{

    private final static int[] CANDIDATE_MOVE_OFFSETS = { -17, -15, -10, -6, 6, 10, 15, 17 };

    /**
     * Constructor that sets the position and the alliance of the knight
     * @param position the position the knight is in 
     * @param alliance the team that the knight is aligned with
     */
    Knight(final int position, final Alliance alliance) {
        super(position, alliance);
    }

    /**
     * Calculates all legal moves that a piece can make
     * @param board the current state of the board
     * @return a list of all legal moves a piece can make
     */
    @Override
    public List<Move> calcLegalMoves(Board board) {
        int destCoord;
        final List<Move> legalMoves = new ArrayList<>();

        // find all possible destinations
        for (final int offset : CANDIDATE_MOVE_OFFSETS) {
            destCoord = this.position + offset;
            // tile coordinate is valid
            if (true/*valid tile coord */) {
                final Tile destTile = board.getTile(destCoord);
                // tile is unoccupied
                if (!destTile.isOccupied()) {
                    legalMoves.add(new Move());
                }
                // tile is occupied 
                else {
                    // check if piece is capturable
                    final Piece pieceAtDest = destTile.getPiece();
                    final Alliance allianceAtDest = pieceAtDest.getAlliance();
                    // enemy piece on destination tile
                    if (this.alliance != allianceAtDest) {
                        legalMoves.add(new Move());
                    }
                }
            }

        }
        return ImmutableList.copyOf(legalMoves);
    }
    
}
