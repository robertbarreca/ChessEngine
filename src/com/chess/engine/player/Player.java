package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveStatus;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

/**
 * This abstract class represents one of the players in the current game
 */
public abstract class Player {
    protected final Board board;
    protected final King king;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    /**
     * Constructor that creates a player
     * @param board the board being used to play the game
     * @param playerLegals the possible moves this player can make
     * @param opponentMoves the possible moves the other player can make
     */
    Player(final Board board, final Collection<Move> playerLegals, final Collection<Move> opponentMoves) {
        this.board = board;
        this.king = establishKing();
        this.isInCheck = !Player.calculateAttacksOnTile(this.king.getPosition(), opponentMoves).isEmpty();
        playerLegals.addAll(calcCastleMoves(playerLegals, opponentMoves));
        this.legalMoves = Collections.unmodifiableCollection(playerLegals);        
    }

    /**
     * Gets the current player's king
     * @return the current player's king
     */
    public King getKing() {
        return this.king;
    }

    /**
     * Says whether the king-side castle is capable for this player
     * @return true if the king-side castle is capable and false otherwise
     */
    public boolean isKingSideCastleCapable() {
        return true;
    }
    
    /**
     * Says whether the queen-side castle is capable for this player
     * @return true if the queen-side castle is capable and false otherwise
     */
    public boolean isQueenSideCastleCapable() {
        return true;
    }

    /**
     * get's all legal moves a player can make
     * @return all legal moves a player can make
     */
    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }
        
    /**
     * Finds all opponent moves that are attacking one tile
     * @param position the tile we are checking 
     * @param opponentMoves All moves an opponent can make
     * @return all moves that attack the tile at the passed position
     */
    protected static Collection<Move> calculateAttacksOnTile(int position,
            Collection<Move> opponentMoves) {
        final List<Move> attackMoves = new ArrayList<>();
        for (Move move : opponentMoves) {
            if (move.getDestCoord() == position) {
                attackMoves.add(move);
            }
        }
        return Collections.unmodifiableList(attackMoves);
    }
        
    /**
     * This method ensures that a king is on the board at all times
     * @throws a RuntimeException if no king is on the board
     * @return the king present on the board if there is one
     */
    public King establishKing() {
        for (Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        // king is not on board
        throw new RuntimeException("King must be present on board!");
    }

    /**
     * Checks if the passed move is legal to make
     * @param move the move being checked
     * @return true if the move is legal and false otherwise
     */
    public boolean isMoveLegal(Move move) {
        return this.legalMoves.contains(move);
    }

    /**
     * Checks if the current player is in check
     * @return true if the current player is in check and false otherwise
     */
    public boolean isInCheck() {
        return this.isInCheck;
    }

    /**
     * Checks if the current player is in checkmate
     * @return true if the current player is in checkmate and false otherwise
     */
    public boolean isInCheckmate() {
        return this.isInCheck() && !hasEscapeMoves();
    }
        
    /**
     * Checks if the current player is in stalemate
     * @return true if the current player is in stalemate and false otherwise
     */
    public boolean isInStalemate() {
        return !this.isInCheck && !hasEscapeMoves();
    }

    /**
     * Checks if any of a player's moves will result in them being in check
     * @return true if they have any escape moves and false otherwise
     */
    protected boolean hasEscapeMoves() {
        for (final Move move : this.legalMoves) {
            final MoveTransition moveTransition = makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the current player has castled
     * @return true if the current player has castled and false otherwise
     */
    public boolean hasCastled() {
        return this.king.hasCastled();
    }

    public MoveTransition makeMove(final Move move) {
        // check if move is illegal
        if (!this.isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL);
        }
        final Board transitionBoard = move.execute();
        
        // check if move leaves player in check
        final Collection<Move> attacksOnKing = Player.calculateAttacksOnTile(
            transitionBoard.getCurrPlayer().getOpponent().getKing().getPosition(),
                transitionBoard.getCurrPlayer().getLegalMoves());
        if (!attacksOnKing.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        // move is legal
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    /**
     * Gets all the active pieces for the player
     * @return All active pieces for the current player
     */
    public abstract Collection<Piece> getActivePieces();

    /**
     * Gets the alliance of the player
     * @return the alliance of the player
     */
    public abstract Alliance getAlliance();

    /**
     * get the opposing player
     * @return the opposing player
     */
    public abstract Player getOpponent();

    public abstract Collection<Move> calcCastleMoves(Collection<Move>legalMoves,                 Collection<Move> opponentMoves);
}
