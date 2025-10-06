import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Rock Paper Scissors GUI game with Strategy pattern.
 * Inner classes: LeastUsed, MostUsed, LastUsed
 * External classes: Cheat, RandomStrategy
 */
public class RockPaperScissorsFrame extends JFrame {

    // --- UI fields
    private JTextArea logArea;
    private JTextField playerWinsField, computerWinsField, tiesField;

    private JButton rockBtn, paperBtn, scissorsBtn, quitBtn;

    // --- Game tracking
    private int playerWins = 0, computerWins = 0, ties = 0;
    private final Map<String, Integer> playerCounts = new HashMap<>(); // "R","P","S" -> count
    private String lastPlayerMove = null;

    // --- Strategies
    private final Strategy cheat = new Cheat();                 // external
    private final Strategy randomStrategy = new RandomStrategy(); // external
    private final Strategy leastUsed = new LeastUsed();         // inner
    private final Strategy mostUsed  = new MostUsed();          // inner
    private final Strategy lastUsed  = new LastUsed();          // inner

    private final Random rng = new Random();

    public RockPaperScissorsFrame() {
        super("Rock Paper Scissors Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // init counts
        playerCounts.put("R", 0);
        playerCounts.put("P", 0);
        playerCounts.put("S", 0);

        // --- NORTH: Title label
        JLabel title = new JLabel("Rock • Paper • Scissors", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        add(title, BorderLayout.NORTH);

        // --- WEST: Buttons panel (with icons + border)
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        buttonsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Choose your move",
                TitledBorder.LEADING, TitledBorder.TOP));

        rockBtn = makeButton("Rock", loadIcon("/images/rock.png"));
        paperBtn = makeButton("Paper", loadIcon("/images/paper.png"));
        scissorsBtn = makeButton("Scissors", loadIcon("/images/scissors.png"));
        quitBtn = makeButton("Quit", loadIcon("/images/quit.png"));

        // One ActionListener for R/P/S via action command
        rockBtn.setActionCommand("R");
        paperBtn.setActionCommand("P");
        scissorsBtn.setActionCommand("S");

        rockBtn.addActionListener(e -> playRound(e.getActionCommand()));
        paperBtn.addActionListener(e -> playRound(e.getActionCommand()));
        scissorsBtn.addActionListener(e -> playRound(e.getActionCommand()));
        quitBtn.addActionListener(e -> System.exit(0));

        buttonsPanel.add(rockBtn);
        buttonsPanel.add(paperBtn);
        buttonsPanel.add(scissorsBtn);
        buttonsPanel.add(quitBtn);
        add(buttonsPanel, BorderLayout.WEST);

        // --- CENTER: Log area with scroll
        logArea = new JTextArea(12, 50);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        JScrollPane scroller = new JScrollPane(logArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroller, BorderLayout.CENTER);

        // --- SOUTH: Stats panel
        JPanel stats = new JPanel(new GridLayout(1, 6, 8, 0));
        stats.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Stats",
                TitledBorder.LEADING, TitledBorder.TOP));

        stats.add(new JLabel("Player Wins:"));
        playerWinsField = roField();
        stats.add(playerWinsField);

        stats.add(new JLabel("Computer Wins:"));
        computerWinsField = roField();
        stats.add(computerWinsField);

        stats.add(new JLabel("Ties:"));
        tiesField = roField();
        stats.add(tiesField);

        updateStats();
        add(stats, BorderLayout.SOUTH);

        // size 3/4 screen and center
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int)(screen.width * 0.75), (int)(screen.height * 0.75));
        setLocationRelativeTo(null);

        // default button + ESC to quit (nice-to-have)
        getRootPane().setDefaultButton(rockBtn);
        getRootPane().registerKeyboardAction(
                e -> System.exit(0),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    // --- Helpers to build UI
    private JButton makeButton(String text, ImageIcon icon) {
        JButton b = new JButton(text, icon);
        b.setFont(new Font("SansSerif", Font.BOLD, 16));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setIconTextGap(10);
        return b;
    }

    private JTextField roField() {
        JTextField f = new JTextField("0");
        f.setHorizontalAlignment(JTextField.RIGHT);
        f.setEditable(false);
        f.setFont(new Font("SansSerif", Font.PLAIN, 16));
        return f;
    }

    private ImageIcon loadIcon(String resourcePath) {
        try {
            java.net.URL url = getClass().getResource(resourcePath);
            if (url != null) {
                Image img = new ImageIcon(url).getImage();
                Image scaled = img.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        } catch (Exception ignored) {}
        return null; // ok if images are missing
    }

    // --- Core game flow
    private void playRound(String playerMove) {
        // record player choice
        playerCounts.put(playerMove, playerCounts.get(playerMove) + 1);

        // pick strategy by probability 1..100
        int p = rng.nextInt(100) + 1;
        Strategy chosen;
        String strategyName;

        if (p <= 10) {
            chosen = cheat; strategyName = "Cheat";
        } else if (p <= 30) {
            chosen = leastUsed; strategyName = "Least Used";
        } else if (p <= 50) {
            chosen = mostUsed; strategyName = "Most Used";
        } else if (p <= 70) {
            chosen = lastUsed; strategyName = "Last Used";
        } else {
            chosen = randomStrategy; strategyName = "Random";
        }

        String compMove = chosen.getMove(playerMove);

        // compute outcome & phrase
        String line = resultLine(playerMove, compMove, strategyName);

        // update last move for strategies needing it
        lastPlayerMove = playerMove;

        // append to log + autoscroll
        logArea.append(line + System.lineSeparator());
        logArea.setCaretPosition(logArea.getDocument().getLength());

        // update stats UI
        updateStats();
    }

    private void updateStats() {
        playerWinsField.setText(String.valueOf(playerWins));
        computerWinsField.setText(String.valueOf(computerWins));
        tiesField.setText(String.valueOf(ties));
    }

    // Returns a descriptive line and updates counters
    private String resultLine(String player, String comp, String strat) {
        if (player.equals(comp)) {
            ties++;
            return String.format("%s vs %s — Tie. (Computer: %s)",
                    toWord(player), toWord(comp), strat);
        }

        // Determine winner + phrase
        String phrase;
        boolean playerWinsThis;
        if (player.equals("R") && comp.equals("S")) {
            phrase = "Rock breaks Scissors"; playerWinsThis = true;
        } else if (player.equals("S") && comp.equals("P")) {
            phrase = "Scissors cut Paper";   playerWinsThis = true;
        } else if (player.equals("P") && comp.equals("R")) {
            phrase = "Paper covers Rock";    playerWinsThis = true;
        } else if (comp.equals("R") && player.equals("S")) {
            phrase = "Rock breaks Scissors"; playerWinsThis = false;
        } else if (comp.equals("S") && player.equals("P")) {
            phrase = "Scissors cut Paper";   playerWinsThis = false;
        } else { // comp P vs player R
            phrase = "Paper covers Rock";    playerWinsThis = false;
        }

        if (playerWinsThis) {
            playerWins++;
            return String.format("%s. (Player wins! Computer: %s)  [You: %s | CPU: %s]",
                    phrase, strat, toWord(player), toWord(comp));
        } else {
            computerWins++;
            return String.format("%s. (Computer wins! Computer: %s)  [You: %s | CPU: %s]",
                    phrase, strat, toWord(player), toWord(comp));
        }
    }

    private String toWord(String m) {
        switch (m) {
            case "R": return "Rock";
            case "P": return "Paper";
            case "S": return "Scissors";
            default:  return "?";
        }
    }

    // --- Inner strategies that need internal game data -----------------

    /**
     * Picks the move that beats the player's LEAST used move.
     */
    private class LeastUsed implements Strategy {
        @Override
        public String getMove(String playerMove) {
            // find least-used among R,P,S
            String least = "R";
            int min = playerCounts.get("R");

            if (playerCounts.get("P") < min) { least = "P"; min = playerCounts.get("P"); }
            if (playerCounts.get("S") < min) { least = "S"; }

            // return counter to least
            switch (least) {
                case "R": return "P"; // Paper beats Rock
                case "P": return "S"; // Scissors beat Paper
                default:  return "R"; // Rock beats Scissors
            }
        }
    }

    /**
     * Picks the move that beats the player's MOST used move.
     */
    private class MostUsed implements Strategy {
        @Override
        public String getMove(String playerMove) {
            String most = "R";
            int max = playerCounts.get("R");

            if (playerCounts.get("P") > max) { most = "P"; max = playerCounts.get("P"); }
            if (playerCounts.get("S") > max) { most = "S"; }

            switch (most) {
                case "R": return "P";
                case "P": return "S";
                default:  return "R";
            }
        }
    }

    /**
     * Mirrors the player's LAST move and counters it.
     * If no last move exists (first round), fall back to random.
     */
    private class LastUsed implements Strategy {
        @Override
        public String getMove(String playerMove) {
            if (lastPlayerMove == null) {
                return randomStrategy.getMove(playerMove);
            }
            switch (lastPlayerMove) {
                case "R": return "P";
                case "P": return "S";
                case "S": return "R";
                default:  return randomStrategy.getMove(playerMove);
            }
        }
    }
}
