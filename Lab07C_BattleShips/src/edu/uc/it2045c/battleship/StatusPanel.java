package edu.uc.it2045c.battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StatusPanel extends JPanel {
    private final JLabel miss = new JLabel("Miss: 0/5");
    private final JLabel strike = new JLabel("Strike: 0/3");
    private final JLabel totalMiss = new JLabel("Total Miss: 0/83");
    private final JLabel totalHit = new JLabel("Total Hit: 0/17");
    private final JButton again = new JButton("Play Again");
    private final JButton quit = new JButton("Quit");

    public StatusPanel() {
        setLayout(new GridLayout(0,1,8,8));
        add(new JLabel("Status"));
        add(miss); add(strike); add(totalMiss); add(totalHit);
        add(again); add(quit);
    }

    public void setMiss(int v){ miss.setText("Miss: " + v + "/5"); }
    public void setStrike(int v){ strike.setText("Strike: " + v + "/3"); }
    public void setTotalMiss(int v){ totalMiss.setText("Total Miss: " + v + "/83"); }
    public void setTotalHit(int v){ totalHit.setText("Total Hit: " + v + "/17"); }

    public void onPlayAgain(ActionListener l){ again.addActionListener(l); }
    public void onQuit(ActionListener l){ quit.addActionListener(l); }
}