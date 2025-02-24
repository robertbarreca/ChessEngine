package com.chess.gui;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Table {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final Board chessBoard;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static String pieceImagesPath = "pieces/";

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
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });

        // add all created items
        fileMenu.add(openPGN);
        fileMenu.add(exitMenuItem);
        return fileMenu;
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
            this.validate();
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}