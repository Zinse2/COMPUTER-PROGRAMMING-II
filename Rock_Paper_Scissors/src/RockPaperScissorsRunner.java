import javax.swing.SwingUtilities;

/**
 * Entry point: creates and shows the game frame.
 */
public class RockPaperScissorsRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RockPaperScissorsFrame f = new RockPaperScissorsFrame();
            f.setVisible(true);
        });
    }
}
