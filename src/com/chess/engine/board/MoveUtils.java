package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.player.Player;

/**
 * Utility class for move-related constants and methods in a chess engine.
 */
public class MoveUtils {
    /**
     * Takes in a collection of moves and filters out all the ones that are illegal to make
     * @param moves the collection of moves to be filtered
     * @param player the player that would be making these moves
     * @return an updated collection of only legal moves
     */
    public static Collection<Move> pruneIllegalMoves(Collection<Move> moves, final Player player) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Move move : moves) {
            final MoveTransition moveTransition = player.makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                legalMoves.add(move);
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }
}
