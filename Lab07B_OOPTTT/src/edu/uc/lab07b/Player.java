package edu.uc.lab07b;

public enum Player {
    X, O;
    public Player next() { return this == X ? O : X; }
    public char mark() { return this == X ? 'X' : 'O'; }
}
