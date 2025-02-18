package com.chess.engine;

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
    };   
    
    public abstract int getPawnDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
    
}
