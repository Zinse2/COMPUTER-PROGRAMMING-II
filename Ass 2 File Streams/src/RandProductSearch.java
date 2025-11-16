import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductSearch extends JFrame {

    private JTextField searchField;
    private JButton searchButton;
    private JButton quitButton;
    private JTextArea resultArea;

    private File dataFile;

    public RandProductSearch() {
        super("Random Product Search");

        dataFile = new File("products.dat"); // same file used by RandProductMaker

        initComponents();
        layoutComponents();
        attachHandlers();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        quitButton = new JButton("Quit");

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    }

    private void layoutComponents() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Partial Name:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(quitButton);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        setLayout(new BorderLayout(5, 5));
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void attachHandlers() {
        searchButton.addActionListener(this::handleSearch);
        quitButton.addActionListener(e -> System.exit(0));
    }

    private void handleSearch(ActionEvent e) {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a partial product name.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!dataFile.exists()) {
            JOptionPane.showMessageDialog(this,
                    "Data file not found: " + dataFile.getName(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String qLower = query.toLowerCase();
        StringBuilder sb = new StringBuilder();

        try (RandomAccessFile raf = new RandomAccessFile(dataFile, "r")) {
            long length = raf.length();
            long numRecords = length / Product.RECORD_SIZE;

            for (int i = 0; i < numRecords; i++) {
                raf.seek((long) i * Product.RECORD_SIZE);
                Product p = Product.readFromFile(raf);

                String nameLower = p.getName().toLowerCase();
                if (nameLower.contains(qLower)) {
                    sb.append("Name: ").append(p.getName()).append("\n");
                    sb.append("Description: ").append(p.getDescription()).append("\n");
                    sb.append("ID: ").append(p.getId()).append("\n");
                    sb.append("Cost: ").append(p.getCost()).append("\n");
                    sb.append("-----------------------------\n");
                }
            }

            if (sb.length() == 0) {
                sb.append("No products match \"").append(query).append("\"");
            }

            resultArea.setText(sb.toString());
            resultArea.setCaretPosition(0);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error reading data file: " + ex.getMessage(),
                    "I/O Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RandProductSearch frame = new RandProductSearch();
            frame.setVisible(true);
        });
    }
}
