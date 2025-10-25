package edu.uc.lab07b;

import java.util.Arrays;

public class TTTBoard {
    private final char[][] cells = new char[3][3];

    public TTTBoard() { clear(); }

    public void clear() {
        for (char[] row : cells) Arrays.fill(row, ' ');
    }

    public char get(int r, int c) { return cells[r][c]; }

    public boolean isEmpty(int r, int c) { return cells[r][c] == ' '; }

    public boolean place(int r, int c, Player p) {
        if (r < 0 || r > 2 || c < 0 || c > 2 || !isEmpty(r,c)) return false;
        cells[r][c] = p.mark();
        return true;
    }

    public boolean isFull() {
        for (char[] row : cells)
            for (char ch : row)
                if (ch == ' ') return false;
        return true;
    }

    public char[][] snapshot() {
        char[][] copy = new char[3][3];
        for (int r=0;r<3;r++) System.arraycopy(cells[r], 0, copy[r], 0, 3);
        return copy;
    }
}
