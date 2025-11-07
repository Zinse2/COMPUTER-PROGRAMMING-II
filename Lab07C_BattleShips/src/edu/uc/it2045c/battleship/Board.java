package edu.uc.it2045c.battleship;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public enum FireResult { HIT, MISS, REDUNDANT }
    private final int rows, cols;
    private final CellState[][] grid;
    private final List<Ship> ships = new ArrayList<>();
    private Ship lastHitShip; // for popup logic

    public Board(int r, int c){
        rows=r; cols=c;
        grid = new CellState[r][c];
        for (int i=0;i<r;i++) for (int j=0;j<c;j++) grid[i][j]=CellState.BLANK;
    }

    public boolean inBounds(int r,int c){ return r>=0 && r<rows && c>=0 && c<cols; }
    public void addShip(Ship s){ ships.add(s); }

    public FireResult fireAt(int r,int c){
        if (grid[r][c] == CellState.HIT || grid[r][c] == CellState.MISS) return FireResult.REDUNDANT;
        if (grid[r][c] == CellState.SHIP){
            grid[r][c] = CellState.HIT;
            // find ship
            for (Ship s : ships){
                if (s.contains(r,c)){
                    s.hit();
                    lastHitShip = s;
                    break;
                }
            }
            return FireResult.HIT;
        } else {
            grid[r][c] = CellState.MISS;
            lastHitShip = null;
            return FireResult.MISS;
        }
    }

    public Ship getSunkShipIfAnyAt(int r,int c){
        if (lastHitShip != null && lastHitShip.sunk()) return lastHitShip;
        return null;
    }

    public boolean allSunk(){
        return ships.stream().allMatch(Ship::sunk);
    }

    public CellState get(int r,int c){ return grid[r][c]; }
    public void setShipCell(int r,int c){ grid[r][c] = CellState.SHIP; }
}