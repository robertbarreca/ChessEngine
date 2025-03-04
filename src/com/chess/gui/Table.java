package com.chess.gui;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;
import com.chess.engine.player.ai.MiniMax;
import com.chess.engine.player.ai.MoveStrategy;
import com.google.common.collect.Lists;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;


@SuppressWarnings("deprecation")
public class Table extends Observable {
    private final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel boardPanel;
    private Board chessBoard;
    private final MoveLog moveLog;
    private final GameSetup gameSetup;

    private Tile srcTile;
    private Tile destTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;

    private Move computerMove;

    private boolean highlightLegalMoves;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static String pieceImagesPath = "art/pieces/";

    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
    private boolean gameOverShown = false;
        
        private static final Table INSTANCE = new Table();
            
        /**
         * Constructor for the table class
         */
        private Table() {
            this.gameFrame = new JFrame("Chess Engine");
            this.gameFrame.setLayout(new BorderLayout());
            final JMenuBar tableMenuBar = createTableMenuBar();
            this.gameFrame.setJMenuBar(tableMenuBar);
            this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
            this.chessBoard = Board.createStandardBoard();
            // this.chessBoard = Board.createCustomBoard();
            this.gameHistoryPanel = new GameHistoryPanel();
            this.takenPiecesPanel = new TakenPiecesPanel();
            this.boardPanel = new BoardPanel();
            this.moveLog = new MoveLog();
            this.addObserver(new TableGameAIWatcher());
            this.gameSetup = new GameSetup(this.gameFrame, true);
            this.boardDirection = BoardDirection.NORMAL;
            this.highlightLegalMoves = true;
            this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
            this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
            this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
            this.gameFrame.setVisible(true);
        }
    
        public static Table get() {
            return INSTANCE;
        }
    
        public void show(){
            Table.get().getMoveLog().clear();
            Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
            Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
            Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
        }
    
        private GameSetup getGameSetup() {
            return this.gameSetup;
        }
    
        private Board getGameBoard() {
            return this.chessBoard; 
        }
    
            
        /**
         * Populates and creates the table's menu bar with various actions a user can do
         * @return the populated menu bar to be populated
         */
        private JMenuBar createTableMenuBar() {
            final JMenuBar tableMenuBar = new JMenuBar();
            tableMenuBar.add(createFileMenu());
            tableMenuBar.add(createPreferencesMenu());
            tableMenuBar.add(createOptionsMenu());
            return tableMenuBar;
        }
        
