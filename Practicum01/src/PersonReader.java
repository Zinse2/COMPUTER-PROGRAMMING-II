import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class PersonReader {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select a Person data file...");

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("No file selected. Exiting.");
            return;
        }

        Path file = chooser.getSelectedFile().toPath();
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }

        // Display formatted
        System.out.printf("%-6s %-12s %-12s %-6s %s%n", "ID#", "First", "Last", "Title", "YOB");
        System.out.println("========================================================");

        for (String rec : lines) {
            String[] parts = rec.split(",\\s*");
            if (parts.length == 5) {
                System.out.printf("%-6s %-12s %-12s %-6s %s%n",
                        parts[0], parts[1], parts[2], parts[3], parts[4]);
            }
        }
    }
}
