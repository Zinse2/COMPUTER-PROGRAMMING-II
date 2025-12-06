import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class RecursiveLister extends JFrame {

    private JTextArea textArea;
    private JButton startButton;
    private JButton quitButton;

    public RecursiveLister() {
        super("Recursive File Lister");

        // Try to use the system look & feel (decent look and feel)
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            // If this fails, just ignore and use default look & feel
        }

        initComponents();
        layoutComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null); // center on screen
    }

    private void initComponents() {
        // Title label
        JLabel titleLabel = new JLabel("Recursive File Lister", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        // Text area inside scroll pane
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Buttons
        startButton = new JButton("Start");
        quitButton = new JButton("Quit");

        // Start button logic
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseDirectoryAndListFiles();
            }
        });

        // Quit button logic
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add title to the frame's "client properties" so we can use it in layoutComponents
        getRootPane().putClientProperty("titleLabel", titleLabel);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10)); // gaps for nicer look

        JLabel titleLabel = (JLabel) getRootPane().getClientProperty("titleLabel");

        // Top: title
        add(titleLabel, BorderLayout.NORTH);

        // Center: scroll pane with text area
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom: buttons in a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void chooseDirectoryAndListFiles() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose a directory to list");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            Path selectedDir = chooser.getSelectedFile().toPath();
            textArea.setText(""); // clear previous output
            textArea.append("Listing files under: " + selectedDir.toString() + "\n\n");

            try {
                listFilesRecursive(selectedDir);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error reading directory:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Recursively lists all files and directories starting at the given path.
     * Uses the java.nio.file.Files class as requested in the assignment.
     */
    private void listFilesRecursive(Path directory) throws IOException {
        // Get all entries (files and directories) in this directory
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    // It's a subdirectory: show it and recurse into it
                    textArea.append("[DIR]  " + entry.toString() + "\n");
                    listFilesRecursive(entry);
                } else {
                    // It's a file: just print it
                    textArea.append("       " + entry.toString() + "\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RecursiveLister frame = new RecursiveLister();
                frame.setVisible(true);
            }
        });
    }
}
