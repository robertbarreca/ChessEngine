package com.chess.engine.board;

import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

/**
 * This class represents a move on a chessboard
 */
public abstract class Move {

    protected final Board board;
    protected final Piece movedPiece;
    protected final int destCoord;
    protected final boolean isFirstMove;

    public static final Move NULL_MOVE = new NullMove();
    /**
     * Constructor for a move
     * @param board the board the move is being played on
     * @param movedPiece the piece being moved
     * @param destCoord the destination of the piece
     */
    private Move(final Board board, final Piece movedPiece, final int destCoord) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destCoord = destCoord;
        this.isFirstMove = !movedPiece.hasMoved();
    }

    /**
     * Convenience constructor for a null move
     * @param board the board the move is being played on
     * @param destCoord the destination of the piece
     */
    private Move(final Board board, final int destCoord) {
        this.board = board;
        this.destCoord = destCoord;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    /**
     * Computes the hash of a piece
     * @return the hascode of a piece
     */
    @Override
    public int hashCode() {
        int prime = 31;
        int res = 1;

        res = prime * res + this.destCoord;
        res = prime * res + this.movedPiece.hashCode();
        res = prime * res + this.movedPiece.getPosition();
        return res;
    }

    /**
     * Checks whether to passed object is equivlant to the current piece
     * @param other the object we are comparing to
     * @return true if they are both moves that have the same moved piece and destination and false otherwise
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Move)) {
            return false;
        }

        final Move otherMove = (Move) other;
        return this.getStartingCoord() == otherMove.getStartingCoord() &&
                this.getDestCoord() == otherMove.getDestCoord() &&
                this.getMovedPiece().equals(otherMove.getMovedPiece());
    }
    /**
     * gets the position the piece starts on 
     * @return the position the piece starts on 
     */ 
    public int getStartingCoord() {
        return this.movedPiece.getPosition();
    }
    /**
     * gets the position of the piece's destination
     * @return the position of the piece's destination
     */
    public int getDestCoord() {
        return this.destCoord;
    }

    /**
     * gets the moved piece
     * @return the moved piece
     */
    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    /**
     * Checks if the move is an attacking move
     * @return true if it is an attacking move and false otherwise
     */
    public boolean isAttackingMove() {
        return false;
    }
    
    /**
     * Checks if move is a castling move
     * @return true if its is a castling move and false otherwise
     */
    public boolean isCastlingMove() {
        return false;
    }

    /**
     * gets the captured piece
     * @return the captured piece if the move was attacking otherwise null
     */
    public Piece getCapturedPiece() {
        return null;
    }

    /**
     * Converts a move to it's string representation
     * @return the String representation of a move
     */
    @Override
    public String toString() {
        return this.movedPiece.getPieceType().toString() + BoardUtils.getPosFromCoord(this.destCoord);
    }

    /**
     * Gets the board the move is made on 
     * @return the board the move is made on
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * This method rebuilds the board based on the move made
     * @return the new board based on the move
     */
    public Board execute() {
        final Board.Builder builder = new Board.Builder();
        // set curr player's pieces except for moved piece
        for (final Piece piece : this.board.getCurrPlayer().getActivePieces()) {
            if (!piece.equals(this.movedPiece)) {
                builder.setPiece(piece);
            }
        }
        // set opponents pieces
        for (final Piece piece : this.board.getCurrPlayer().getOpponent().getActivePieces()) {
            if (!piece.equals(this.movedPiece)) {
                builder.setPiece(piece);
            }
        }
        // set movedPiece
        builder.setPiece(this.movedPiece.movePiece(this));
        // switch active player
        builder.setCurrPlayerAlliance(this.board.getCurrPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    /**
     * This class represents an invalid move on a chessboard
     */
    public static final class NullMove extends Move {
        /**
         * Constructor for a null move
         */
        public NullMove() {
            super(null, -1);
        }

        /**
         * get's the starting coordinate for a null move
         * @return -1 since there is no starting position
         */
        @Override
        public int getStartingCoord() {
            return -1;
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Cannot execute a null move!");
        }
    }

    /**
    * This class represents a non-attacking move on a chessboard
    */
    public static final class PassiveMove extends Move {
        /**
         * Constructor for a move
         * @param board the board the move is being played on
         * @param movedPiece the piece being moved
         * @param destCoord the destination of the piece
         */
        public PassiveMove(final Board board, final Piece movedPiece, final int destCoord) {
            super(board, movedPiece, destCoord);
        }


        /**
         * Checks whether to passed object is equivlant to the current passive move
         * @param other the object we are comparing to
         * @return true if they are both moves that have the same moved piece and destination and false otherwise
         */
        @Override
        public boolean equals(Object other) {
            return this == other || (other instanceof PassiveMove && super.equals(other));
        }
    }

    /**
     * This class represents a attack move on a chessboard
     */
    public static class AttackMove extends Move {
        final Piece capturedPiece;

        /**
         * Constructor for a move
         * @param board the board the move is being played on
         * @param movedPiece the piece being moved
         * @param destCoord the destination of the piece
         * @param capturedPiece the piece being attacked 
         */
        public AttackMove(final Board board, final Piece movedPiece, final int destCoord, final Piece capturedPiece) {
            super(board, movedPiece, destCoord);
            this.capturedPiece = capturedPiece;
        }

        /**
         * Checks if the move is an attacking move
         * @return true if it is an attacking move and false otherwise
         */
        public boolean isAttackingMove() {
            return true;
        }

        /**
         * gets the captured piece
         * @return the captured piece if the move was attacking otherwise null
         */
        public Piece getCapturedPiece() {
            return this.capturedPiece;
        }

        /**
         * Converts the attack move to it's string representation
         * @return the string representation of the attack move
         */
        @Override
        public String toString() {
            return this.movedPiece.getPieceType().toString() + "X" + BoardUtils.getPosFromCoord(destCoord);
        }

        /**
         * Computes the hash of a piece
         * @return the hascode of a piece
         */
        @Override
        public int hashCode() {
            return this.capturedPiece.hashCode() + super.hashCode();
        }

        /**
         * Checks whether to passed object is equivlant to the current piece
         * @param other the object we are comparing to
         * @return true if they are both moves that have the same moved piece and destination and false otherwise
         */
        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AttackMove)) {
                return false;
            }

            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) &&
                    this.getCapturedPiece ().equals(otherAttackMove.getCapturedPiece());
        }

        /**
         * This method rebuilds the board based on the move made
         * @return the new board based on the move
         */
        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            // set curr player's pieces except for moved piece
            for (final Piece piece : this.board.getCurrPlayer().getActivePieces()) {
                if (!piece.equals(this.movedPiece)) {
                    builder.setPiece(piece);
                }
            }
            // set opponents pieces
            for (final Piece piece : this.board.getCurrPlayer().getOpponent().getActivePieces()) {
                if (!piece.equals(this.capturedPiece)) {
                    builder.setPiece(piece);
                }
            }
            // set movedPiece
            builder.setPiece(this.movedPiece.movePiece(this));
            // switch active player
            builder.setCurrPlayerAlliance(this.board.getCurrPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    /**
     * This class represents a pawn move on a chessboard
     */
    public static class PawnMove extends Move {
            /**
             * Constructor for a pawn move
             * @param board the board the move is being played on
             * @param movedPiece the pawn being moved
             * @param destCoord the destination of the pawn
             */
            public PawnMove(final Board board, final Piece movedPiece, final int destCoord) {
                super(board, movedPiece, destCoord);
            }
    
            /**
             * Get's the string representation of a pawn move
             * @return the string representation of a pawn move
             */
            @Override
            public String toString() {
                return BoardUtils.getPosFromCoord(this.destCoord);
            }

            /**
             * Checks if the passed object is equal to this pawn move
             * @return true if they are equivlant and false otherwise
             */
            @Override
            public boolean equals(final Object other){
                return this == other || (other instanceof PawnMove && super.equals(other));
            }
        }
        
    
        public static class PawnPromotion extends Move {
            final Move decoratedMove;
            final Pawn promotedPawn;


            public PawnPromotion(final Move decoratedMove) {
                super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestCoord());
                this.decoratedMove = decoratedMove;
                this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
            }

            /**
             * Gets the hashcode of the pawn promotion move
             * @return the hashcode of the pawn promotion move
             */
            @Override
            public int hashCode() {
                return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
            }

            /**
             * Says if an object is equal to this pawn promotion move
             * @param other the object we are comparing to 
             * @return true if they are equivlant and false otherwise
             */
            @Override
            public boolean equals(Object other) {
                return this == other || (other instanceof PawnPromotion && super.equals(other));
            }

            @Override 
            public Board execute() {
                final Board pawnMovedBoard = this.decoratedMove.execute();
                final Board.Builder builder = new Board.Builder();
                for (final Piece piece : pawnMovedBoard.getCurrPlayer().getActivePieces()) {
                    if (!this.promotedPawn.equals(piece)) {
                        builder.setPiece(piece);
                    }
                }

                for (final Piece piece : pawnMovedBoard.getCurrPlayer().getOpponent().getActivePieces()) {
                    builder.setPiece(piece);
                }

                builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
                builder.setCurrPlayerAlliance(pawnMovedBoard.getCurrPlayer().getAlliance());
                return builder.build();
            }

            /**
             * Says whether the move is attacking or not
             * @return true if it is an attacking move and false otherwise
             */
            @Override
            public boolean isAttackingMove() {
                return this.decoratedMove.isAttackingMove();
            }

            /**
             * Gets the piece captured from the move made
             * @return the piece captured if one exists otherwise null
             */
            @Override
            public Piece getCapturedPiece() {
                return this.decoratedMove.getCapturedPiece();
            }

            /**
             * Gets the string representation of a pawn promotion move
             * @return the string representation of a pawn promotion move
             */
            @Override
            public String toString() {
                return BoardUtils.getPosFromCoord(this.decoratedMove.getStartingCoord()).substring(0, 2) + "=" + this.promotedPawn.getPromotionPiece().getPieceType().toString(); 
            }

             
        }

        /**
         * This class represents a pawn jump move on a chessboard
         */
        public static final class PawnJumpMove extends PawnMove {
        /**
         * Constructor for a move
         * @param board the board the move is being played on
         * @param movedPiece the pawn being moved
         * @param destCoord the destination of the pawn
         */
        public PawnJumpMove(final Board board, final Piece movedPiece, final int destCoord) {
            super(board, movedPiece, destCoord);
        }

        /**
        * This method rebuilds the board based on the pawn jump made
        * @return the new board based on the pawn jump
        */
        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            // set curr player's pieces except for moved piece
            for (final Piece piece : this.board.getCurrPlayer().getActivePieces()) {
                if (!piece.equals(this.movedPiece)) {
                    builder.setPiece(piece);
                }
            }
            // set opponents pieces
            for (final Piece piece : this.board.getCurrPlayer().getOpponent().getActivePieces()) {
                if (!piece.equals(this.movedPiece)) {
                    builder.setPiece(piece);
                }
            }
            // set moved pawn
            Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setCurrPlayerAlliance(board.getCurrPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    /**
     * This class represents a pawn attack move on a chessboard
     */
    public static class PawnAttackMove extends AttackMove {
        /**
         * Constructor for a move
         * @param board the board the move is being played on
         * @param movedPiece the pawn being moved
         * @param destCoord the destination of the pawn
         * @param capturedPiece the piece the pawn is attacking 
         */
        public PawnAttackMove(final Board board, final Piece movedPiece, final int destCoord,
                final Piece capturedPiece) {
            super(board, movedPiece, destCoord, capturedPiece);
        }

        /**
         * Converts the attack move to it's string representation
         * @return the string representation of the attack move
         */
        @Override
        public String toString() {
            return BoardUtils.getPosFromCoord(this.movedPiece.getPosition()).substring(0, 1) + "X"
                    + BoardUtils.getPosFromCoord(destCoord);
        }
        

        /**
         * Checks if the passed object to this pawn attack move
         * @return true if they are equivlant and false otherwise
         */
        @Override
        public boolean equals(final Object other){
            return this == other || (other instanceof PawnAttackMove && super.equals(other));
        }
    }

    /**
     * This class represents an en Passant move on a chessboard
     */
    public static class PawnEnPassantAttackMove extends PawnAttackMove {
        /**
         * Constructor for an en Passant move
         * @param board the board the move is being played on
         * @param movedPiece the pawn being moved
         * @param destCoord the destination of the pawn
         * @param capturedPiece the piece the pawn is attacking
         */
        public PawnEnPassantAttackMove(final Board board, final Piece movedPiece, final int destCoord,
                final Piece capturedPiece) {
            super(board, movedPiece, destCoord, capturedPiece);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || (other instanceof PawnEnPassantAttackMove && super.equals(other));
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            // set curr player's pieces except for moved piece
            for (final Piece piece : this.board.getCurrPlayer().getActivePieces()) {
                if (!piece.equals(this.movedPiece)) {
                    builder.setPiece(piece);
                }
            }
            // set opponents pieces
            for (final Piece piece : this.board.getCurrPlayer().getOpponent().getActivePieces()) {
                if (!piece.equals(this.capturedPiece)) {
                    builder.setPiece(piece);
                }
            }
            // set movedPiece
            builder.setPiece(this.movedPiece.movePiece(this));
            // switch active player
            builder.setCurrPlayerAlliance(this.board.getCurrPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }
    /**
     * This class represents a castle move on a chessboard
     */
    static abstract class CastleMove extends Move {
        protected final Rook castleRook;
        protected final int castleRookStartCoord;
        protected final int castleRookDestCoord;
        
        /**
         * Constructor for a castle move
         * @param board the board the move is being played on
         * @param movedPiece the piece being moved
         * @param destCoord the destination of the piece
         * @param castleRook the rook the king castles with
         * @param castleRookStartCoord the starting position of the rook
         * @param castleRookDestCoord the ending position of the rook
         */
        public CastleMove(final Board board, final Piece movedPiece, final int destCoord, final Rook castleRook, final int castleRookStartCoord, final int castleRookDestCoord) {
            super(board, movedPiece, destCoord);
            this.castleRook = castleRook;
            this.castleRookStartCoord = castleRookStartCoord;
            this.castleRookDestCoord = castleRookDestCoord;
        }
        
        /**
         * Gets the rook the kings castles with
         * @return the rook the king castles with
         */
        public Rook getCastleRook() {
            return this.castleRook;
        }

        /**
         * Checks if the current move is a castling move
         * @return true
         */
        @Override
        public boolean isCastlingMove() {
            return true;
        }

        /**
         * This method rebuilds the board based on the castle move made
         * @return the new board based on the castle move
         */
        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            // set curr player's pieces except for moved piece
            for (final Piece piece : this.board.getCurrPlayer().getActivePieces()) {
                if (!piece.equals(this.movedPiece) && !piece.equals(this.castleRook)) {
                    builder.setPiece(piece);
                }
            }
            // set opponents pieces
            for (final Piece piece : this.board.getCurrPlayer().getOpponent().getActivePieces()) {
                if (!piece.equals(this.movedPiece)) {
                    builder.setPiece(piece);
                }
            }
            // set king and rook
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(
                    new Rook(this.castleRook.getAlliance(), this.castleRookDestCoord));
            // switch active player
            builder.setCurrPlayerAlliance(this.board.getCurrPlayer().getOpponent().getAlliance());
            return builder.build();
        }
        
        /**
         * Makes the hashcode for the castle move
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int res = super.hashCode();
            res = prime * res + this.castleRook.hashCode();
            res = prime * res + this.castleRookDestCoord;
            return res;
        }
        
        /**
         * Says whether this castle move is equal to the passed object
         * @return true if they are equivlant and false otherwise
         */
        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CastleMove)) {
                return false;
            }
            final CastleMove otherCastleMove = (CastleMove) other;
            return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
        }
    }

    /**
     * This class represents a attack move on a chessboard
     */
    public static class KingSideCastleMove extends CastleMove {
        /**
         * Constructor for a move
         * @param board the board the move is being played on
         * @param movedPiece the piece being moved
         * @param destCoord the destination of the piece
         * @param castleRook the rook the king castles with
         * @param castleRookStartCoord the starting position of the rook
         * @param castleRookDestCoord the ending position of the rook
         */
        public KingSideCastleMove(final Board board, final Piece movedPiece, final int destCoord, final Rook castleRook,
                final int castleRookStartCoord, final int castleRookDestCoord) {
            super(board, movedPiece, destCoord, castleRook, castleRookStartCoord, castleRookDestCoord);
        }
        
        /**
         * Converts the king side castle to it's string representation
         * @return the string representation of a king-side castle move
         */
        @Override
        public String toString() {
            return "O-O";
        }

        /**
         * Checks whether to passed object is equivlant to the current king side castle move
         * @param other the object we are comparing to
         * @return true if they are equivlant and false otherwise
         */
        @Override
        public boolean equals(Object other) {
            return this == other || (other instanceof KingSideCastleMove && super.equals(other));
        }
    }

    /**
     * This class represents a attack move on a chessboard
     */
    public static class QueenSideCastleMove extends CastleMove {
        /**
         * Constructor for a move
         * @param board the board the move is being played on
         * @param movedPiece the piece being moved
         * @param destCoord the destination of the piece
         * @param castleRook the rook the king castles with
         * @param castleRookStartCoord the starting position of the rook
         * @param castleRookDestCoord the ending position of the rook
         */
        public QueenSideCastleMove(final Board board, final Piece movedPiece, final int destCoord,
                final Rook castleRook, final int castleRookStartCoord, final int castleRookDestCoord) {
            super(board, movedPiece, destCoord, castleRook, castleRookStartCoord, castleRookDestCoord);
        }
        
        /**
         * Converts the queen side castle to it's string representation
         * @return the string representation of a queen-side castle move
         */
        @Override
        public String toString() {
            return "O-O-O";
        }

        /**
         * Checks whether to passed object is equivlant to the current queen side castle move
         * @param other the object we are comparing to
         * @return true if they are equivlant and false otherwise
         */
        @Override
        public boolean equals(Object other) {
            return this == other || (other instanceof QueenSideCastleMove && super.equals(other));
        }
    }

    public static class MoveFactory{

        private MoveFactory() {
            throw new RuntimeException("Not instantiable");
        }

        public static Move createMove(final Board board, final int currCoord, final int destCoord) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.getStartingCoord() == currCoord && move.getDestCoord() == destCoord) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}


