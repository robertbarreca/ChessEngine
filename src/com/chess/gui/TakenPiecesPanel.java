package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.gui.Table.MoveLog;

/**
 * this class represents a gui panel that will show all taken pieces in a game
 */
public class TakenPiecesPanel extends JPanel{
    private final JPanel northPanel;
    private final JPanel southPanel;

    private static final Color PANEL_COLOR = Color.decode("0xFDF5E6");
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40, 80);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    
    /**
     * Constructor for the taken pieces
     */
    public TakenPiecesPanel() {
        super(new BorderLayout());
        this.setBackground(PANEL_COLOR);
        this.setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    public void redo(final MoveLog moveLog) {
        this.southPanel.removeAll();
        this.northPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();

        // get all captured pieces based on move log and split between black and white
        for (Move move : moveLog.getMoves()) {
            if (move.isAttackingMove()) {
                Piece capturedPiece = move.getCapturedPiece();
                if (capturedPiece.getAlliance().isWhite()) {
                    whiteTakenPieces.add(capturedPiece);
                } else if (capturedPiece.getAlliance().isBlack()) {
                    blackTakenPieces.add(capturedPiece);
                } else {
                    throw new RuntimeException("Piece must be white or black");
                }
            }
        }
        // sort taken white pieces based on piece value
        Collections.sort(whiteTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return o1.getValue() - o2.getValue();
            }
        });

        // sort taken black pieces based on piece value
        Collections.sort(blackTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return o1.getValue() - o2.getValue();
            }
        });


        // place captured white pieces
        for (final Piece capturedPiece : whiteTakenPieces) {
            try {
                String piecePath = "art/pieces/" + capturedPiece.getAlliance().toString().substring(0, 1) + capturedPiece.toString() + ".gif";
                final BufferedImage image = ImageIO.read(new File(piecePath));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(icon);
                this.southPanel.add(imageLabel);

            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        
        // place captured black pieces
        for (final Piece capturedPiece : blackTakenPieces) {
            try {
                String piecePath = "art/pieces/" + capturedPiece.getAlliance().toString().substring(0, 1) + capturedPiece.toString() + ".gif";
                final BufferedImage image = ImageIO.read(new File(piecePath));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(icon);
                this.northPanel.add(imageLabel);

            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        this.validate();
    }

}
