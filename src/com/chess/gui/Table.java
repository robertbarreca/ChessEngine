package com.chess.gui;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;
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


public class Table {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private Board chessBoard;

    private Tile srcTile;
    private Tile destTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;

    private boolean highlightLegalMoves;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static String pieceImagesPath = "art/pieces/";

    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
    
        
    /**
     * Constructor for the table class
     */
    public Table() {
        this.gameFrame = new JFrame("Chess Engine");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessBoard = Board.createStandardBoard();
        this.boardPanel = new BoardPanel();
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = true;
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }
        
    /**
     * Populates and creates the table's menu bar with various actions a user can do
     * @return the populated menu bar to be populated
     */
    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
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
    
    private JMenu createPreferencesMenu() {
        final JMenu preferencesMenu = new JMenu("Preferences");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
            boardDirection = boardDirection.opposite();
            boardPanel.drawBoard(chessBoard);
        }
        });
        
        final JCheckBoxMenuItem legalMovesHighlighterCheckbox = new JCheckBoxMenuItem("Highlight legal moves", true);
        
        legalMovesHighlighterCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                highlightLegalMoves = legalMovesHighlighterCheckbox.isSelected();
            }
        });

        preferencesMenu.add(flipBoardMenuItem);
        preferencesMenu.add(legalMovesHighlighterCheckbox);
        return preferencesMenu;
    }
    
    public enum BoardDirection{
        NORMAL{
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
            
        },
        FLIPPED{
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }

        };
        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();
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
                        System.out.println(tileId);
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
                                chessBoard = transition.getTransitionBoard();
                            }
                            // clear selections
                            srcTile = null;
                            destTile = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable(){
                            @Override
                            public void run() {
                                boardPanel.drawBoard(chessBoard);
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

        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightLegalMoves(board);
            this.validate();
            this.repaint();
        }

        private void highlightLegalMoves(final Board board) {
            if (highlightLegalMoves) {
                for (final Move move : pieceLegalMoves(board)) {
                    if (move.getDestCoord() == this.tileId) {
                        try {
                            this.add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
                        } catch (Exception e) {
                            System.out.println("can't read file");
                        }
                    }
                }
            }
        }
        
        private Collection<Move> pieceLegalMoves(final Board board) {
            if (humanMovedPiece != null && humanMovedPiece.getAlliance() == board.getCurrPlayer().getAlliance()) {
                return humanMovedPiece.calcLegalMoves(board);
            }
            return Collections.emptyList();
        }
            
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