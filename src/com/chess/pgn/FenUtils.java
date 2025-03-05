package com.chess.pgn;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Pawn;

public class FenUtils {
    /**
     * Utility class for fen string related constants and methods.
     */
    private FenUtils() {
        throw new RuntimeException("Not instantiable");
    }

    public static Board createBoardFromFen(final String fenString) {
        return null;
    }
    
    /**
     * Given a board produces the corresponding fen string
     * @param board the board used to produce a fen string
     * @return the fen string corresponding to the board
     */
    public static String writeFenFromBoard(final Board board) {
        return calcBoardText(board) + " " +
            calcCurrPlayerText(board) + " " + 
            calcCastleText(board) + " " +
            calcEnPassantText(board) + " " + 
            "0 1";
        }
    
    /**
     * Calculates the board layout portion of the FEN string.
     *
     * @param board the board to extract the piece positions from
     * @return the FEN board representation
     */
    private static String calcBoardText(final Board board) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = board.getTile(i).toString();
            builder.append(tileText);
        }
        
        builder.insert(8, "/");
        builder.insert(17, "/");
        builder.insert(26, "/");
        builder.insert(35, "/");
        builder.insert(44, "/");
        builder.insert(53, "/");
        builder.insert(62, "/");

        return builder.toString()
            .replaceAll("--------", "8")
            .replaceAll("-------", "7")
            .replaceAll("------", "6")
            .replaceAll("-----", "5")
            .replaceAll("----", "4")
            .replaceAll("---", "3")
            .replaceAll("--", "2")
            .replaceAll("-", "1");
    }

    /**
     * Determines the current player's turn for the FEN string.
     *
     * @param board the board to check for the current player
     * @return "w" if it's White's turn, "b" if it's Black's turn
     */
    private static String calcCurrPlayerText(final Board board) {
        return board.getCurrPlayer().getAlliance().toString().substring(0, 1).toLowerCase();
    }

    /**
     * Determines the castling availability notation for the FEN string.
     *
     * @param board the board to check for castling rights
     * @return the FEN castling notation 
     */
    private static String calcCastleText(final Board board) {
        final StringBuilder builder = new StringBuilder();
        if (board.getWhitePlayer().isKingSideCastleCapable()) {
            builder.append("K");
        }
        if (board.getWhitePlayer().isQueenSideCastleCapable()) {
            builder.append("Q");
        }
        if (board.getBlackPlayer().isKingSideCastleCapable()) {
            builder.append("k");
        }
        if (board.getBlackPlayer().isQueenSideCastleCapable()) {
            builder.append("q");
        }
        final String res = builder.toString();
        return res.isEmpty() ? " - ": res;
    }


    /**
     * Determines the en passant target square for the FEN string.
     *
     * @param board the board to check for an en passant target
     * @return the FEN en passant notation 
     */
    private static String calcEnPassantText(final Board board) {
        final Pawn enPassantPawn = board.getEnPassantPawn();
        if (enPassantPawn != null) {
            return BoardUtils.getPosFromCoord(
                    enPassantPawn.getPosition() + (8 * enPassantPawn.getAlliance().getOppositePawnDirection()));
        }
        return "-";
    }

}
