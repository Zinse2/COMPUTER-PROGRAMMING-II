/**
 * Cheats by picking the winning move against the player's already chosen move.
 * Used with 10% probability.
 */
public class Cheat implements Strategy {
    @Override
    public String getMove(String playerMove) {
        switch (playerMove) {
            case "R": return "P"; // Paper covers Rock
            case "P": return "S"; // Scissors cut Paper
            case "S": return "R"; // Rock breaks Scissors
            default:  return "R";
        }
    }
}
