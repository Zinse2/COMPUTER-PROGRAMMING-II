import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FortuneTellerFrame extends JFrame {
    private final JTextArea output;

    // 12+ humorous fortunes
    private final List<String> fortunes = Arrays.asList(
            "You will debug it on the first try. (…sure you will.)",
            "A semicolon will save your day.",
            "Your coffee will be strong and your Wi-Fi stronger.",
            "Beware the off-by-one at midnight.",
            "A merge conflict approaches—remain calm.",
            "Rubber duck: your wisest mentor.",
            "NullPointerException lurks in shadows.",
            "Your next commit message will be poetic.",
            "A mysterious TODO will be completed today.",
            "You cache today; you conquer latency tomorrow.",
            "Unit tests will be your lucky charm.",
            "Tabs and spaces will finally make peace."
    );

    private final Random rng = new Random();
    private int lastIndex = -1;

    public FortuneTellerFrame() {
        super("Fortune Teller");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        // --- Top panel: title + image (text below image). Use TOP for text-above.
        JLabel title = new JLabel("Fortune Teller", loadIcon(), SwingConstants.CENTER);
        title.setHorizontalTextPosition(SwingConstants.CENTER);
        title.setVerticalTextPosition(SwingConstants.BOTTOM); // text below image
        title.setFont(new Font("Serif", Font.BOLD, 48));

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        // --- Middle: JTextArea inside JScrollPane
        output = new JTextArea(10, 40);
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        output.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JScrollPane scroller = new JScrollPane(
                output,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        add(scroller, BorderLayout.CENTER);

        // --- Bottom: Buttons
        JButton readBtn = new JButton("Read My Fortune!");
        JButton quitBtn = new JButton("Quit");

        Font buttonFont = new Font("SansSerif", Font.BOLD, 16);
        readBtn.setFont(buttonFont);
        quitBtn.setFont(buttonFont);

        // Java 8 lambdas for listeners
        readBtn.addActionListener(e -> showNextFortune());
        quitBtn.addActionListener(e -> System.exit(0));

        JPanel bottom = new JPanel();
        bottom.add(readBtn);
        bottom.add(quitBtn);
        add(bottom, BorderLayout.SOUTH);

        // --- Size to 3/4 of the screen and center
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) (screen.width * 0.75);
        int h = (int) (screen.height * 0.75);
        setSize(w, h);
        setLocationRelativeTo(null);
    }

    private void showNextFortune() {
        int idx;
        do {
            idx = rng.nextInt(fortunes.size());
        } while (idx == lastIndex);       // never repeat the last one
        lastIndex = idx;

        output.append(fortunes.get(idx) + System.lineSeparator());
        output.setCaretPosition(output.getDocument().getLength()); // auto-scroll to newest
    }

    private ImageIcon loadIcon() {
        try {
            // Looks for the image inside src/images/
            java.net.URL imageURL = getClass().getResource("/images/fortune.png");
            if (imageURL != null) {
                Image img = new ImageIcon(imageURL).getImage();
                Image scaled = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            } else {
                System.err.println("⚠️ Image not found: /images/fortune.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
