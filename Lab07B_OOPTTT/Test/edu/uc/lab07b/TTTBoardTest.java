package edu.uc.lab07b;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TTTBoardTest {
    @Test void placesValidMove() {
        TTTBoard b = new TTTBoard();
        assertTrue(b.place(1,1, Player.X));
        assertEquals('X', b.get(1,1));
        assertFalse(b.place(1,1, Player.O)); // already occupied
    }

    @Test void fullBoardDetection() {
        TTTBoard b = new TTTBoard();
        Player p = Player.X;
        for (int r=0;r<3;r++)
            for (int c=0;c<3;c++) {
                assertTrue(b.place(r,c,p));
                p = p.next();
            }
        assertTrue(b.isFull());
    }
}
