import java.util.Random;

/**
 * Picks R, P, or S uniformly at random.
 * Used with 30% probability.
 */
public class RandomStrategy implements Strategy {
    private final Random rng = new Random();

    @Override
    public String getMove(String playerMove) {
        int v = rng.nextInt(3);
        return (v == 0) ? "R" : (v == 1) ? "P" : "S";
    }
}
