package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;

public final class StandardBoardEvauluator implements BoardEvaluator {
    private static final int CHECK_BONUS = 50;
    private static final int CHECKMATE_BONUS = 10000;
    private static final int DEPTH_BONUS = 100;
    private static final int CASTLE_BONUS = 60;

     /**
     * Evaluates the board position by comparing the material strength of White and Black.
     * The score is calculated as:
     *      (White's position score) - (Black's position score)
     *
     * @param board The current board state.
     * @param depth The remaining search depth 
     * @return A numerical score representing the evaluation of the board.
     */
    @Override
    public int evaluate(final Board board, final int depth) {
        return scorePlayer(board, board.getWhitePlayer(), depth) - 
                scorePlayer(board, board.getBlackPlayer(), depth);
    }

    /**
     * Computes the total score for a given player based on the value of their pieces.
     * 
     * @param board The current board state.
     * @param player The player whose board position is being evaluated.
     * @param depth The remaining search depth
     * @return The difference in score of the white vs black player
     */
    private int scorePlayer(final Board board, final Player player, final int depth) {
        return pieceValueScore(player) +
                mobilityScore(player) +
                check(player) +
                checkmate(player, depth) + 
                castled(player);
        }
                
        
    /**
     * Calculates the total material value of a player's active pieces.
     *
     * @param player The player whose piece values are being summed.
     * @return The total material value of all the player's active pieces.
     */
    private static int pieceValueScore(final Player player) {
        int totalPieceScore = 0;
        for (final Piece piece : player.getActivePieces()) {
            totalPieceScore += piece.getValue();
        }
        return totalPieceScore;
    }
    
    /**
     * Scores the passed player based on how many moves they have
     * @param player the player being scored
     * @return the number of legal moves a player has
     */
    private static int mobilityScore(final Player player) {
        return player.getLegalMoves().size();
    }

    /**
     * Scores the player based on if they have their opponent in check
     * @param player the player being scored
     * @return a check bonus if their opponent is in check otherwise 0
     */
    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }

    /**
     * Scores the player based on if they their opponent is checkmated
     * @param player the player being scored
     * @return a checkmate bonus if their opponent is checkmated otherwise 0
     */
    private int checkmate(final Player player, final int depth) {
        return player.getOpponent().isInCheckmate() ? CHECKMATE_BONUS * depthBonus(depth) : 0;    
    }

    /**
     * Computes a bonus multiplier based on the search depth. The more moves in means a smaller bonus
     *
     * @param depth The current depth of the search.
     * @return A bonus multiplier, where depth 0 returns 1, and deeper searches return DEPTH_BONUS * depth.
     */
    private static int depthBonus(final int depth) {
        return depth == 0 ? 1 : DEPTH_BONUS * depth;
    }
    
    /**
     * Scores the player based on if they have castled or not
     * @param player the player being scored
     * @return a castle bonus if they have castled otherwise 0
     */
    private static int castled(final Player player) {
        return player.hasCastled() ? CASTLE_BONUS : 0;
    }

}
