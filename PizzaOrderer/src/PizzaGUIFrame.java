package pizzagui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PizzaGUIFrame extends JFrame {

    // --- constants ---
    private static final double TAX_RATE = 0.07;

    // sizes & prices (kept ordered)
    private final Map<String, Double> sizePrices = new LinkedHashMap<>() {{
        put("Small", 8.00);
        put("Medium", 12.00);
        put("Large", 16.00);
        put("Super", 20.00);
    }};

    // --- UI components ---
    // HCI: crust (radio buttons)
    private final JRadioButton thinCrust = new JRadioButton("Thin");
    private final JRadioButton regularCrust = new JRadioButton("Regular");
    private final JRadioButton deepCrust = new JRadioButton("Deep-dish");

    // HCI: size (combo box)
    private final JComboBox<String> sizeCombo = new JComboBox<>(sizePrices.keySet().toArray(String[]::new));

    // HCI: toppings (check boxes) – feel free to rename or add more
    private final JCheckBox cbPepperoni = new JCheckBox("Pepperoni");
    private final JCheckBox cbMushroom  = new JCheckBox("Mushrooms");
    private final JCheckBox cbOnion     = new JCheckBox("Onions");
    private final JCheckBox cbBacon     = new JCheckBox("Bacon");
    private final JCheckBox cbJalapeno  = new JCheckBox("Jalapeños");
    private final JCheckBox cbOlives    = new JCheckBox("Olives");

    // receipt area
    private final JTextArea receiptArea = new JTextArea(14, 36);

    // buttons
    private final JButton btnOrder = new JButton("Order");
    private final JButton btnClear = new JButton("Clear");
    private final JButton btnQuit  = new JButton("Quit");

    public PizzaGUIFrame() {
        super("Pizza Order");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(760, 520);
        setLocationRelativeTo(null);

        // confirm on window close (X)
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { doQuit(); }
        });

        // ---------- layout ----------
        // main uses BorderLayout to avoid one long vertical stack (per assignment)
        setLayout(new BorderLayout(12, 12));

        // NORTH: put Crust & Size side-by-side
        JPanel north = new JPanel(new GridLayout(1, 2, 12, 12));
        north.add(buildCrustPanel());
        north.add(buildSizePanel());
        add(north, BorderLayout.NORTH);

        // WEST: toppings panel
        add(buildToppingsPanel(), BorderLayout.WEST);

        // CENTER: receipt area in scroll
        add(buildReceiptPanel(), BorderLayout.CENTER);

        // SOUTH: buttons
        add(buildButtonPanel(), BorderLayout.SOUTH);

        // wire actions
        btnOrder.addActionListener(e -> doOrder());
        btnClear.addActionListener(e -> doClear());
        btnQuit.addActionListener(e -> doQuit());

        // sensible defaults
        regularCrust.setSelected(true);
        sizeCombo.setSelectedItem("Medium");
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
    }

    // --- panels ---
    private JPanel buildCrustPanel() {
        JPanel p = new JPanel(new GridLayout(3, 1));
        p.setBorder(new TitledBorder("Crust"));

        ButtonGroup group = new ButtonGroup();
        group.add(thinCrust);
        group.add(regularCrust);
        group.add(deepCrust);

        p.add(thinCrust);
        p.add(regularCrust);
        p.add(deepCrust);
        return p;
    }

    private JPanel buildSizePanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new TitledBorder("Size"));
        p.add(sizeCombo, BorderLayout.NORTH);

        // show a tiny helper under the combo
        JTextArea legend = new JTextArea("Prices:\nSmall  $8.00\nMedium $12.00\nLarge  $16.00\nSuper  $20.00");
        legend.setEditable(false);
        legend.setOpaque(false);
        legend.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        p.add(legend, BorderLayout.CENTER);
        return p;
    }

    private JPanel buildToppingsPanel() {
        JPanel p = new JPanel(new GridLayout(0, 1));
        p.setBorder(new TitledBorder("Toppings ($1 each)"));
        p.add(cbPepperoni);
        p.add(cbMushroom);
        p.add(cbOnion);
        p.add(cbBacon);
        p.add(cbJalapeno);
        p.add(cbOlives);
        return p;
    }

    private JScrollPane buildReceiptPanel() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBorder(new TitledBorder("Receipt"));
        JScrollPane sp = new JScrollPane(receiptArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        wrap.add(sp, BorderLayout.CENTER);
        return sp; // we only need the scrollpane in center
    }

    private JPanel buildButtonPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.add(btnOrder);
        p.add(btnClear);
        p.add(btnQuit);
        return p;
    }

    // --- actions ---
    private void doOrder() {
        String crust = getSelectedCrust();
        if (crust == null) {
            JOptionPane.showMessageDialog(this, "Please select a crust type.", "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String size = (String) sizeCombo.getSelectedItem();
        if (size == null) {
            JOptionPane.showMessageDialog(this, "Please select a size.", "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<String> toppings = getSelectedToppings();
        if (toppings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please choose at least one topping.", "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double base = sizePrices.get(size);
        double toppingsCost = toppings.size() * 1.00;
        double subTotal = base + toppingsCost;
        double tax = subTotal * TAX_RATE;
        double total = subTotal + tax;

        DecimalFormat df = new DecimalFormat("$0.00");

        StringBuilder r = new StringBuilder();
        r.append("=========================================\n");
        r.append(String.format("%-26s %10s%n", "Type of Crust & Size", "Price"));
        r.append(String.format("%s, %s%20s%n%n", crust, size, df.format(base)));

        r.append(String.format("%-26s %10s%n", "Ingredient", "Price"));
        for (String t : toppings) {
            r.append(String.format("%-26s %10s%n", t, df.format(1.00)));
        }
        r.append("\n");

        r.append(String.format("%-26s %10s%n", "Sub-total:", df.format(subTotal)));
        r.append(String.format("%-26s %10s%n", "Tax (7%):", df.format(tax)));
        r.append("---------------------------------------------------------------------\n");
        r.append(String.format("%-26s %10s%n", "Total:", df.format(total)));
        r.append("=========================================\n");

        receiptArea.setText(r.toString());
        receiptArea.setCaretPosition(0);
    }

    private void doClear() {
        // crust
        thinCrust.setSelected(false);
        regularCrust.setSelected(false);
        deepCrust.setSelected(false);
        // size
        sizeCombo.setSelectedIndex(-1);
        // toppings
        for (JCheckBox cb : getAllToppingBoxes()) cb.setSelected(false);
        // receipt
        receiptArea.setText("");
    }

    private void doQuit() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Confirm Quit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (result == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    // --- helpers ---
    private String getSelectedCrust() {
        if (thinCrust.isSelected()) return "Thin";
        if (regularCrust.isSelected()) return "Regular";
        if (deepCrust.isSelected()) return "Deep-dish";
        return null;
    }

    private List<JCheckBox> getAllToppingBoxes() {
        List<JCheckBox> all = new ArrayList<>();
        all.add(cbPepperoni);
        all.add(cbMushroom);
        all.add(cbOnion);
        all.add(cbBacon);
        all.add(cbJalapeno);
        all.add(cbOlives);
        return all;
    }

    private List<String> getSelectedToppings() {
        List<String> list = new ArrayList<>();
        for (JCheckBox cb : getAllToppingBoxes()) {
            if (cb.isSelected()) list.add(cb.getText());
        }
        return list;
    }
}
