package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

/**
 * This class represents a chess board
 */
public class Board {

    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currPlayer;
    private final Pawn enPassantPawn;



    /**
     * Constructor that sets up the chess board
     * @param builder
     */
    private Board(final Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calcActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calcActivePieces(this.gameBoard, Alliance.BLACK);

        this.enPassantPawn = builder.enPassantPawn;

        final Collection<Move> whiteMoves = calcMoves(this.whitePieces);
        final Collection<Move> blackMoves = calcMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteMoves, blackMoves);
        this.blackPlayer = new BlackPlayer(this, whiteMoves, blackMoves);
        this.currPlayer = builder.currPlayerAlliance.choosePlayer(this.whitePlayer, this.blackPlayer);
    }
    
    /**
     * Gets all white pieces currently on the board
     * @return all white pieces currently on the board
     */
    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    /**
     * Gets all black pieces currently on the board
     * @return all black pieces currently on the board
     */
    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    /**
     * Gets all pieces currently on the board
     * @return all pieces currently on the board
     */
    public Collection<Piece> getAllPieces() {
        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(this.whitePieces);
        allPieces.addAll(this.blackPieces);
        return Collections.unmodifiableList(allPieces);
    }

    /**
     * gets the white player 
     * @return the white player 
     */
    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    /**
     * gets the black player
     * @return the black player
     */
    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    /**
     * gets the current player
     * @return the current player
     */
    public Player getCurrPlayer() {
        return this.currPlayer;
    }

    /**
     * Gets the en passant pawn on the board if it exists
     * @return the en passant pawn if it exists otherwise null
     */
    public Pawn getEnPassantPawn() {
        return this.enPassantPawn;
    }

    /**
     * Finds all legal moves for a collection of pieces
     * @param pieces the pieces we are finding legal moves for
     * @return all legal moves for all the pieces
     */
    public Collection<Move> calcMoves(final Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece p: pieces){
            legalMoves.addAll(p.calcLegalMoves(this));
        }
        return legalMoves;
    }
        
    /**
     * Gets all pieces on the board that match the passed alliance
     * @param gameBoard The board we are using to find the pieces
     * @param alliance the type of pieces we are looking for
     * @return a list of all the active pieces that match the alliance
     */
    private static Collection<Piece> calcActivePieces(final List<Tile> gameBoard, final Alliance alliance) {
        final List<Piece> activePieces = new ArrayList<>();
        for (final Tile tile : gameBoard) {
            if (tile.isOccupied()) {
                Piece piece = tile.getPiece();
                if (piece.getAlliance() == alliance) {
                    activePieces.add(piece);
                }
            }
        }
        return Collections.unmodifiableList(activePieces);
    }
    
    /**
     * Converts the gameboard to a string representation
     * @return the string representation of the gameboard
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = this.gameBoard.get(i).toString();
                builder.append(String.format("%3s", tileText));
                if ((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
                    builder.append("\n");
                }
            }
            return builder.toString();
        }
            
    /**
     * Creates a chess board using the provided builder configuration.
     * 
     * @param builder the builder containing the board configuration
     * @return a list of tiles representing the game board
     */
    private static List<Tile> createGameBoard(final Builder builder) {
        final List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles.add(Tile.createTile(i, builder.boardConfig.get(i)));
        }
        return Collections.unmodifiableList(tiles);
    }

    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        // black layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
        // white Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Pawn(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        //white to move
        builder.setCurrPlayerAlliance(Alliance.WHITE);
        //build the board
        return builder.build();
    }

    /**
     * Gets all moves on the board
     * @return all legal moves on the board
     */
    public Iterable<Move> getAllLegalMoves() {
        List<Move> allLegalMoves = new ArrayList<>();
        allLegalMoves.addAll(this.whitePlayer.getLegalMoves());
        allLegalMoves.addAll(this.blackPlayer.getLegalMoves());
        return Collections.unmodifiableList(allLegalMoves);
    }

    /**
     * Gets the tile associated with the passed coordinate
     * @param coord the coordinate on the chess board
     * @return the tile associated with the passed coordinate
     */
    public Tile getTile(final int coord) {
        return gameBoard.get(coord);
    }


    /**
     * Builder class for constructing a Board instance.
     */
    public static class Builder {
        Map<Integer, Piece> boardConfig;
        Alliance currPlayerAlliance;
        Pawn enPassantPawn;
        
        public Builder() {
            this.boardConfig = new HashMap<>();
        }
        
        /**
         * Sets a piece on the board.
         * 
         * @param piece the piece to place on the board
         * @return the updated Builder instance
         */
        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPosition(), piece);
            return this;
        }

        /**
         * Sets the current player.
         * 
         * @param currPlayer the alliance representing the current player
         * @return the updated Builder instance
         */
        public Builder setCurrPlayerAlliance(final Alliance currPlayerAlliance) {
            this.currPlayerAlliance = currPlayerAlliance;
            return this;
        }

        /**
         * Builds and returns a Board instance.
         * 
         * @return a new Board instance configured by this Builder
         */
        public Board build() {
            return new Board(this);
        }

        public void setEnPassantPawn(Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
        }
    }
}
