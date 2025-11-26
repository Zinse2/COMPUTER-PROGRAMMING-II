import javax.swing.*;
import java.awt.*;

/**
 * Swing GUI for Lab 10: lets the user add Strings into a SortedList
 * and search using binary search. All operations are logged
 * to a JTextArea.
 */
public class SortedListGUI extends JFrame {

    private final SortedList sortedList = new SortedList();

    private JTextField addField;
    private JButton addButton;

    private JTextField searchField;
    private JButton searchButton;

    private JTextArea outputArea;

    public SortedListGUI() {
        super("Lab 10 - SortedList (Binary Search)");
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // center on screen
    }

    private void initComponents() {
        // Top panel: Add section
        JPanel addPanel = new JPanel(new BorderLayout(5, 5));
        addField = new JTextField();
        addButton = new JButton("Add to Sorted List");

        addPanel.add(new JLabel("Add String:"), BorderLayout.WEST);
        addPanel.add(addField, BorderLayout.CENTER);
        addPanel.add(addButton, BorderLayout.EAST);

        // Middle panel: Search section
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Search String:"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Text area to show operations
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Layout everything in the frame
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1, 5, 5));
        topPanel.add(addPanel);
        topPanel.add(searchPanel);

        getContentPane().setLayout(new BorderLayout(5, 5));
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Wire up button actions
        addButton.addActionListener(e -> handleAdd());
        searchButton.addActionListener(e -> handleSearch());

        // Press Enter in the add field to add
        addField.addActionListener(e -> handleAdd());
        // Press Enter in the search field to search
        searchField.addActionListener(e -> handleSearch());
    }

    private void handleAdd() {
        String text = addField.getText().trim();
        if (text.isEmpty()) {
            return;
        }

        outputArea.append("Adding: \"" + text + "\"\n");
        sortedList.add(text);

        outputArea.append("Current list:\n");
        outputArea.append(sortedList.toMultilineString());
        outputArea.append("\n");

        addField.setText("");
    }

    private void handleSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            return;
        }

        outputArea.append("Searching for: \"" + query + "\"\n");

        int result = sortedList.search(query);
        if (result >= 0) {
            // Found it
            outputArea.append("Found \"" + query + "\" at index " + result + ".\n");
        } else {
            // Not found: decode insertion point
            int insertionPoint = -result - 1;
            outputArea.append("Not found. \"" + query + "\" would be at index "
                    + insertionPoint + ".\n");
        }

        outputArea.append("\n");
        searchField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortedListGUI gui = new SortedListGUI();
            gui.setVisible(true);
        });
    }
}
