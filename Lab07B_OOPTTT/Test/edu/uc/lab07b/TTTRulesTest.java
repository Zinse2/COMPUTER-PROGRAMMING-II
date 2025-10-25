package edu.uc.lab07b;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TTTRulesTest {
    @Test void xWinsRow() {
        char[][] c = {
                {'X','X','X'},
                {' ','O',' '},
                {'O',' ',' '}
        };
        assertEquals(TTTStatus.X_WINS, new TTTRules().evaluate(c));
    }

    @Test void oWinsDiag() {
        char[][] c = {
                {'O','X','X'},
                {'X','O',' '},
                {'X',' ','O'}
        };
        assertEquals(TTTStatus.O_WINS, new TTTRules().evaluate(c));
    }

    @Test void draw() {
        char[][] c = {
                {'X','O','X'},
                {'X','O','O'},
                {'O','X','X'}
        };
        assertEquals(TTTStatus.DRAW, new TTTRules().evaluate(c));
    }

    @Test void inProgress() {
        char[][] c = {
                {'X','O',' '},
                {' ','O',' '},
                {'X',' ',' '}
        };
        assertEquals(TTTStatus.IN_PROGRESS, new TTTRules().evaluate(c));
    }
}
