package invoice;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoiceFrame extends JFrame {
    private final JTextField tfName = new JTextField();
    private final JTextField tfPrice = new JTextField();
    private final JSpinner spQty = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
    private final JButton btnAdd = new JButton("Add Line");
    private final JButton btnClear = new JButton("Clear");
    private final JButton btnPrint = new JButton("Print");
    private final JButton btnQuit = new JButton("Quit");
    private final JTextArea receipt = new JTextArea(14, 50); // wider receipt

    private final Invoice invoice = new Invoice();
    private final java.util.List<LineItem> backing = new ArrayList<>();
    private final JTable table = new JTable(new LineItemTableModel(backing));

    public InvoiceFrame() {
        super("Invoice");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(820, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        // LEFT: entry panel
        JPanel entry = new JPanel(new GridLayout(0,1,6,6));
        entry.setBorder(new TitledBorder("Add Line Item"));
        entry.add(labeled("Product Name", tfName));
        entry.add(labeled("Unit Price (e.g. 12.50)", tfPrice));
        entry.add(labeled("Quantity", spQty));
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(btnAdd); buttons.add(btnClear); buttons.add(btnPrint); buttons.add(btnQuit);
        entry.add(buttons);
        add(entry, BorderLayout.WEST);

        // CENTER: table
        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(new TitledBorder("Items"));
        center.add(new JScrollPane(table), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // EAST: receipt
        JPanel east = new JPanel(new BorderLayout());
        east.setBorder(new TitledBorder("Receipt"));
        receipt.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        receipt.setEditable(false);
        east.add(new JScrollPane(receipt), BorderLayout.CENTER);
        add(east, BorderLayout.EAST);

        // actions
        btnAdd.addActionListener(e -> onAdd());
        btnClear.addActionListener(e -> onClear());
        btnPrint.addActionListener(e -> onPrint());
        btnQuit.addActionListener(e -> System.exit(0));
    }

    private JPanel labeled(String label, JComponent comp) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(comp, BorderLayout.CENTER);
        return p;
    }

    private void onAdd() {
        try {
            String name = tfName.getText().trim();
            double price = Double.parseDouble(tfPrice.getText().trim());
            int qty = (int) spQty.getValue();

            Product p = new Product(name, Money.ofDollars(price));
            LineItem li = new LineItem(p, qty);
            invoice.add(li);
            backing.add(li);
            ((AbstractTableModel) table.getModel()).fireTableDataChanged();

            tfName.setText(""); tfPrice.setText(""); spQty.setValue(1);
            tfName.requestFocus();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onClear() {
        invoice.clear(); backing.clear();
        ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        receipt.setText("");
    }

    private void onPrint() {
        receipt.setText(new InvoiceFormatter().format(invoice));
        receipt.setCaretPosition(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InvoiceFrame().setVisible(true));
    }
}
