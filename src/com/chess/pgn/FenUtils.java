package com.chess.pgn;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;

public class FenUtils {
    /**
     * Utility class for fen string related constants and methods.
     */
    private FenUtils() {
        throw new RuntimeException("Not instantiable");
    }

    //TODO: complete this function for castling
    public static Board createBoardFromFen(final String fenString) {
        final String[] fenPartitions = fenString.trim().split(" ");
        final Builder builder = new Builder();
        final boolean whiteKingSideCastle = whiteKingSideCastle(fenPartitions[2]);
        final boolean whiteQueenSideCastle = whiteQueenSideCastle(fenPartitions[2]);
        final boolean blackKingSideCastle = blackKingSideCastle(fenPartitions[2]);
        final boolean blackQueenSideCastle = blackQueenSideCastle(fenPartitions[2]);
        final String gameConfiguration = fenPartitions[0];
        final char[] boardTiles = gameConfiguration.replaceAll("/", "")
                .replaceAll("8", "--------")
                .replaceAll("7", "-------")
                .replaceAll("6", "------")
                .replaceAll("5", "-----")
                .replaceAll("4", "----")
                .replaceAll("3", "---")
                .replaceAll("2", "--")
                .replaceAll("1", "-")
                .toCharArray();
        int i = 0;
        while (i < boardTiles.length) {
            switch (boardTiles[i]) {
                case 'r':
                    boolean hasMoved = !(i == 0 && !blackQueenSideCastle) && !(i == 7 && !blackKingSideCastle);
                    builder.setPiece(new Rook(Alliance.BLACK, i, hasMoved));
                    i++;
                    break;
                case 'n':
                    builder.setPiece(new Knight(Alliance.BLACK, i));
                    i++;
                    break;
                case 'b':
                    builder.setPiece(new Bishop(Alliance.BLACK, i));
                    i++;
                    break;
                case 'q':
                    builder.setPiece(new Queen(Alliance.BLACK, i));
                    i++;
                    break;
                case 'k':
                    hasMoved = !blackKingSideCastle && !blackQueenSideCastle;
                    builder.setPiece(new King(Alliance.BLACK, i, hasMoved, hasMoved));
                    i++;
                    break;
                case 'p':
                    builder.setPiece(new Pawn(Alliance.BLACK, i));
                    i++;
                    break;
                case 'R':
                    hasMoved = (!(i == 56 && !whiteQueenSideCastle) && !(i == 63 && !whiteKingSideCastle)
                            || (i != 56 && i != 63));
                    builder.setPiece(new Rook(Alliance.WHITE, i));
                    i++;
                    break;
                case 'N':
                    builder.setPiece(new Knight(Alliance.WHITE, i));
                    i++;
                    break;
                case 'B':
                    builder.setPiece(new Bishop(Alliance.WHITE, i));
                    i++;
                    break;
                case 'Q':
                    builder.setPiece(new Queen(Alliance.WHITE, i));
                    i++;
                    break;
                case 'K':
                    hasMoved = !blackKingSideCastle && !blackQueenSideCastle;
                    builder.setPiece(new King(Alliance.WHITE, i, hasMoved, hasMoved));
                    i++;
                    break;
                case 'P':
                    builder.setPiece(new Pawn(Alliance.WHITE, i));
                    i++;
                    break;
                case '-':
                    i++;
                    break;
                default:
                    throw new RuntimeException("Invalid FEN String " + gameConfiguration);
            }
        // set curr player
        }
        if (fenPartitions[1] == "w") {
            builder.setCurrPlayerAlliance(Alliance.WHITE);
        }
        else if (fenPartitions[1] == "b") {
            builder.setCurrPlayerAlliance(Alliance.BLACK);
        }
        else {
            throw new RuntimeException("Invalid FEN String " + gameConfiguration);
        }
        return builder.build();
    }

    /**
     * Says whether white can make a king side castle move or not based on the fen string
     * @param fenCastleString the castle section of the fen string
     * @return true if white can make king side castle move or false otherwise
     */
    private static boolean whiteKingSideCastle(final String fenCastleString) {
        return fenCastleString.contains("K");
    }


    /**
     * Says whether white can make a queen side castle move or not based on the fen string
     * @param fenCastleString the castle section of the fen string
     * @return true if white can make queen side castle move or false otherwise
     */
    private static boolean whiteQueenSideCastle(final String fenCastleString) {
        return fenCastleString.contains("Q");
    }

    /**
     * Says whether black can make a king side castle move or not based on the fen string
     * @param fenCastleString the castle section of the fen string
     * @return true if black can make king side castle move or false otherwise
     */
    private static boolean blackKingSideCastle(final String fenCastleString) {
        return fenCastleString.contains("k");
    }

    /**
     * Says whether black can make a queen side castle move or not based on the fen string
     * @param fenCastleString the castle section of the fen string
     * @return true if black can make queen side castle move or false otherwise
     */
    private static boolean blackQueenSideCastle(final String fenCastleString) {
        return fenCastleString.contains("q");
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
