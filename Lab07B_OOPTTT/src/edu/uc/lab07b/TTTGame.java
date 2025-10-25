package edu.uc.lab07b;

import javax.swing.*;

public class TTTGame {
    private final TTTBoard board = new TTTBoard();
    private final TTTRules rules = new TTTRules();
    private final TTTFrame frame = new TTTFrame();
    private Player current = Player.X;
    private TTTStatus status = TTTStatus.IN_PROGRESS;

    public void start() {
        frame.setController(this);
        frame.clearBoard();
        updateStatusLabel();
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public void handleClick(int r, int c) {
        if (status != TTTStatus.IN_PROGRESS) return;
        if (board.place(r, c, current)) {
            frame.setCell(r, c, current.mark());
            status = rules.evaluate(board.snapshot());
            if (status == TTTStatus.IN_PROGRESS) {
                current = current.next();
                updateStatusLabel();
            } else {
                endGame();
            }
        }
    }

    private void endGame() {
        switch (status) {
            case X_WINS -> frame.setStatusText("X wins! Click Reset to play again.");
            case O_WINS -> frame.setStatusText("O wins! Click Reset to play again.");
            case DRAW  -> frame.setStatusText("Draw! Click Reset to play again.");
            default -> {}
        }
        frame.enableBoard(false);
    }

    private void updateStatusLabel() {
        frame.setStatusText("Turn: " + current);
        frame.enableBoard(true);
    }

    public void reset() {
        board.clear();
        frame.clearBoard();
        current = Player.X;
        status = TTTStatus.IN_PROGRESS;
        updateStatusLabel();
    }

    public static void main(String[] args) {
        new TTTGame().start();
    }
}
