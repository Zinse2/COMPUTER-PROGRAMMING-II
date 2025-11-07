package edu.uc.it2045c.battleship;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class GameController {
    private final BattleshipFrame frame;
    private final GameStats stats = new GameStats();
    private Board board;

    public GameController(BattleshipFrame frame){ this.frame = frame; }

    public void newGame() {
        board = new Board(10,10);
        List<ShipType> ships = Arrays.asList(
                ShipType.CARRIER5, ShipType.BATTLESHIP4,
                ShipType.CRUISER3A, ShipType.CRUISER3B,
                ShipType.DESTROYER2);
        new ShipPlacer().place(board, ships);
        stats.reset();
        frame.updateStats(stats);
    }

    public void newGameWithConfirm(JFrame parent){
        int res = JOptionPane.showConfirmDialog(parent, "Start a new game?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) newGame();
    }

    public void quitWithConfirm(JFrame parent){
        int res = JOptionPane.showConfirmDialog(parent, "Quit the game?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) System.exit(0);
    }

    public void fireAt(int r, int c) {
        if (!board.inBounds(r,c)) return;
        Board.FireResult result = board.fireAt(r,c);
        frame.disableCell(r,c);
        switch (result) {
            case HIT -> {
                stats.onHit();
                frame.showHit(r,c);
                Ship sunk = board.getSunkShipIfAnyAt(r,c);
                if (sunk != null) frame.showSunk(sunk.size());
                if (board.allSunk()) { frame.updateStats(stats); frame.showWin(); return; }
            }
            case MISS -> {
                stats.onMiss();
                frame.showMiss(r,c);
                if (stats.getStrikeCount() >= 3) { frame.updateStats(stats); frame.showLoss(); return; }
            }
            case REDUNDANT -> { /* already disabled in UI, ignore */ }
        }
        frame.updateStats(stats);
    }
}