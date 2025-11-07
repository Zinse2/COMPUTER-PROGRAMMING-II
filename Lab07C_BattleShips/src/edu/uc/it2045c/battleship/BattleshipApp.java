package edu.uc.it2045c.battleship;

import javax.swing.SwingUtilities;

public class BattleshipApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BattleshipFrame frame = new BattleshipFrame();
            frame.setVisible(true);
        });
    }
}