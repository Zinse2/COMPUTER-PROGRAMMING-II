package edu.uc.it2045c.battleship;

import java.util.*;

public class ShipPlacer {
    private final Random rnd = new Random();

    public void place(Board b, List<ShipType> types){
        // place largest first
        types.stream().sorted(Comparator.comparingInt(t -> -t.size)).forEach(t -> placeOne(b, t));
    }

    private void placeOne(Board b, ShipType t){
        boolean placed = false;
        while(!placed){
            boolean horizontal = rnd.nextBoolean();
            if (horizontal){
                int row = rnd.nextInt(10);
                List<Integer> starts = contiguousRuns(b, row, true, t.size);
                if (!starts.isEmpty()){
                    int start = starts.get(rnd.nextInt(starts.size()));
                    Ship s = new Ship(t);
                    for (int c = start; c < start + t.size; c++){
                        b.setShipCell(row, c);
                        s.addCell(row, c);
                    }
                    b.addShip(s);
                    placed = true;
                }
            } else {
                int col = rnd.nextInt(10);
                List<Integer> starts = contiguousRuns(b, col, false, t.size);
                if (!starts.isEmpty()){
                    int start = starts.get(rnd.nextInt(starts.size()));
                    Ship s = new Ship(t);
                    for (int r = start; r < start + t.size; r++){
                        b.setShipCell(r, col);
                        s.addCell(r, col);
                    }
                    b.addShip(s);
                    placed = true;
                }
            }
        }
    }

    // If horizontal==true: scan row=rC; else scan column=rC
    private List<Integer> contiguousRuns(Board b, int rC, boolean horizontal, int len){
        List<Integer> starts = new ArrayList<>();
        int count = 0;
        for (int i=0;i<10;i++){
            int r = horizontal ? rC : i;
            int c = horizontal ? i : rC;
            if (b.get(r,c) == CellState.BLANK) {
                count++;
                if (count >= len) starts.add(i - len + 1);
            } else count = 0;
        }
        return starts;
    }
}