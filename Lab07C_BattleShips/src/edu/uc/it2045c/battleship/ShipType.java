package edu.uc.it2045c.battleship;
public enum ShipType {
    CARRIER5(5), BATTLESHIP4(4), CRUISER3A(3), CRUISER3B(3), DESTROYER2(2);
    public final int size;
    ShipType(int s){ this.size = s; }
}