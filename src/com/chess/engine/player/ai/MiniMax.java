package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveTransition;

public class MiniMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;
    private final int searchDepth;

    /**
     * Constructor for the minimax class
     */
    public MiniMax(final int searchDepth) {
        this.boardEvaluator = new StandardBoardEvauluator();
        this.searchDepth = searchDepth;
    }

    /**
     * Converts the minimax object to it's string representation
     * @return the string representation of this minimax instance
     */
    @Override
    public String toString() {
        return "MiniMax";
    }

    /**
     * Executes the MiniMax algorithm to find the best possible move for the current player.
     * 
     * @param board The current board being analyzed.
     * @param depth The maximum search depth for the algorithm.
     * @return The best move determined by the MiniMax algorithm.
     */
    @Override
    public Move execute(Board board) {
        final long startTime = System.currentTimeMillis();
        
        Move bestMove = null;
        int maxVal = Integer.MIN_VALUE;
        int minVal = Integer.MAX_VALUE;
        int currVal;

        // Iterate through all legal moves for the current player
        for (final Move move : board.getCurrPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.getCurrPlayer().makeMove(move);

            if (moveTransition.getMoveStatus().isDone()) {
                currVal = board.getCurrPlayer().getAlliance().isWhite()
                        ? min(moveTransition.getUpdatedBoard(), this.searchDepth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE)
                        : max(moveTransition.getUpdatedBoard(), this.searchDepth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);

                // if player is white maximize the score
                if (board.getCurrPlayer().getAlliance().isWhite() && currVal >= maxVal) {
                    maxVal = currVal;
                    bestMove = move;
                }
                // if player is black minimize the score
                else if (board.getCurrPlayer().getAlliance().isBlack() && currVal <= minVal) {
                    minVal = currVal;
                    bestMove = move;
                }
            }
        }
        final long executionTime = System.currentTimeMillis() - startTime;
        System.out.println(executionTime);
        return bestMove;
    }

     /**
     * Computes the minimum possible score for the given board state by evaluating 
     * all possible opponent moves. 
     * 
     * @param board The current board being evaluated.
     * @param depth The remaining search depth.
     * @return The minimum score achievable for the current player.
     */
    public int min(final Board board, final int depth, int alpha, int beta) {
        if (depth == 0 || isGameOver(board)) {
            return this.boardEvaluator.evaluate(board, depth);
        }

        int minVal = Integer.MAX_VALUE;
        for (final Move move : board.getCurrPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.getCurrPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currVal = max(moveTransition.getUpdatedBoard(), depth - 1, alpha, beta);
                minVal = Math.min(minVal, currVal);
                beta = Math.min(beta, currVal);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return minVal;
    }
    
     /**
     * Computes the maximum possible score for the given board state by evaluating 
     * all possible opponent moves. 
     * 
     * @param board The current board being evaluated.
     * @param depth The remaining search depth.
     * @return The maximum score achievable for the current player.
     */
     public int max(final Board board, final int depth, int alpha, int beta) {
         if (depth == 0 || isGameOver(board)) {
            return this.boardEvaluator.evaluate(board, depth);
        }

        int maxVal = Integer.MIN_VALUE;
        for (final Move move : board.getCurrPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.getCurrPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currVal = min(moveTransition.getUpdatedBoard(), depth - 1, alpha, beta);
                maxVal = Math.max(currVal, maxVal);
                alpha = Math.max(alpha, currVal);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return maxVal;
    }
    
     /**
      * Determines if the passed board's game has finished
      * @param board the board being evaulted
      * @return true if the game is over and false otherwise
      */
     private static boolean isGameOver(final Board board) {
         return board.getCurrPlayer().isInCheckmate() || board.getCurrPlayer().isInStalemate();
     }
    
}
