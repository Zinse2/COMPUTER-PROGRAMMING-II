package edu.uc.lab07b;

import javax.swing.*;

public class TTTTileButton extends JButton {
    private final int row, col;

    public TTTTileButton(int r, int c) {
        super(" ");
        this.row = r; this.col = c;
        setFocusPainted(false);
        setFont(getFont().deriveFont(28f));
    }
    public int getRow() { return row; }
    public int getCol() { return col; }
}
