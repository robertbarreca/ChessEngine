package com.chess.engine;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

public enum Alliance {
    WHITE {
        /**
         * Gets the number that corresponds to the direction that pawns move in
         * @return the number that corresponds to the direction that pawns move in
         */
        @Override
        public int getPawnDirection() {
            return -1;
        }

        /**
         * Gets the number that corresponds to the opposite direction thatpawns move in
         * @return the number that corresponds to the oppposite direction that pawns move in
         */
        @Override
        public int getOppositePawnDirection() {
            return 1;
        }
        
        /**
         * Says whether the piece is white or not
         * @return true since the piece is white
         */
        @Override
        public boolean isWhite() {
            return true;
        }

        /**
         * Says whether the piece is black or not
         * @return false since the piece is white
         */
        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return whitePlayer;
        }

        /**
         * Given a position says whether the passed tile is used for pawn promotion for the white player
         * @param position the position of the tile we are checking
         * @return true if it is used for pawn promotion and false otherwise
         */
        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardUtils.RANK_8[position];
        }
    },    
    BLACK {
        /**
         * Gets the number that corresponds to the direction that pawns move in
         * @return the number that corresponds to the direction that pawns move in
         */
        @Override
        public int getPawnDirection() {
            return 1;
        }

        /**
         * Gets the number that corresponds to the opposite direction that pawns move in
         * @return the number that corresponds to the opposite direction that pawns move in
         */
        @Override
        public int getOppositePawnDirection() {
            return -1;
        }

        /**
         * Says whether the piece is white or not
         * @return false since the piece is black
         */
        @Override
        public boolean isWhite() {
            return false;
        }

        /**
         * Says whether the piece is black or not
         * @return true since the piece is black
         */
        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public  Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return blackPlayer;
        }

        /**
         * Given a position says whether the passed tile is used for pawn promotion for the black player
         * @param position the position of the tile we are checking
         * @return true if it is used for pawn promotion and false otherwise
         */
        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardUtils.RANK_1[position];
        }
    };   
    
    public abstract int getPawnDirection();
    public abstract int getOppositePawnDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
    public abstract boolean isPawnPromotionSquare(int position);
    
}
