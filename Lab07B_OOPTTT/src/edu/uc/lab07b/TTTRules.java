package edu.uc.lab07b;

public class TTTRules {
    public TTTStatus evaluate(char[][] cells) {
        if (hasWin(cells, Player.X)) return TTTStatus.X_WINS;
        if (hasWin(cells, Player.O)) return TTTStatus.O_WINS;
        boolean full = true;
        for (char[] row : cells) for (char ch : row) if (ch == ' ') full = false;
        return full ? TTTStatus.DRAW : TTTStatus.IN_PROGRESS;
    }

    public boolean hasWin(char[][] c, Player p) {
        char m = p.mark();
        for (int i=0;i<3;i++) {
            if (c[i][0]==m && c[i][1]==m && c[i][2]==m) return true; // rows
            if (c[0][i]==m && c[1][i]==m && c[2][i]==m) return true; // cols
        }
        return (c[0][0]==m && c[1][1]==m && c[2][2]==m) ||
                (c[0][2]==m && c[1][1]==m && c[2][0]==m);
    }
}
