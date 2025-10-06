import javax.swing.JButton;

/** A board button that knows its row/col and current mark. */
public class TicTacToeButton extends JButton {
    public final int row;
    public final int col;
    private char mark = ' '; // ' ', 'X', or 'O'

    public TicTacToeButton(int r, int c) {
        super("");
        this.row = r;
        this.col = c;
        setFocusPainted(false);
    }

    public char getMark() { return mark; }

    public void setMark(char m) {
        mark = m;
        setText(mark == ' ' ? "" : String.valueOf(mark));
    }

    public boolean isEmpty() { return mark == ' '; }

    public void clear() { setMark(' '); }
}
