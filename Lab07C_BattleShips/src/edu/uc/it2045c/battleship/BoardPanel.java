package edu.uc.it2045c.battleship;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private final JButton[][] btns;
    private final GameController controller;

    public BoardPanel(int rows, int cols, GameController controller) {
        this.controller = controller;
        setLayout(new GridLayout(rows, cols));
        btns = new JButton[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JButton b = new JButton("~"); // BLANK water
                b.setFocusPainted(false);
                final int rr = r, cc = c;
                b.addActionListener(e -> controller.fireAt(rr, cc));
                btns[r][c] = b;
                add(b);
            }
        }
    }

    public void setCellHit(int r, int c) { btns[r][c].setText("X"); btns[r][c].setBackground(Color.PINK); }
    public void setCellMiss(int r, int c) { btns[r][c].setText("M"); btns[r][c].setBackground(Color.YELLOW); }
    public void disableCell(int r, int c) { btns[r][c].setEnabled(false); }
}