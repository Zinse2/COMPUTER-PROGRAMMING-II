import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * GUI Tic-Tac-Toe for two human players (X starts).
 * - 3x3 GridLayout of buttons (+ Quit)
 * - Single listener for all squares
 * - JOptionPane for errors, wins, ties, and play-again prompt
 * - Checks win after each move starting with move 5; checks tie starting with move 7
 * - Supports both "full-board tie" and an "early tie" when no winning line is still possible
 */
public class TicTacToeFrame extends JFrame {

    private final TicTacToeButton[][] buttons = new TicTacToeButton[3][3];
    private final char[][] board = new char[3][3];

    private char current = 'X';
    private int moveCount = 0;

    private final JLabel turnLabel = new JLabel("Turn: X");

    public TicTacToeFrame() {
        super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));
        ((JComponent)getContentPane()).setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // --- Top title / turn
        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        // --- Center: board 3x3
        JPanel boardPanel = new JPanel(new GridLayout(3,3,6,6));
        Font cellFont = new Font("SansSerif", Font.BOLD, 40);

        ActionListener squareListener = e -> {
            TicTacToeButton b = (TicTacToeButton)e.getSource();
            handleMove(b.row, b.col);
        };

        for (int r=0; r<3; r++) {
            for (int c=0; c<3; c++) {
                buttons[r][c] = new TicTacToeButton(r,c);
                buttons[r][c].setFont(cellFont);
                buttons[r][c].addActionListener(squareListener);
                buttons[r][c].setPreferredSize(new Dimension(80,80));
                boardPanel.add(buttons[r][c]);
                board[r][c] = ' ';
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // --- South: controls (turn + quit)
        JPanel south = new JPanel(new BorderLayout());
        turnLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        south.add(turnLabel, BorderLayout.WEST);

        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> {
            int ans = JOptionPane.showConfirmDialog(
                    this, "Quit the game?", "Quit",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ans == JOptionPane.YES_OPTION) System.exit(0);
        });
        south.add(quit, BorderLayout.EAST);
        add(south, BorderLayout.SOUTH);

        // size and center
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int)(screen.width*0.35), (int)(screen.height*0.55));
        setLocationRelativeTo(null);
    }

    private void handleMove(int r, int c) {
        // Illegal move?
        if (board[r][c] != ' ') {
            JOptionPane.showMessageDialog(this,
                    "That square is already taken. Choose another.",
                    "Illegal Move", JOptionPane.ERROR_MESSAGE);
            return; // same player's turn continues
        }

        // Place mark
        board[r][c] = current;
        buttons[r][c].setMark(current);
        moveCount++;

        // Starting with move 5, wins become possible
        if (moveCount >= 5 && isWin(current)) {
            JOptionPane.showMessageDialog(this,
                    "Player " + current + " wins!",
                    "Win", JOptionPane.INFORMATION_MESSAGE);
            askPlayAgain();
            return;
        }

        // Starting with move 7, check ties
        if (moveCount >= 7) {
            if (isTieEarly() || moveCount == 9) {
                String msg = (moveCount == 9)
                        ? "It's a tie (full board)."
                        : "It's a tie (no winning line remains).";
                JOptionPane.showMessageDialog(this, msg, "Tie", JOptionPane.INFORMATION_MESSAGE);
                askPlayAgain();
                return;
            }
        }

        // Switch turn and continue
        current = (current == 'X') ? 'O' : 'X';
        turnLabel.setText("Turn: " + current);
    }

    private boolean isWin(char m) {
        // rows
        for (int r=0; r<3; r++)
            if (board[r][0]==m && board[r][1]==m && board[r][2]==m) return true;
        // cols
        for (int c=0; c<3; c++)
            if (board[0][c]==m && board[1][c]==m && board[2][c]==m) return true;
        // diags
        if (board[0][0]==m && board[1][1]==m && board[2][2]==m) return true;
        if (board[0][2]==m && board[1][1]==m && board[2][0]==m) return true;
        return false;
    }

    /**
     * Early tie detection: from move 7 onward, if there is no line (row/col/diag)
     * that could still become three-in-a-row for either player, the game is a draw even
     * before the board is full.
     */
    private boolean isTieEarly() {
        return !hasAnyOpenWinningLine('X') && !hasAnyOpenWinningLine('O');
    }

    private boolean hasAnyOpenWinningLine(char m) {
        // A line is "open" for m if it contains only m or blanks (no opponent marks).
        char opp = (m=='X') ? 'O' : 'X';
        // rows
        for (int r=0; r<3; r++) {
            if (board[r][0]!=opp && board[r][1]!=opp && board[r][2]!=opp) return true;
        }
        // cols
        for (int c=0; c<3; c++) {
            if (board[0][c]!=opp && board[1][c]!=opp && board[2][c]!=opp) return true;
        }
        // diags
        if (board[0][0]!=opp && board[1][1]!=opp && board[2][2]!=opp) return true;
        if (board[0][2]!=opp && board[1][1]!=opp && board[2][0]!=opp) return true;

        return false;
    }

    private void askPlayAgain() {
        int ans = JOptionPane.showConfirmDialog(this,
                "Play again?", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (ans == JOptionPane.YES_OPTION) {
            resetBoard();
        } else {
            dispose();
        }
    }

    private void resetBoard() {
        for (int r=0; r<3; r++) {
            for (int c=0; c<3; c++) {
                board[r][c] = ' ';
                buttons[r][c].clear();
            }
        }
        moveCount = 0;
        current = 'X';
        turnLabel.setText("Turn: X");
    }
}
