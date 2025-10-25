package edu.uc.lab07b;

import javax.swing.*;
import java.awt.*;

public class TTTFrame extends JFrame {
    private final TTTTileButton[][] buttons = new TTTTileButton[3][3];
    private final JLabel status = new JLabel("Welcome to Tic Tac Toe", SwingConstants.CENTER);
    private TTTGame controller;

    public TTTFrame() {
        super("OOP Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel grid = new JPanel(new GridLayout(3,3,6,6));
        for (int r=0;r<3;r++) for (int c=0;c<3;c++) {
            TTTTileButton b = new TTTTileButton(r,c);
            b.addActionListener(e -> {
                if (controller != null) controller.handleClick(b.getRow(), b.getCol());
            });
            buttons[r][c] = b;
            grid.add(b);
        }

        JPanel top = new JPanel(new BorderLayout());
        JButton reset = new JButton("Reset");
        reset.addActionListener(e -> { if (controller != null) controller.reset(); });
        top.add(status, BorderLayout.CENTER);
        top.add(reset, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
        setSize(360, 420);
        setLocationRelativeTo(null);
    }

    public void setController(TTTGame g) { this.controller = g; }

    public void setCell(int r,int c,char mark) {
        buttons[r][c].setText(String.valueOf(mark == ' ' ? ' ' : mark));
        buttons[r][c].setEnabled(mark == ' ');
    }

    public void setStatusText(String s) { status.setText(s); }

    public void enableBoard(boolean enabled) {
        for (TTTTileButton[] row : buttons)
            for (TTTTileButton b : row)
                if (b.getText().isBlank()) b.setEnabled(enabled);
    }

    public void clearBoard() {
        for (int r=0;r<3;r++) for (int c=0;c<3;c++) setCell(r,c,' ');
    }
}
