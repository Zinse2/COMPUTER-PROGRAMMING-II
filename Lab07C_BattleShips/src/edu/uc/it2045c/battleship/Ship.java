package edu.uc.it2045c.battleship;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final ShipType type;
    private final List<Point> coords = new ArrayList<>();
    private int hits = 0;
    public Ship(ShipType t){ this.type = t; }
    public int size(){ return type.size; }
    public void addCell(int r,int c){ coords.add(new Point(r,c)); }
    public boolean contains(int r,int c){ return coords.stream().anyMatch(p -> p.x==r && p.y==c); }
    public void hit(){ hits++; }
    public boolean sunk(){ return hits >= size(); }
    public List<Point> cells(){ return coords; }
}