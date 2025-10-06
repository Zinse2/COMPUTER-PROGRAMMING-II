/**
 * Strategy interface for choosing the computer's move.
 * Returns "R", "P", or "S".
 */
public interface Strategy {
    String getMove(String playerMove);
}