        /**
         * Creates the file file menu item for the table bar and gives it actions it can do
         * @return the file menu item
         */
        private JMenu createFileMenu() {
            final JMenu fileMenu = new JMenu("File");
            // create load pgn item
            final JMenuItem openPGN = new JMenuItem("Load PGN File");
            openPGN.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("open pgn file!");
                }
            });
    
            // create exit game item
            final JMenuItem exitMenuItem = new JMenuItem("Exit");
            exitMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
    
            // add all created items
            fileMenu.add(openPGN);
            fileMenu.add(exitMenuItem);
            return fileMenu;
        }
        
         /**
         * Creates the file preferences menu item for the table bar and gives it actions it can do
         * @return the preferences menu item
         */
         private JMenu createPreferencesMenu() {
             final JMenu preferencesMenu = new JMenu("Preferences");
             // create flip board menu item
             final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
             flipBoardMenuItem.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(final ActionEvent e) {
                     boardDirection = boardDirection.opposite();
                     boardPanel.drawBoard(chessBoard);
                 }
             });
    
             // create highlight legal moves menu item
             final JCheckBoxMenuItem legalMovesHighlighterCheckbox = new JCheckBoxMenuItem("Highlight legal moves", true);
    
             legalMovesHighlighterCheckbox.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(final ActionEvent e) {
                     highlightLegalMoves = legalMovesHighlighterCheckbox.isSelected();
                 }
             });
    
             // add created items
             preferencesMenu.add(flipBoardMenuItem);
             preferencesMenu.add(legalMovesHighlighterCheckbox);
             return preferencesMenu;
         }
        
        private JMenu createOptionsMenu() {
            final JMenu optionsMenu = new JMenu("Options");
    
            final JMenuItem setupGaMenuItem = new JMenuItem("Setup Game");
            setupGaMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Table.get().getGameSetup().promptUser();
                    Table.get().setupUpdate(Table.get().getGameSetup());
                }
            });
    
            optionsMenu.add(setupGaMenuItem);
            return optionsMenu;
        }
        
        private void setupUpdate(final GameSetup gameSetup) {
            setChanged();
            notifyObservers();
        }
    
        private static class TableGameAIWatcher implements Observer {
    
            @Override
            public void update(final Observable o, final Object arg) {
                if (Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().getCurrPlayer())
                        && !Table.get().getGameBoard().getCurrPlayer().isInCheckmate() &&
                        !Table.get().getGameBoard().getCurrPlayer().isInStalemate()) {
                    final AIThinkTank thinkTank = new AIThinkTank();
                    thinkTank.execute();
                }
    
                Table.renderGameOverPane();
            }
        }
                        
    public static void renderGameOverPane() {
        // only render once
        if (Table.get().gameOverShown) {
            return;
        }
        // player is in check mate
        if (Table.get().getGameBoard().getCurrPlayer().isInCheckmate()) {
            JOptionPane.showMessageDialog(Table.get().getBoardPanel(),
                    "Game Over: Player " + Table.get().getGameBoard().getCurrPlayer().getAlliance() + " is in checkmate!", "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            Table.get().setGameOverShown(true);
        }

        // player is in stalemate
        if (Table.get().getGameBoard().getCurrPlayer().isInStalemate()) {
            JOptionPane.showMessageDialog(Table.get().getBoardPanel(),
                    "Game Over: Player " + Table.get().getGameBoard().getCurrPlayer().getAlliance() + " is in stalemate!", "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            Table.get().setGameOverShown(true);
        }
    }

    private void setGameOverShown(final boolean b) {
        this.gameOverShown = b;
    }

    public void updateGameBoard(final Board board) {
        this.chessBoard = board;
    }

    public void updateComputerMove(final Move move) {
        this.computerMove = move;
    }

    private MoveLog getMoveLog() {
        return this.moveLog;
    }

    private GameHistoryPanel getGameHistoryPanel() {
        return this.gameHistoryPanel;
    }

    private TakenPiecesPanel getTakenPiecesPanel() {
        return this.takenPiecesPanel;
    }

    private BoardPanel getBoardPanel() {
        return this.boardPanel;
    }

    private void moveMadeUpdate(final PlayerType playerType) {
        setChanged();
        notifyObservers(playerType);
    }

    private static class AIThinkTank extends SwingWorker<Move, String>{
        private AIThinkTank() {
            
        }

        @Override
        protected Move doInBackground() throws Exception {
            final MoveStrategy miniMax = new MiniMax(Table.get().getGameSetup().getSearchDepth());
            final Move bestMove = miniMax.execute(Table.get().getGameBoard());

            return bestMove;
        }

        @Override
        public void done() {
            try {
                final Move bestMove = get();
                Table.get().updateComputerMove(bestMove);
                Table.get().updateGameBoard(Table.get().getGameBoard().getCurrPlayer().makeMove(bestMove).getUpdatedBoard());
                Table.get().getMoveLog().addMove(bestMove);
                Table.get().getGameHistoryPanel().redo(Table.get().getGameBoard(), Table.get().getMoveLog());
                Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
                Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
                Table.get().moveMadeUpdate(PlayerType.COMPUTER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Enum that represents the direction the board is facing
     */
    public enum BoardDirection {
        NORMAL {
            /**
             * Gets the the board's tile panels based on the board directions order
             * @param boardTiles the gui representation of the tile panels
             * @return the the board's tile panels in order
             */
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return boardTiles;
            }

            /**
             * Get's the opposite board direction
             * @return the flipped board direction
             */
            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }

        },
        FLIPPED {

            /**
             * Gets the the board's tile panels based on the board directions order
             * @param boardTiles the gui representation of the tile panels
             * @return the the board's tile panels in reversed order
             */
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            /**
             * Get's the opposite board direction
             * @return the normal board direction
             */
            @Override
            BoardDirection opposite() {
                return NORMAL;
            }

        };

        /**
         * Gets the the board's tile panels based on the board directions order
         * @param boardTiles the gui representation of the tile panels
         * @return the the board's tile panels based on the board directions order
         */
        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);

        /**
         * Get's the opposite board direction
         * @return the opposite board direction
         */
        abstract BoardDirection opposite();
    }
    
    enum PlayerType {
        HUMAN,
        COMPUTER;
    }

    /**
     * This class is the visual representation of a chess board on screen
     */
    private final class BoardPanel extends JPanel {
        private final List<TilePanel> boardTiles;

        /**
         * The constructor for the visual chessboard
         */
        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < 64; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                this.add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            this.validate();
        }

        /**
         * draws all the board's tile panels
         * @param board the board the gui is drawing
         */
        public void drawBoard(final Board board) {
            this.removeAll();
            for (final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
                tilePanel.drawTile(board);
                this.add(tilePanel);
                this.validate();
                repaint();
            }
        }
    }

    /**
     * This class represents all the moves made in a chess game
     */
    public static class MoveLog {
        private final List<Move> moves;

        /**
         * Constructor that creates the move log to an empty array list
         */
        MoveLog() {
            this.moves = new ArrayList<>();
        }

        /**
         * Gets all moves made in a game
         * @return an arraylist representation moves made in a game
         */
        public List<Move> getMoves() {
            return this.moves;
        }

        /**
         * Adds a move that was made in a game
         * @param move the move to be added
         */
        public void addMove(Move move) {
            this.moves.add(move);
        }

        /**
         * Gets the number of moves made in a game
         * @return the number of moves made in a game
         */
        public int size() {
            return this.moves.size();
        }

        /**
         * Clears all moves made
         */
        public void clear() {
            this.moves.clear();
        }

        /**
         * Removes a move based on the index
         * @param index the index which the move is being removed at
         * @return the move being removed
         */
        public Move removeMove(int index) {
            return this.moves.remove(index);
        }

        /**
         * Removes a specific move
         * @param move the move to be removed
         * @return true if the removal was succesful and fals otherwise
         */
        public boolean removeMove(Move move) {
            return this.moves.remove(move);
        }

        @Override
        public String toString() {
            StringBuilder logString = new StringBuilder();
            for (Move move : this.moves) {
                logString.append(move.toString()).append("\n");  
            }
            return logString.toString();
        }
    }
    
    /**
     * This class is the visual representation for a tile on a chessboard
     */
    private class TilePanel extends JPanel {
        private final int tileId;

        /**
         * Constructor for the visual tile on screeen
         * @param boardPanel the board the tile is a member of
         * @param tileId the id of the tile which maps to it's coordinates
         */
        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (isRightMouseButton(e)) {
                        // cancel all selections
                        srcTile = null;
                        destTile = null;
                        humanMovedPiece = null;
                    }
                    else if (isLeftMouseButton(e)) {
                        // first click selecting source tile
                        if (srcTile == null) {
                            srcTile = chessBoard.getTile(tileId);
                            humanMovedPiece = srcTile.getPiece();
                            // can't select empty tile as src tile
                            if (humanMovedPiece == null) {
                                srcTile = null;
                            }
                        }
                        // second click selecting destination tile
                        else {
                            destTile = chessBoard.getTile(tileId);
                            final Move move = MoveFactory.createMove(chessBoard, srcTile.getCoord(),
                                    destTile.getCoord());
                            final MoveTransition transition = chessBoard.getCurrPlayer().makeMove(move);
                            // if legal move make new chessboard based on move
                            if (transition.getMoveStatus().isDone()) {
                                chessBoard = transition.getUpdatedBoard();
                                moveLog.addMove(move);
                            }
                            // clear selections
                            srcTile = null;
                            destTile = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable(){
                            @Override
                            public void run() {
                                gameHistoryPanel.redo(chessBoard, moveLog);
                                takenPiecesPanel.redo(moveLog);
                                
                                if (gameSetup.isAIPlayer(chessBoard.getCurrPlayer())) {
                                    Table.get().moveMadeUpdate(PlayerType.HUMAN);
                                }

                                boardPanel.drawBoard(chessBoard);
                                renderGameOverPane();

                            }
                        });
                    }
                }
                
                @Override
                public void mousePressed(final MouseEvent e) {

                }
                
                @Override
                public void mouseReleased(final MouseEvent e) {

                }
                
                @Override
                public void mouseEntered(final MouseEvent e) {

                }
                
                @Override
                public void mouseExited(final MouseEvent e) {

                } 
            });

            this.validate();
        }

        /**
         * draws the tile panel on the gui
         * @param board the board the tile panel is referencing
         */
        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightLegalMoves(board);
            this.validate();
            this.repaint();
        }

        /**
         * Highlights all legal moves for the tile selected
         * @param board the board referenced to find legal moves
         */
        private void highlightLegalMoves(final Board board) {
            if (highlightLegalMoves) {
                for (final Move move : pieceLegalMoves(board)) {
                    if (move.getDestCoord() == this.tileId) {
                        try {
                            this.add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        
        /**
         * Finds all legal moves for the tile selected
         * @param board the board referenced to find legal moves
         * @return a collection of all legal moves for this tile
         */
        private Collection<Move> pieceLegalMoves(final Board board) {
            if (humanMovedPiece != null && humanMovedPiece.getAlliance() == board.getCurrPlayer().getAlliance()) {
                return humanMovedPiece.calcLegalMoves(board);
            }
            return Collections.emptyList();
        }
            
        /**
         * Assigns the tile a color based on it's position
         */
        private void assignTileColor() {
            // odd rows tile color
            if (BoardUtils.RANK_8[this.tileId] ||
                    BoardUtils.RANK_6[this.tileId] ||
                    BoardUtils.RANK_4[this.tileId] ||
                    BoardUtils.RANK_2[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);

            }
            // even row tile color
            else if (BoardUtils.RANK_7[this.tileId] ||
                    BoardUtils.RANK_5[this.tileId] ||
                    BoardUtils.RANK_3[this.tileId] ||
                    BoardUtils.RANK_1[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }

        /**
         * Assings the tile a piece icon based on the passed board
         * @param board the board referenced
         */
        private void  assignTilePieceIcon(final Board board) {
            this.removeAll();
            if (board.getTile(this.tileId).isOccupied()) {
                try {
                    // get image of piece on tile
                    final BufferedImage image = 
                            ImageIO.read(new File(pieceImagesPath
                                    + board.getTile(this.tileId).getPiece().getAlliance().toString().substring(0, 1) + board.getTile(tileId).getPiece().toString() + ".gif"));
                    this.add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}