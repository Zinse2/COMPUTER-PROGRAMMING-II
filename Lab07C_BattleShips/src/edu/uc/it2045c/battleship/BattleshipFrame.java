package edu.uc.it2045c.battleship;

import javax.swing.*;
import java.awt.*;

public class BattleshipFrame extends JFrame {
    private final BoardPanel boardPanel;
    private final StatusPanel statusPanel;
    private final GameController controller;

    public BattleshipFrame() {
        super("Battleship — Single Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        controller = new GameController(this);
        boardPanel = new BoardPanel(10, 10, controller);
        statusPanel = new StatusPanel();

        add(boardPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.EAST);

        statusPanel.onPlayAgain(e -> controller.newGameWithConfirm(this));
        statusPanel.onQuit(e -> controller.quitWithConfirm(this));

        pack();
        setLocationRelativeTo(null);
        controller.newGame();
    }

    // UI update helpers
    public void showHit(int r, int c) { boardPanel.setCellHit(r, c); }
    public void showMiss(int r, int c) { boardPanel.setCellMiss(r, c); }
    public void disableCell(int r, int c) { boardPanel.disableCell(r, c); }

    public void updateStats(GameStats s) {
        statusPanel.setMiss(s.getMissStreak());
        statusPanel.setStrike(s.getStrikeCount());
        statusPanel.setTotalMiss(s.getTotalMiss());
        statusPanel.setTotalHit(s.getTotalHit());
    }

    public void showSunk(int size) {
        JOptionPane.showMessageDialog(this, "You sunk a ship of size " + size + "!");
    }

    public void showWin() {
        int res = JOptionPane.showConfirmDialog(this, "You WIN! Play again?",
                "Victory", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) controller.newGame();
    }

    public void showLoss() {
        int res = JOptionPane.showConfirmDialog(this, "3 strikes — you lose. Play again?",
                "Defeat", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) controller.newGame();
    }
}