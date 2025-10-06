import javax.swing.SwingUtilities;

public class TicTacToeRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeFrame f = new TicTacToeFrame();
            f.setVisible(true);
        });
    }
}
