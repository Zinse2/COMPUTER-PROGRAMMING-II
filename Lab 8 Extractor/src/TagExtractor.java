import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;

public class TagExtractor extends JFrame {

    // GUI components
    private JButton chooseTextButton;
    private JButton chooseStopButton;
    private JButton runButton;
    private JButton saveButton;

    private JLabel textFileLabel;
    private JLabel stopFileLabel;

    private JTextArea outputArea;

    // Data fields
    private File textFile;
    private File stopWordFile;

    private Set<String> stopWords = new HashSet<>();
    private Map<String, Integer> frequencies = new TreeMap<>(); // alphabetical by default

    public TagExtractor() {
        super("Tag / Keyword Extractor");

        initComponents();
        layoutComponents();
        attachListeners();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        chooseTextButton = new JButton("Choose Text File...");
        chooseStopButton = new JButton("Choose Stop Words File...");
        runButton = new JButton("Run Extraction");
        saveButton = new JButton("Save Tags to File");

        textFileLabel = new JLabel("Text file: (none selected)");
        stopFileLabel = new JLabel("Stop words file: (none selected)");

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    }

    private void layoutComponents() {
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        topPanel.add(chooseTextButton);
        topPanel.add(textFileLabel);
        topPanel.add(chooseStopButton);
        topPanel.add(stopFileLabel);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(runButton);
        bottomPanel.add(saveButton);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        setLayout(new BorderLayout(5, 5));
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void attachListeners() {
        chooseTextButton.addActionListener(this::handleChooseText);
        chooseStopButton.addActionListener(this::handleChooseStop);
        runButton.addActionListener(this::handleRun);
        saveButton.addActionListener(this::handleSave);
    }

    private void handleChooseText(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            textFile = chooser.getSelectedFile();
            textFileLabel.setText("Text file: " + textFile.getName());
        }
    }

    private void handleChooseStop(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            stopWordFile = chooser.getSelectedFile();
            stopFileLabel.setText("Stop words file: " + stopWordFile.getName());
        }
    }

    private void handleRun(ActionEvent e) {
        if (textFile == null) {
            JOptionPane.showMessageDialog(this,
                    "Please choose a text file first.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (stopWordFile == null) {
            JOptionPane.showMessageDialog(this,
                    "Please choose a stop words file first.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            loadStopWords();
            scanTextFile();
            showResults();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error reading files: " + ex.getMessage(),
                    "I/O Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSave(ActionEvent e) {
        if (frequencies.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No tags to save. Run extraction first.",
                    "Nothing to Save",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File outFile = chooser.getSelectedFile();
            try (PrintWriter out = new PrintWriter(new FileWriter(outFile))) {
                for (Map.Entry<String, Integer> entry : frequencies.entrySet()) {
                    out.println(entry.getKey() + "\t" + entry.getValue());
                }
                JOptionPane.showMessageDialog(this,
                        "Tags saved to: " + outFile.getName(),
                        "Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error saving file: " + ex.getMessage(),
                        "I/O Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadStopWords() throws IOException {
        stopWords.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(stopWordFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim().toLowerCase();
                if (!word.isEmpty()) {
                    stopWords.add(word);
                }
            }
        }
    }

    private void scanTextFile() throws IOException {
        frequencies.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // normalize: non-letters -> space, then lowercase
                line = line.replaceAll("[^a-zA-Z]", " ").toLowerCase();
                String[] tokens = line.split("\\s+");

                for (String token : tokens) {
                    if (token.isEmpty()) continue;
                    if (stopWords.contains(token)) continue;

                    frequencies.merge(token, 1, Integer::sum);
                }
            }
        }
    }

    private void showResults() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tags for file: ").append(textFile.getName()).append("\n\n");

        // Already alphabetical because of TreeMap
        for (Map.Entry<String, Integer> entry : frequencies.entrySet()) {
            sb.append(String.format("%-20s : %d%n", entry.getKey(), entry.getValue()));
        }

        outputArea.setText(sb.toString());
        outputArea.setCaretPosition(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TagExtractor frame = new TagExtractor();
            frame.setVisible(true);
        });
    }
}
