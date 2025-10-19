package pizzagui;

public class PizzaGUIRunner {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new PizzaGUIFrame().setVisible(true));
    }
}
