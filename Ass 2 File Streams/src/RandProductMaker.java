import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {

    private JTextField nameField;
    private JTextField descField;
    private JTextField idField;
    private JTextField costField;
    private JTextField countField;

    private JButton addButton;
    private JButton quitButton;

    private RandomAccessFile raf;
    private int recordCount;

    public RandProductMaker() {
        super("Random Product Maker");

        initFile();
        initComponents();
        layoutComponents();
        attachHandlers();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 250);
        setLocationRelativeTo(null);
    }

    private void initFile() {
        try {
            // Match this filename to what you want for your random file
            File file = new File("products.dat");
            raf = new RandomAccessFile(file, "rw");

            // Determine how many records already exist
            long length = raf.length();
            recordCount = (int) (length / Product.RECORD_SIZE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error opening file: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        nameField = new JTextField(30);
        descField = new JTextField(30);
        idField = new JTextField(10);
        costField = new JTextField(10);

        countField = new JTextField(5);
        countField.setEditable(false);
        countField.setText(String.valueOf(recordCount));

        addButton = new JButton("Add Record");
        quitButton = new JButton("Quit");
    }

    private void layoutComponents() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descField);
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Cost:"));
        formPanel.add(costField);
        formPanel.add(new JLabel("Record Count:"));
        formPanel.add(countField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(quitButton);

        setLayout(new BorderLayout(5, 5));
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void attachHandlers() {
        addButton.addActionListener(this::handleAdd);
        quitButton.addActionListener(e -> {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                // ignore on close
            }
            System.exit(0);
        });
    }

    private void handleAdd(ActionEvent e) {
        if (raf == null) {
            JOptionPane.showMessageDialog(this,
                    "File not ready.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = nameField.getText().trim();
        String desc = descField.getText().trim();
        String id = idField.getText().trim();
        String costText = costField.getText().trim();

        if (name.isEmpty() || desc.isEmpty() || id.isEmpty() || costText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (id.length() > Product.ID_LENGTH) {
            JOptionPane.showMessageDialog(this,
                    "ID must be at most " + Product.ID_LENGTH + " characters.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double cost;
        try {
            cost = Double.parseDouble(costText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Cost must be a valid number.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Product p = new Product(name, desc, id, cost);

        try {
            // Seek to end of file (next record slot)
            raf.seek((long) recordCount * Product.RECORD_SIZE);
            p.writeToFile(raf);
            recordCount++;
            countField.setText(String.valueOf(recordCount));

            // Clear fields for next record
            nameField.setText("");
            descField.setText("");
            idField.setText("");
            costField.setText("");
            nameField.requestFocus();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error writing record: " + ex.getMessage(),
                    "I/O Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RandProductMaker frame = new RandProductMaker();
            frame.setVisible(true);
        });
    }
}
